package dao;

import models.Band;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oBandDaoTest {


    private Sql2oBandDao bandDao;
    private Sql2oSongDao songDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        bandDao = new Sql2oBandDao(sql2o);
        songDao = new Sql2oSongDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Band band = new Band("LMFAO",1);
        int originalBandId = band.getId();
        bandDao.add(band);
        assertNotEquals(originalBandId, band.getId());
    }


    @Test
    public void existingBandsCanBeFoundById() throws Exception {
        Band band = new Band("Foo Fighters",1);
        bandDao.add(band);
        Band foundWalker = bandDao.findById(band.getId());
        assertEquals(band, foundWalker);
    }

    @Test
    public void addedBandsAreReturnedFromGetAll() throws Exception {
        Band band = new Band("Foo Fighters",1);
        bandDao.add(band);
        assertEquals(1, bandDao.getAll().size());
    }

    @Test
    public void noBandReturnsEmptyList() throws Exception {
        assertEquals(0, bandDao.getAll().size());
    }

    @Test
    public void updateChangesBandName() throws Exception {
        String initialBandName = "Foo Fighters";
        Band band = new Band (initialBandName, 1);
        bandDao.add(band);

        bandDao.update(band.getId(),"Nirvana");
        Band updatedBand = bandDao.findById(band.getId());
        assertNotEquals(initialBandName, updatedBand.getBandName());
    }

    @Test
    public void deleteByIdDeletesCorrectBand() throws Exception {
        Band band    = new Band("Nirvana",1);
        bandDao.add(band);
        bandDao.deleteBandById(band.getId());
        assertEquals(0, bandDao.getAll().size());
    }

    public void deleteAllClearsAll() throws Exception {
        Band band = setupBand();
        Band otherBand = new Band("WOTE",1);
        bandDao.add(band);
        bandDao.add(otherBand);
        int daoSize = bandDao.getAll().size();
        bandDao.deleteAllBands();
        assertTrue(daoSize > 0 && daoSize > bandDao.getAll().size());
    }

    @Test
    public void songIdIsReturnedCorrectly() throws Exception {
        Band band = setupBand();
        int originalSongId = band.getSongId();
        bandDao.add(band);
        assertEquals(originalSongId, bandDao.findById(band.getId()).getSongId());
    }



    //helper methods
    public Band setupBand() {
        return new Band("Foo Fighters",1);
    }
}