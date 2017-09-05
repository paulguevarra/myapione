package dao;

import models.Hit;

import java.util.List;

public interface HitDao {
    // Create
    void add(Hit hit);

    // Read
//    List<Hit> getAllHits();
    List<Hit> getAllByWeeklySoldAmt(Integer havesoldeawk);
    List<Hit> getAllByPublishRankList(String publishedrank);
    Hit findHitBySales(Integer havesoldeawk);
    Hit findHitByRank(String publishedrank);
    Hit findHitById(int id);

    // Update
    void updateHit(int id, String songname, String genre, String subgenre, Integer havesoldeawk, String publishedrank);

    // Delete
    void deleteHit(int id);
}
