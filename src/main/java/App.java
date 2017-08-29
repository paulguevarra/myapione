import com.google.gson.Gson;
import dao.Sql2oBandDao;
import dao.Sql2oSongDao;
import exceptions.ApiException;
import models.Band;
import models.Song;
import org.sql2o.Sql2o;

import org.sql2o.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[]args){
        Sql2oSongDao songDao;
        Sql2oBandDao bandDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        songDao = new Sql2oSongDao(sql2o);
        bandDao = new Sql2oBandDao(sql2o);
        conn = sql2o.open();

        //Create

        post("/song/new", "application/json", (req, res) -> {
            Song song = gson.fromJson(req.body(), Song.class);
            songDao.add(song);
            res.status(201);
            return gson.toJson(song);
        });


        //Read
        get("/song","application/json",(req, res)->{
            return gson.toJson(songDao.getAll());
        });
        get("/song/:id", "application/json", (request, response) -> {
            int songId = Integer.parseInt(request.params("id"));
            Song songToFind = songDao.findById(songId);
            if(songToFind==null){
                throw new ApiException(404,String.format("No songs with the id: \"&s\" exists", request.params("id")));
            }
            return gson.toJson(songToFind);
        });
        get("/band/:id/Song", "application/json",(request, response) -> {
            int bandId = Integer.parseInt(request.params("id"));
            List<Song> allSongs = bandDao.getAllSongsByBand(bandId);
            return gson.toJson(allSongs);
        });
        get("/song/:id/band", "application/json",(request, response) -> {
            int songId = Integer.parseInt(request.params("id"));
            List<Band> allBands = songDao.getAllBandsBySong(songId);
            return gson.toJson(allBands);
        });
        get("/song","application/json",(request, response) -> {
            return gson.toJson(songDao.getAll());
        });
        get("/band","application/json",(request, response) -> {
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
        after((req,res)->{
           res.type("application/json");
        });
    }
}


