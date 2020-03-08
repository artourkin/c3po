package rocks.artur.processing;

import java.util.List;

public class DigitalObjectImpl implements DigitalObject {
    private String path;
    private List<?> properties;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<?> getProperties() {
        return properties;
    }

    public void setProperties(List<?> properties) {
        this.properties = properties;
    }
}
