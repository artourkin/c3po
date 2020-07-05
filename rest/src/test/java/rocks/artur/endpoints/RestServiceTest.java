package rocks.artur.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import rocks.artur.helpers.MultipartBody;
import rocks.artur.helpers.MultipartService;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class RestServiceTest {
    @Inject
    @RestClient
    MultipartService service;
    @Test
    void getProperties() throws IOException {
        String str = given()
                .when().get("/rest/properties")
                .then()
                .statusCode(200).extract().asString();
        System.out.println(str);
        ObjectMapper objectMapper = new ObjectMapper();

        List list = objectMapper.readValue(str, List.class);
        assertEquals(3, list.size());
    }

    @Test
    public void testMultipartDataIsSent() {
        MultipartBody body = new MultipartBody();
        body.fileName = "greeting.txt";
        body.file = new ByteArrayInputStream("HELLO WORLD".getBytes(StandardCharsets.UTF_8));
        String result = service.sendMultipartData(body);
        System.out.println(result);
    }
}