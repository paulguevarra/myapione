package models;

public class SongType {
    private String type;
    private int id;
    public SongType(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

        SongType songType = (SongType) o;

        if (id != songType.id) return false;
        return type.equals(songType.type);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + id;
        return result;
    }
}
