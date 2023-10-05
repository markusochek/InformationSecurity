package letterReplacement.run;

public class QuantitativeRatioMap implements Comparable<QuantitativeRatioMap>{
    private Integer key;
    private Float value;

    public QuantitativeRatioMap(Integer key, Float value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QuantitativeRatioMap{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    @Override
    public int compareTo(QuantitativeRatioMap quantitativeRatioMap) {
        if (this.value > quantitativeRatioMap.value) {
            return 1;
        } else if (this.value < quantitativeRatioMap.value) {
            return -1;
        } else {
            return 0;
        }
    }
}
