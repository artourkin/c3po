package at.ac.tuwien.ifs.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ValueType {
    STRING("string"),
    BOOL("bool"),
    INTEGER("integer"),
    FLOAT("float"),
    TIMESTAMP("timestamp"),
    UID("uid");

    private final String valuetype;

    ValueType(String valuetype) {
        this.valuetype = valuetype;
    }

    @JsonValue
    public String getValuetype() {
        return valuetype;
    }
}
