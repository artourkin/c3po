package at.ac.tuwien.ifs.requests;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;

public class FitsProcessFileRequest {
    private byte[] datafile;

    public byte[] getDatafile() {
        return datafile;
    }

    @FormParam("datafile")
    @PartType("application/octet-stream")
    public void setDatafile(byte[] datafile) {
        this.datafile = datafile;
    }
}
