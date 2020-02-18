package at.ac.tuwien.ifs;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@QuarkusTest
class FITSClientTest {
    private ClientAndServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = mockServer.startClientAndServer(8080);
    }

    @AfterEach
    public void stopServer() {
        mockServer.stop();
    }


    @Test
    void processFile() {

        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/version")
                        .withHeader("\"Content-type\", \"application/json\""))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("1.5.0")
                );


        FITSClient fitsClient = new FITSClient();
        String s = fitsClient.processFile();


        Assert.assertEquals("1.5.0", s);

    }
}