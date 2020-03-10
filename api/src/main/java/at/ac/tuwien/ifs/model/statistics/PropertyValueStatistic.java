package at.ac.tuwien.ifs.model.statistics;

public class PropertyValueStatistic {

    private Long count;
    private String value;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PropertyValueStatistic(String value, Long count) {
        this.count = count;
        this.value = value;
    }
}
