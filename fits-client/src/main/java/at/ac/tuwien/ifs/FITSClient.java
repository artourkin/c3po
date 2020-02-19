package at.ac.tuwien.ifs;


import at.ac.tuwien.ifs.requests.FitsProcessFileRequest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FITSClient {

    public String getVersion() {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("http://localhost:8080/version");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        int status = response.getStatus();
        if (status > 200) {
            response.close();
            throw new WebApplicationException(status);
        }
        String value = response.readEntity(String.class);
        response.close();
        return value;
    }

    public String processFile(File file) throws IOException {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("http://localhost:8080/fits/examine");


        MultipartFormDataOutput mdo = new MultipartFormDataOutput();
        mdo.addFormData("datafile", Files.readAllBytes(file.toPath()), MediaType.TEXT_PLAIN_TYPE);
        GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mdo) { };
        Response response = target.request().post(Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
        int status = response.getStatus();
        if (status > 200) {
            response.close();
            throw new WebApplicationException(status);
        }
        String value = response.readEntity(String.class);
        response.close();
        return value;
    }


}
