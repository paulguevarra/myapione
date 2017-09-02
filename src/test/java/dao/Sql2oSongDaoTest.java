package dao;

import models.Band;
import models.Song;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oSongDaoTest {
    private Sql2oSongDao songDao;
    private Sql2oBandDao bandDao;
    private Connection conn;
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        songDao = new Sql2oSongDao(sql2o);
        bandDao = new Sql2oBandDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    //helper
    public Song setupSong(){
        return  new Song ("Animals","Rock", "Hard Rock");
    }
    @Test
    public void addingCourseSetsId() throws Exception {
        Song song = new Song("Animals","Rock","Hard Rock");
        int originalSongId = song.getId();
        songDao.add(song);
        assertNotEquals(originalSongId, song.getId());
    }



    @Test
    public void existingSongsCanBeFoundById() throws Exception {
        Song song = setupSong();
        songDao.add(song);
        Song foundSong = songDao.findById(song.getId());
        assertEquals(song, foundSong);
    }

    @Test
    public void addedSongsAreReturnedFromGetAll() throws Exception {
        Song song = setupSong();
        songDao.add(song);
        assertEquals(1, songDao.getAll().size());
    }

    @Test
    public void noSongReturnsEmptyList() throws Exception {
        assertEquals(0, songDao.getAll().size());
    }

    @Test
    public void updateChangesSongName() throws Exception {
        String initialSongName = "Whip";
        Song song = new Song (initialSongName, "Rock", "Folk");
        songDao.add(song);

        songDao.update(song.getId(),"Animals", "Rock", "Folk");
        Song updatedSong = songDao.findById(song.getId());
        assertNotEquals(initialSongName, updatedSong.getSongName());
    }

    @Test
    public void deleteByIdDeletesCorrectSong() throws Exception {
        Song song = setupSong();
        songDao.add(song);
        songDao.deleteSongById(song.getId());
        assertEquals(0, songDao.getAll().size());
    }

//    @Test
//    public void clearAllClearsAll() throws Exception {
//        Song song = setupSong();
//        Song otherSong = new Song("Simple Man", "Rock", "Southern Rock");
//        songDao.add(song);
//        songDao.add(otherSong);
//        int daoSize = songDao.getAll().size();
//        songDao.deleteAllSongs();
//        assertTrue(daoSize > 0 && daoSize > songDao.getAll().size());
//    }

    @Test
    public void getAllBandsBySingReturnsBandCorrectly() throws Exception {
        Song song = setupSong();
        songDao.add(song);
        int songId = song.getId();
        Band band = new Band("Green Day", songId);
        Band otherBand = new Band("Metalica", songId);
        Band yetanotherband = new Band("WOTE", 10);
        bandDao.add(band);
        bandDao.add(otherBand);
        assertTrue(songDao.getAllBandsBySong(songId).size() == 2);
        assertTrue(songDao.getAllBandsBySong(songId).contains(band));
        assertTrue(songDao.getAllBandsBySong(songId).contains(otherBand));
        assertFalse(songDao.getAllBandsBySong(songId).contains(yetanotherband));
    }
}