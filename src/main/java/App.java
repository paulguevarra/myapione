import com.google.gson.Gson;
import dao.Sql2oBandDao;
import dao.Sql2oHitDao;
import dao.Sql2oSongDao;
import dao.Sql2oWriterDao;
import exceptions.ApiException;
import models.Band;
import models.Hit;
import models.Song;
import models.Writer;
import org.sql2o.Sql2o;

import org.sql2o.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2oSongDao songDao;
        Sql2oBandDao bandDao;
        Sql2oWriterDao writerDao;
        Sql2oHitDao hitDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        songDao = new Sql2oSongDao(sql2o);
        bandDao = new Sql2oBandDao(sql2o);
        writerDao = new Sql2oWriterDao(sql2o);
        hitDao = new Sql2oHitDao(sql2o);
        conn = sql2o.open();

        //Create

        post("/song/new", "application/json", (req, res) -> {
            Song song = gson.fromJson(req.body(), Song.class);
            songDao.add(song);
            res.status(201);
            return gson.toJson(song);
        });
        post("/Writer/new", "application/json", (req, res) -> {
            Writer writer = gson.fromJson(req.body(), Writer.class);
            writerDao.add(writer);
            res.status(201);
            return gson.toJson(writer);
        });

        //Read
        get("/song", "application/json", (req, res) -> {
            return gson.toJson(songDao.getAll());
        });
        get("/writer", "application/json", (req, res) -> {
            return gson.toJson(songDao.getAll());
        });
        get("/song/:id", "application/json", (request, response) -> {
            int songId = Integer.parseInt(request.params("id"));
            Song songToFind = songDao.findById(songId);
            if (songToFind == null) {
                throw new ApiException(404, String.format("No songs with the id: \"&s\" exists", request.params("id")));
            }
            return gson.toJson(songToFind);
        });
        get("/writer/:id", "application/json", (request, response) -> {
            int writerid = Integer.parseInt(request.params("id"));
            Writer writerToFind = writerDao.findById(writerid);
            if (writerToFind == null) {
                throw new ApiException(404, String.format("No writers with the id: \"&s\" exists", request.params("id")));
            }
            return gson.toJson(writerToFind);
        });
        get("/band/:id/Song", "application/json", (request, response) -> {
            int bandId = Integer.parseInt(request.params("id"));
            List<Song> allSongs = bandDao.getAllSongsByBand(bandId);
            return gson.toJson(allSongs);
        });
        get("/song/:id/band", "application/json", (request, response) -> {
            int songId = Integer.parseInt(request.params("id"));
            List<Band> allBands = songDao.getAllBandsBySong(songId);
            return gson.toJson(allBands);
        });
        get("/song", "application/json", (request, response) -> {
            return gson.toJson(songDao.getAll());
        });
        get("/writer", "application/json", (request, response) -> {
            return gson.toJson(writerDao.getAll());
        });
        get("/band", "application/json", (request, response) -> {
            return gson.toJson(bandDao.getAll());
        });

        //Update
        post("/song/:songId/band/new", "application/json", (req, res) -> {
            int songId = Integer.parseInt(req.params("songId"));
            Band band = gson.fromJson(req.body(), Band.class);
            band.setSongId(songId);
            bandDao.add(band);
            res.status(201);
            return gson.toJson(band);
        });
        get("/writer/:writerid/song/:songId", "application/json", (req, res) -> {
            int songId = Integer.parseInt(req.params("songId"));
            int writerId = Integer.parseInt(req.params("writerid"));
            Writer writer = writerDao.findById(writerId);
            Song song = songDao.findById(songId);
            songDao.addSongToWriter(song, writer);
            res.status(201);
            return gson.toJson(writerDao.getAllSongsByWriter(writerId));
        });
        get("/song/:songId/writer/:writerId", "application/json", (req, res) -> {
            int songId = Integer.parseInt(req.params("songId"));
            int writerId = Integer.parseInt(req.params("writerid"));
            Writer writer = writerDao.findById(writerId);
            Song song = songDao.findById(songId);
            writerDao.addWriterToSong(writer,song);
            res.status(201);
            return gson.toJson(songDao.getAllWritersBySong(songId));
        });
        //get: delete song by Id
        get("/song/:songId/delete", "application/json", (req, res) -> {
            int movieId = Integer.parseInt(req.params("songId"));
            songDao.deleteSongById(movieId);
            return gson.toJson(songDao.getAll());
        });

        //get: delete writer by Id
        get("/writer/:writerId/delete", "application/json", (req, res) -> {
            int movieTypeId = Integer.parseInt(req.params("writerId"));
            writerDao.deleteById(movieTypeId);
            return gson.toJson(writerDao.getAll());
        });


        //post: read posted hits by sales
        post("/hit/new", "application/json", (req, res) -> {
            Hit hit = gson.fromJson(req.body(), Hit.class);
            hitDao.add(hit);
            res.status(201);
            res.type("application/joson");
            String publishedrank = hit.getPublishedRank();
            int year = Integer.parseInt(publishedrank);
            if (year <= 2017) {
                throw new ApiException(404, String.format("Year should be greater than 2017"));
            }
            return gson.toJson(hit);
        });

        //get: read posted hits by sales
        get("/hit/:havesoldeawk", "application/json", (req, res) ->{
            res.type("application/json");
            Integer hitssold = (Integer.parseInt(req.params("havesoldeawk")));
            Hit hit = hitDao.findHitBySales(hitssold);
            res.type("application/json");
            if (hit == null) {
                throw new ApiException(404, String.format("No hit available with sales of: %s exists", req.params("havesoldeawk")));
            }
            return gson.toJson(hitDao.getAllByWeeklySoldAmt(hitssold));
        });

        //Filter
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = (ApiException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });
        after((req, res) -> {
            res.type("application/json");
        });
    }
}