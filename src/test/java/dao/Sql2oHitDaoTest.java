package dao;

import models.Hit;
import models.Writer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import static org.junit.Assert.*;

public class Sql2oHitDaoTest {
    private Sql2oSongDao songDao;
    private Sql2oHitDao hitDao;
    private Sql2oWriterDao writerDao;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        songDao = new Sql2oSongDao(sql2o);
        writerDao = new Sql2oWriterDao(sql2o);
        hitDao = new Sql2oHitDao(sql2o);
        con = sql2o.open();
    }


    @After
    public void tearDown() throws Exception {
        con.close();
    }
    //Helper
    public Hit setupHit (){
        return new Hit("Lonely Day","Rock","Metal",40000,"Billboard Top 40");
    }

    public Hit setupHitTwo (){
        return new Hit("Numb","Rock","Rap Metal",2036000,"Billboard Hot Rock Songs");
    }

    public Hit setupHitThree (){
        return new Hit("In The End","Rock","Rap Metal",2036000,"Billboard Hot Rock Songs");
    }

    public Writer setupWriter (){
        return new Writer("Chester");
    }

    public Writer setupWriterTwo (){
        return new Writer("Biography");
    }

    @Test
    public void add() throws Exception {
        Hit hit = setupHit();
        int originalHitId = hit.getId();
        hitDao.add(hit);
        assertNotEquals(originalHitId,hit.getId());
    }


    @Test
    public void getAllByWeeklySoldAmt() throws Exception {
    Hit hitTwo = setupHitTwo();
    Hit hitThree = setupHitThree();
    hitDao.add(hitTwo);
    hitDao.add(hitThree);
    assertEquals(2,hitDao.getAllByWeeklySoldAmt(2036000).size());
    }

    @Test
    public void getAllByPublishRankList() throws Exception {
        Hit hit = setupHit();
        Hit hitTwo = setupHitTwo();
        Hit hitThree = setupHitThree();
        hitDao.add(hit);
        hitDao.add(hitTwo);
        hitDao.add(hitThree);
        assertEquals(2,hitDao.getAllByPublishRankList("Billboard Hot Rock Songs").size());
    }

    @Test
    public void findHitBySales() throws Exception {
        Hit hitTwo = setupHitTwo();
        Hit hitThree = setupHitThree();
        hitDao.add(hitTwo);
        hitDao.add(hitThree);
        assertEquals(2,hitDao.getAllByWeeklySoldAmt(2036000).size());
    }

    @Test
    public void findHitByRank() throws Exception {
        Hit hit = setupHit();
        Hit hitTwo = setupHitTwo();
        Hit hitThree = setupHitThree();
        hitDao.add(hit);
        hitDao.add(hitTwo);
        hitDao.add(hitThree);
        assertEquals(2,hitDao.getAllByPublishRankList("Billboard Hot Rock Songs").size());
    }


    @Test
    public void updateHit() throws Exception {
        Hit hit = setupHit();
        hitDao.add(hit);
        hitDao.updateHit(hit.getId(),"Creep","Rock","Alternative",40000,"Bilboard Top 100");
        Hit updatedHit = hitDao.findHitById(hit.getId());
        assertNotEquals(hit, updatedHit);

    }

    @Test
    public void deleteHit() throws Exception {
        Hit hit = setupHitThree();
        Hit hitTwo = setupHitTwo();
        hitDao.add(hit);
        hitDao.add(hitTwo);
        assertEquals(2, hitDao.getAllByWeeklySoldAmt(2036000).size());
        hitDao.deleteHit(hit.getId());
        assertEquals(1, hitDao.getAllByWeeklySoldAmt(2036000).size());
    }

}