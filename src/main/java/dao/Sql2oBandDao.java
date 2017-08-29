package dao;

import models.Band;
import models.Song;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oBandDao implements BandDao{

    private final Sql2o sql2o;

    public Sql2oBandDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Band band) {
        String sql = "INSERT INTO band (bandName, songId) VALUES (:bandname, :songid)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("bandname", band.getBandName())
                    .addParameter("songid", band.getSongId())
                    .addColumnMapping("BANDNAME", "bandName")
                    .addColumnMapping("SONGID", "songid")
                    .executeUpdate()
                    .getKey();
            band.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Band> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM band")
                    .executeAndFetch(Band.class);
        }
    }

    @Override
    public Band findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM band WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Band.class);
        }
    }

    @Override
    public void update(int id, String newBandName) {
        String sql = "UPDATE band SET (bandName) = (:bandName) WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("bandName", newBandName)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteBandById(int id) {
        String sql = "DELETE from band WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAllBands() {
        String sql = "DELETE from band";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Song> getAllSongsByBand(int bandId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM song WHERE bandId = :bandid")
                    .addParameter("bandid", bandId)
                    .executeAndFetch(Song.class);
        }

    }
}