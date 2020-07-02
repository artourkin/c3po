package at.ac.tuwien.ifs.model.statistics;

import at.ac.tuwien.ifs.model.Property;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PropertyStatistic {

    @JsonProperty
    private Long count;
    @JsonProperty
    private Property property;

    public PropertyStatistic(Long count, Property property) {
        this.count = count;
        this.property = property;
    }

    Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "PropertyStatistic{" +
                "count=" + count +
                ", property=" + property +
                '}';
    }
}