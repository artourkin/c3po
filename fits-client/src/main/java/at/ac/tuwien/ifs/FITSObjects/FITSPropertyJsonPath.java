package at.ac.tuwien.ifs.FITSObjects;

public enum FITSPropertyJsonPath {
    FILENAME("$.fits.fileinfo.filename");

    private final String fitsProperty;

    FITSPropertyJsonPath(String fitsProperty) {
        this.fitsProperty = fitsProperty;
    }

    public String getFitsProperty() {
        return fitsProperty;
    }
}
