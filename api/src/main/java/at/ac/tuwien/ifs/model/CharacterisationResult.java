package at.ac.tuwien.ifs.model;

public class CharacterisationResult {
    private Property property;
    private Object value;
    private ValueType valueType;
    private String source;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "CharacterisationResult{" +
                "property=" + property +
                ", value=" + value +
                ", valueType=" + valueType +
                ", source=" + source +
                '}';
    }
}
