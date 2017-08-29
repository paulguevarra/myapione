package dao;

import models.Band;
import models.Song;

import java.util.List;

public interface SongDao {
    //create
    void add(Song song);
    //read
    List<Song> getAll();
    List<Band> getAllBandsBySong(int song);

    Song findById(int id);
    //update
    void update(int id, String songName, String genre, String subgenre);
    //delete
    void deleteSongById(int id);
    void deleteAllSongs();

}
