package dao;

import models.Hit;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oHitDao implements HitDao {
    private final Sql2o sql2o;

    public Sql2oHitDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Hit hit) {
        String sql = "INSERT INTO song (songname, genre, subgenre, havesoldeawk, publishedrank) VALUES (:songname, :genre, :subgenre, :havesoldeawk, :publishedrank)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("songname", hit.getSongName())
                    .addParameter("genre", hit.getGenre())
                    .addParameter("subgenre", hit.getSubgenre())
                    .addParameter("havesoldeawk", hit.getHaveSoldEaWk())
                    .addParameter("publishedrank", hit.getPublishedRank())
                    .addColumnMapping("SONGNAME", "songname")
                    .addColumnMapping("GENRE", "genre")
                    .addColumnMapping("SUBGENRE", "subgenre")
                    .addColumnMapping("HAVESOLDEAWK", "havesoldeawk")
                    .addColumnMapping("PUBLISHEDRANK", "publishedrank")
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
            hit.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

//    @Override
//    public List<Hit> getAllHits(){
//        try Connection con = sql2o.open(){
//            return con.createQuery("SELECT * FROM song WHERE ahit = :ahit")
//                    .addParameter()
//        }
//    } boolean

    @Override
    public List<Hit> getAllByWeeklySoldAmt(Integer havesoldeawk) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM song WHERE havesoldeawk = :havesoldeawk")
                    .addParameter("havesoldeawk", havesoldeawk)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Hit.class);
        }
    }

    @Override
    public List<Hit> getAllByPublishRankList(String publishedrank) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM song WHERE publishedrank = :publishedrank")
                    .addParameter("publishedrank", publishedrank)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Hit.class);
        }
    }

    @Override
    public Hit findHitBySales(Integer havesoldeawk) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM movies WHERE havesoldeawk = :havesoldeawk")
                    .addParameter("havesoldeawk", havesoldeawk)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Hit.class);
        }
    }

    @Override
    public Hit findHitByRank(String publishedrank) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM song WHERE publishedrank = :publishedrank")
                    .addParameter("publishedrank", publishedrank)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Hit.class);
        }
    }

    @Override
    public Hit findHitById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM song WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Hit.class);
        }
    }


    @Override
    public void updateHit(int id, String songname, String genre, String subgenre, Integer havesoldeawk, String publishedrank) {
        String sql = "UPDATE song SET (songname, genre, subgenre, havesoldeawk, publishedrank) = (:songname, :genre, :subgenre, :havesoldeawk, :publishedrank) WHERE id=:id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("songname", songname)
                    .addParameter("genre", genre)
                    .addParameter("subgenre", subgenre)
                    .addParameter("havesoldeawk", havesoldeawk)
                    .addParameter("publishedrank", publishedrank)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    @Override
    public void deleteHit(int id) {
        String sql = "DELETE FROM song WHERE id = :id";
        String deleteJoin = "DELETE FROM song_writer WHERE songid = :songid";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("songid", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


}