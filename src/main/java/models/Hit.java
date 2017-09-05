package models;

public class Hit extends Song {
    private Integer haveSoldEaWk;
    private int id;
    private String publishedRank;
//    private Boolean aHit;
    public Hit(String songname, String genre, String subgenre, Integer havesoldeawk, String publishedrank){
        super(songname, genre, subgenre);
        this.haveSoldEaWk=havesoldeawk;
        this.publishedRank=publishedrank;
//        this.aHit = aHit;
    }

//    public Boolean getaHit() {
//        return aHit;
//    }
//
//    public void setaHit(Boolean aHit) {
//        this.aHit = aHit;
//    }

    public Integer getHaveSoldEaWk() {
        return haveSoldEaWk;
    }

    public void setHaveSoldEaWk(Integer havesoldeawk) {
        this.haveSoldEaWk = havesoldeawk;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getPublishedRank() {
        return publishedRank;
    }

    public void setPublishedRank(String publishedrank) {
        this.publishedRank = publishedrank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Hit hit = (Hit) o;

        if (id != hit.id) return false;
        if (!haveSoldEaWk.equals(hit.haveSoldEaWk)) return false;
        return publishedRank.equals(hit.publishedRank);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + haveSoldEaWk.hashCode();
        result = 31 * result + id;
        result = 31 * result + publishedRank.hashCode();
        return result;
    }
}
