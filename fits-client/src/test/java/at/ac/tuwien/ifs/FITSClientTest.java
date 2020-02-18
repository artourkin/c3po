package at.ac.tuwien.ifs;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import static io.restassured.RestAssured.given;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@QuarkusTest
class FITSClientTest {
    private ClientAndServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = mockServer.startClientAndServer(1080);
    }

    @AfterEach
    public void stopServer() {
        mockServer.stop();
    }


    @Test
    void processFile() {

        mockServer.when(
                request()
                        .withMethod("/fits/version")
                        .withPath("POST")
                        .withHeader("\"Content-type\", \"application/json\""))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withBody("1.5.0")
                );

        String s = given().contentType("application/json")
                .when()
                .post("/fits/version")
                .then()
                .statusCode(200)
                .extract().response().asString();

        Assert.assertEquals("", s);

    }
}