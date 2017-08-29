package models;

public class Band {
    private String bandName;
    private boolean released;
    private int id;
    private int songId;

    public Band(String bandName,int songId){
        this.bandName = bandName;
        this.released = false;
        this.songId = songId;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Band band = (Band) o;

        if (released != band.released) return false;
        if (id != band.id) return false;
        if (songId != band.songId) return false;
        return bandName.equals(band.bandName);
    }

    @Override
    public int hashCode() {
        int result = bandName.hashCode();
        result = 31 * result + (released ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + songId;
        return result;
    }
}
