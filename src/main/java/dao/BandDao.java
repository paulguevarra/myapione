package dao;

import models.Band;
import models.Song;

import java.util.List;

public interface BandDao {
    //create
    void add(Band band);
    //read
    List<Song> getAll();
    List<Band> getAllBandsBySong(int song);
    //update
    void update(int id, String songName, String genre, String subgenre);
    //delete
    void deleteSongById(int id);
    void deleteAllSongs();

}
