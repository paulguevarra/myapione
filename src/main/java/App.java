import com.google.gson.Gson;
import dao.Sql2oBandDao;
import dao.Sql2oSongDao;
import models.Song;
import org.sql2o.Sql2o;

import org.sql2o.Connection;

import static spark.Spark.get;
import static spark.Spark.post;

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

        post("/song/new", "application/json", (req, res) -> {
            Song song = gson.fromJson(req.body(), Song.class);
            songDao.add(song);
            res.status(201);
            res.type("application/json");
            return gson.toJson(song);
        });

        get("/song","application/json",(req, res)->{
            res.type("application/json");
            return gson.toJson(songDao.getAll());
        });

        get("/song/:id", "application/json", (request, response) -> {
            response.type("application/json");
            int songId = Integer.parseInt(request.params("id"));
            return gson.toJson(songDao.findById(songId));
        });

    }
}


