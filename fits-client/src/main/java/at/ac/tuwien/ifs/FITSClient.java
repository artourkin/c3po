package at.ac.tuwien.ifs;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;

public class FITSClient {

    public void processFile(File file) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("http://localhost:8080/version");
        Response response = target.request().get();
        String value = response.readEntity(String.class);
        response.close();  // You should close connections!

    }


}
