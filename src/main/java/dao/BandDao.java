package dao;

import models.Song;
import models.Band;

import java.util.List;


public interface BandDao {

    //create
    void add(Band band);
    //read
    List<Band> getAll();
    List<Song> getAllSongsByBand(int bandId);
    //find
    Band findById(int id);
    //update
    void update(int id, String bandName);
    //delete
    void deleteBandById(int id);
    void deleteAllBands();
}