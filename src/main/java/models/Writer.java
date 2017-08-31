package models;

public class Writer {
    private String writername;
    private int id;
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
        return id;
    }

    public void setWriterId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Writer writer = (Writer) o;

        if (id != writer.id) return false;
        return writername.equals(writer.writername);
    }

    @Override
    public int hashCode() {
        int result = writername.hashCode();
        result = 31 * result + id;
        return result;
    }
}
