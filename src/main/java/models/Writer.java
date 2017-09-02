package models;

public class Writer {
    private String writername;
    private int writerid;
    public Writer(String writername){
        this.writername=writername;
    }

    public String getName() {
        return writername;
    }

    public void setName(String writername) {
        this.writername = writername;
    }

    public int getWriterId() {
        return writerid;
    }

    public void setId(int id) {
        this.writerid = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Writer writer = (Writer) o;

        if (writerid != writer.writerid) return false;
        return writername.equals(writer.writername);
    }

    @Override
    public int hashCode() {
        int result = writername.hashCode();
        result = 31 * result + writerid;
        return result;
    }
}
