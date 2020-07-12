package rocks.artur.endpoints;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

public class FileUploadFormBean {
    @FormParam("file")
    @PartType(MediaType.MEDIA_TYPE_WILDCARD)
    private byte[] file;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }
}
