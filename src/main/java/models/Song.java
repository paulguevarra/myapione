package models;

public class Song {
    private String songName;
    private String genre;
    private String subgenre;
    private int id;

    public Song(String songName, String genre, String subgenre){
        this.songName=songName;
        this.genre=genre;
        this.subgenre=subgenre;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(String subgenre) {
        this.subgenre = subgenre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (id != song.id) return false;
        if (!songName.equals(song.songName)) return false;
        if (!genre.equals(song.genre)) return false;
        return subgenre.equals(song.subgenre);
    }

    @Override
    public int hashCode() {
        int result = songName.hashCode();
        result = 31 * result + genre.hashCode();
        result = 31 * result + subgenre.hashCode();
        result = 31 * result + id;
        return result;
    }
}
