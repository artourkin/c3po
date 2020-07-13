package rocks.artur.endpoints;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class FileUploadFormBean {
    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private byte[] file;


    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }
}
