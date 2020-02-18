package at.ac.tuwien.ifs;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class FITSClient {

    public String processFile() {
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
        // You should close connections!
        return value;
    }


}
