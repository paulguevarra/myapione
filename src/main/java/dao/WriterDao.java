package dao;

import models.Band;
import models.Song;
import models.Writer;

import java.util.List;

public interface WriterDao {
        //create
        void add(Writer writer);
        void addWriterToSong( Writer writer, Song song);
        //read
        List<Writer> getAll();
        List<Song> getAllSongsByWriter(int id);

        Writer findById(int id);
        //update
        void updateName(int writerid, String writername);
        //delete
        void deleteById(int id);
//        void deleteAll();

}
