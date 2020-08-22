package at.ac.tuwien.ifs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Filter {
    @JsonProperty
    private List<FilterCondition> conditions;

    public List<FilterCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<FilterCondition> conditions) {
        this.conditions = conditions;
    }

    class FilterCondition {
        @JsonProperty
        private Property property;
        @JsonProperty
        private String value;

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
