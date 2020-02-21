package at.ac.tuwien.ifs.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Property {
    FORMAT("format"),
    FORMAT_VERSION("format_version"),
    MIMETYPE("mimetype"),
    FILENAME("filename"),
    AUTHOR("author"),
    EXTERNALIDENTIFIER("externalidentifier"),
    SIZE("size"),
    MD5CHECKSUM("md5checksum"),
    FSLASTMODIFIED("fslastmodified"),
    FILEPATH("filepath");

    private final String property;


    Property(String property) {
        this.property=property;
    }

    @JsonValue
    public String getProperty() {
        return property;
    }
}
