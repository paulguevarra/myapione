package dao;

import models.Band;
import models.Song;
import models.Writer;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oSongDao implements SongDao {

    private final Sql2o sql2o;

    public Sql2oSongDao(Sql2o sql2o) {
        this.sql2o=sql2o;
    }

    @Override
    public void add(Song song) {
        String sql = "INSERT INTO song (songname, genre, subgenre) VALUES (:songname, :genre, :subgenre)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("songname", song.getSongName())
                    .addParameter("genre", song.getGenre())
                    .addParameter("subgenre", song.getSubgenre())
                    .addColumnMapping("SONGNAME", "songname")
                    .addColumnMapping("GENRE", "genre")
                    .addColumnMapping("SUBGENRE", "subgenre")
                    .bind(song)
                    .executeUpdate()
                    .getKey();
            song.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<Song> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM song")
                    .executeAndFetch(Song.class);
        }
    }

    @Override
    public Song findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM song WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Song.class);
        }
    }

    @Override
    public void update(int id, String newSongName, String newGenre, String newSubgenre){
        String sql = "UPDATE song SET (songname, genre, subgenre) = (:songname, :genre, :subgenre) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("songname", newSongName )
                    .addParameter("genre", newGenre)
                    .addParameter("subgenre", newSubgenre)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteSongById(int id) {
        String sql = "DELETE from song WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllSongs() {
        String sql = "DELETE from song";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Band> getAllBandsBySong(int songid) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM band WHERE songId = :songid")
                    .addParameter("songid", songid)
                    .executeAndFetch(Band.class);
        }
    }
    @Override
    public List<Writer> getAllWritersBySong(int songid){
        ArrayList<Writer> writer = new ArrayList<>();
        String joinQuery = "SELECT writerid FROM song_writer WHERE songid = :songid";
        try (Connection con = sql2o.open()) {
            List<Integer> allWritersIds = con.createQuery(joinQuery)
                    .addParameter("songid", songid)
                    .executeAndFetch(Integer.class);
            for (Integer writerid : allWritersIds) {
                String writerQuery = "SELECT * FROM writer WHERE id = :writerid";
                writer.add(
                        con.createQuery(writerQuery)
                                .addParameter("writerid", writerid)
                                .throwOnMappingFailure(false)
                                .executeAndFetchFirst(Writer.class));
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return writer;
    }
    @Override
    public void addSongToWriter(Song song, Writer writer){
        String sql = "INSERT INTO song_writer (songid, writerid) VALUES (:songid, :writerid)";
            try (Connection con = sql2o.open()){
                con.createQuery(sql)
                        .addParameter("songid",song.getId())
                        .addParameter("writer", writer.getId())
                        .executeUpdate();
            }catch (Sql2oException ex){
                System.out.println(ex);
            }
    }

}
