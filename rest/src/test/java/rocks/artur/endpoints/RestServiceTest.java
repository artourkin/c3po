package rocks.artur.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class RestServiceTest {

    @Test
    void getPropertiesTest() throws IOException {
        String str = given()
                .when().get("/rest/properties")
                .then()
                .statusCode(200).extract().asString();
        System.out.println("Result: " + str);
        ObjectMapper objectMapper = new ObjectMapper();

        List list = objectMapper.readValue(str, List.class);
        assertEquals(3, list.size());
    }

    @Test
    void uploadFileTest() {

        FileUploadFormBean bean = new FileUploadFormBean();
        bean.setFile("HELLO".getBytes());




        String str = given()
                .when().body(bean).post("/rest/upload")
                .then().statusCode(200).extract().asString();
        System.out.println("Result: " + str);
        assertNotNull(str);
    }
}