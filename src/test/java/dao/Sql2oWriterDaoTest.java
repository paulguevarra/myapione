package dao;

import models.Song;
import models.Writer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oWriterDaoTest {
    private Sql2oWriterDao writerDao;
    private Sql2oSongDao songDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        writerDao = new Sql2oWriterDao(sql2o);
        songDao = new Sql2oSongDao(sql2o);
        conn = sql2o.open();    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add() throws Exception {
        Writer writer = new Writer("jacob");
        int originalWriterId = writer.getWriterId();
        writerDao.add(writer);
        assertNotEquals(originalWriterId, writer.getWriterId());
    }

    @Test
    public void getAll() throws Exception {
        Writer writer = new Writer("jacob");
        writerDao.add(writer);
        assertEquals(1, writerDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Writer writer = new Writer("jacob");
        writerDao.add(writer);
        Writer foundWriter = writerDao.findById(writer.getWriterId());
        assertEquals(writer, foundWriter);
    }
    @Test
    public void noWriterReturnsEmptyList() throws Exception {
        assertEquals(0, writerDao.getAll().size());
    }

    @Test
    public void deleteById() throws Exception {
        Writer writer = new Writer ("jacob");
        writerDao.add(writer);
        writerDao.deleteById(writer.getWriterId());
        assertEquals(0, writerDao.getAll().size());
    }

    @Test
    public void getAllSongsByWriter() throws Exception {
        Writer writer = new Writer("jacob");
        writerDao.add(writer);
        int writerId = writer.getWriterId();
        Song song = new Song("Lonely Day","rock", "metal");
        Song songTwo = new Song("Chop Suey","rock", "metal");
        Song songThree = new Song("Hypnotize", "rock","metal");

        writerDao.addWriterToSong(writer, song);
        writerDao.addWriterToSong(writer, songTwo);

        assertEquals(2,writerDao.getAllSongsByWriter(writer.getWriterId()).size());
    }

    @Test
    public void updateName() throws Exception {
        String initialWriterName = "jacob";
        Writer writer = new Writer (initialWriterName);
        writerDao.add(writer);
        writerDao.updateName(writer.getWriterId(),"sally");
        Writer updatedWriter = writerDao.findById(writer.getWriterId());
        assertNotEquals(initialWriterName, updatedWriter.getName());
    }

    @Test
    public void addWriterToSong() throws Exception {

        Song song = new Song("Lonely Day","rock", "metal");
        Song songTwo = new Song("Chop Suey","rock", "metal");

        songDao.add(song);
        songDao.add(songTwo);

        Writer writer = new Writer("jacob");

        writerDao.add(writer);

        writerDao.addWriterToSong(writer, song);
        writerDao.addWriterToSong(writer, songTwo);

        assertEquals(2, writerDao.getAllSongsByWriter(writer.getWriterId()).size());
    }
}