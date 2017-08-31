package dao;

import models.Band;
import models.Song;
import models.Writer;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oWriterDao implements WriterDao{
    private final Sql2o sql2o;

    public Sql2oWriterDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Writer writer) {
        String sql = "INSERT INTO writer (writername) VALUES (:writername";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(writer)
                    .executeUpdate()
                    .getKey();
            writer.setWriterId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Writer> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM writer")
                    .executeAndFetch(Writer.class);
        }
    }

    @Override
    public Writer findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM writer WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Writer.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from writer WHERE id=:id";
        String deleteJoined = "DELETE from song_writer WHERE writerid=:writerid";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoined)
                    .addParameter("writerid", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Song> getAllSongsByWriter(int writerid) {
        ArrayList<Song> song =new ArrayList<>();
        String joinQuery="SELECT songid FROM song_writer WHERE writerid = :writerid";
        try (Connection con = sql2o.open()) {
            List<Integer> allSongIds = con.createQuery(joinQuery)
                    .addParameter("writerid", writerid)
                    .executeAndFetch(Integer.class);
            for (Integer songid : allSongIds) {
                String songQuery = "SELECT * FROM song WHERE id = :songid";
                song.add(
                        con.createQuery(songQuery)
                                .addParameter("songid", songid)
                                .throwOnMappingFailure(false)
                                .executeAndFetchFirst(Song.class));
            }
        }catch(Sql2oException ex) {
            System.out.println(ex);
        }
        return song;
    }
    @Override
    public void updateName(String name) {
        String sql = "UPDATE name SET (name) = (:name) WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addWriterToSong(Writer writer, Song song){
        String sql = "INSERT INTO song_writer (songid, writerid) VALUES (:songid, :writerid)";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("songid",song.getId())
                    .addParameter("writer", writer.getWriterId())
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}


//    @Override
//    public void deleteAllWriter() {
//        String sql = "DELETE from writer";
//        try (Connection con = sql2o.open()) {
//            con.createQuery(sql)
//                    .executeUpdate();
//        } catch (Sql2oException ex) {
//            System.out.println(ex);
//        }
//    }

//    @Override
//    public void addWriterToSong(Song song, Writer writer){
////do stuff here.
//    }
//    @Override
//    public List<Foodtype> getAllFoodtypesForARestaurant(int restaurantId){
//        List<Foodtype> foodtypes = new ArrayList(); //empty list
//        return foodtypes;
//    }

