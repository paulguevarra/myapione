package dao;

import models.Band;
import models.Song;
import models.Writer;

import java.util.List;

public interface SongDao {
    //create
    void add(Song song);
    void addSongToWriter(Song song, Writer writer);
    //read
    List<Song> getAll();
    List<Band> getAllBandsBySong(int song);
    List<Writer> getAllWritersBySong(int songid);
    Song findById(int id);
    //update
    void update(int id, String songName, String genre, String subgenre);
    //delete
    void deleteSongById(int id);
    void deleteAllSongs();

}
