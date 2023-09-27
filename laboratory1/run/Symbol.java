package laboratory1.run;

public class Symbol implements Comparable<Symbol> {
    private Integer location;
    private Float count;

    public Symbol(Integer location, Float count) {
        this.location = location;
        this.count = count;
    }

    @Override
    public int compareTo(Symbol o) {
        return count.compareTo(o.count);
    }

    @Override
    public String toString() {
        return "Symbol {" +
                "location=" + location +
                ", count=" + count +
                '}';
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float count) {
        this.count = count;
    }
}
