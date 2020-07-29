package at.ac.tuwien.ifs.FITSObjects;

public enum FITSPropertyJsonPath {
    FILENAME("$.fits.fileinfo.filename"),
    SIZE("$.fits.fileinfo.size"),
    FILEPATH("$.fits.fileinfo.filepath"),
    MD5CHECKSUM("$.fits.fileinfo.md5checksum"),
    FSLASTMODIFIED("$.fits.fileinfo.fslastmodified"),
    IDENTIFICATION("$.fits.identification"),
    CREATINGAPPLICATIONNAME("$.fits.fileinfo.creatingApplicationName"),
    CREATED("$.fits.fileinfo.created"),
    LASTMODIFIED("$.fits.fileinfo.lastmodified");



    private final String fitsProperty;

    FITSPropertyJsonPath(String fitsProperty) {
        this.fitsProperty = fitsProperty;
    }

    public String getFitsProperty() {
        return fitsProperty;
    }
}
