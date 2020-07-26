package rocks.artur.endpoints;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

public class FileUploadFormBean {
    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private byte[] file;

    @FormParam("filename")
    private String filename;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
