package rocks.artur.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class RestServiceTest {

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
}