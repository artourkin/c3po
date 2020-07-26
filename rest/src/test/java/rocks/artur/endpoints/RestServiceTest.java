package rocks.artur.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@QuarkusTest
class RestServiceTest {

    private ClientAndServer mockFitsServer;

    private int MOCK_FITS_SERVER_PORT = 8888;

    public static String VALID_FITS_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<fits xmlns=\"http://hul.harvard.edu/ois/xml/ns/fits/fits_output\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://hul.harvard.edu/ois/xml/ns/fits/fits_output http://hul.harvard.edu/ois/xml/xsd/fits/fits_output.xsd\" version=\"1.5.0\" timestamp=\"2/19/20 1:26 PM\">\n" +
            "  <identification status=\"CONFLICT\">\n" +
            "    <identity format=\"Markdown\" mimetype=\"text/markdown\" toolname=\"FITS\" toolversion=\"1.5.0\">\n" +
            "      <tool toolname=\"Droid\" toolversion=\"6.4\" />\n" +
            "      <externalIdentifier toolname=\"Droid\" toolversion=\"6.4\" type=\"puid\">fmt/1149</externalIdentifier>\n" +
            "    </identity>\n" +
            "    <identity format=\"Plain text\" mimetype=\"text/plain\" toolname=\"FITS\" toolversion=\"1.5.0\">\n" +
            "      <tool toolname=\"Jhove\" toolversion=\"1.20.1\" />\n" +
            "      <tool toolname=\"file utility\" toolversion=\"5.35\" />\n" +
            "    </identity>\n" +
            "  </identification>\n" +
            "  <fileinfo>\n" +
            "    <size toolname=\"Jhove\" toolversion=\"1.20.1\">903</size>\n" +
            "    <filepath toolname=\"OIS File Information\" toolversion=\"1.0\" status=\"SINGLE_RESULT\">/usr/local/tomcat/webapps/fits/upload/1582118786085/README.md</filepath>\n" +
            "    <filename toolname=\"OIS File Information\" toolversion=\"1.0\" status=\"SINGLE_RESULT\">README.md</filename>\n" +
            "    <md5checksum toolname=\"OIS File Information\" toolversion=\"1.0\" status=\"SINGLE_RESULT\">133c6cf05a139fa2e472ce6fa11bb5d2</md5checksum>\n" +
            "    <fslastmodified toolname=\"OIS File Information\" toolversion=\"1.0\" status=\"SINGLE_RESULT\">1582118786000</fslastmodified>\n" +
            "  </fileinfo>\n" +
            "  <filestatus />\n" +
            "  <metadata />\n" +
            "  <statistics fitsExecutionTime=\"178\">\n" +
            "    <tool toolname=\"MediaInfo\" toolversion=\"0.7.75\" status=\"did not run\" />\n" +
            "    <tool toolname=\"OIS Audio Information\" toolversion=\"0.1\" status=\"did not run\" />\n" +
            "    <tool toolname=\"ADL Tool\" toolversion=\"0.1\" status=\"did not run\" />\n" +
            "    <tool toolname=\"VTT Tool\" toolversion=\"0.1\" status=\"did not run\" />\n" +
            "    <tool toolname=\"Droid\" toolversion=\"6.4\" executionTime=\"10\" />\n" +
            "    <tool toolname=\"Jhove\" toolversion=\"1.20.1\" executionTime=\"42\" />\n" +
            "    <tool toolname=\"file utility\" toolversion=\"5.35\" executionTime=\"65\" />\n" +
            "    <tool toolname=\"Exiftool\" toolversion=\"11.54\" executionTime=\"176\" />\n" +
            "    <tool toolname=\"NLNZ Metadata Extractor\" toolversion=\"3.6GA\" status=\"did not run\" />\n" +
            "    <tool toolname=\"OIS File Information\" toolversion=\"1.0\" executionTime=\"5\" />\n" +
            "    <tool toolname=\"OIS XML Metadata\" toolversion=\"0.2\" status=\"did not run\" />\n" +
            "    <tool toolname=\"ffident\" toolversion=\"0.2\" executionTime=\"14\" />\n" +
            "    <tool toolname=\"Tika\" toolversion=\"1.21\" executionTime=\"69\" />\n" +
            "  </statistics>\n" +
            "</fits>\n" +
            "\n" +
            "\n";

    @BeforeEach
    void setUp() {
        mockFitsServer = mockFitsServer.startClientAndServer(MOCK_FITS_SERVER_PORT);
    }

    @AfterEach
    public void stopServer() {
        mockFitsServer.stop();
    }

    @Test
    void getPropertiesTest() throws IOException {
        String str = given()
                .when().get("/rest/properties")
                .then()
                .statusCode(200).extract().asString();
        System.out.println("Result: " + str);
        ObjectMapper objectMapper = new ObjectMapper();

        List list = objectMapper.readValue(str, List.class);
        assertTrue(list.size() > 0);
    }

    @Test
    void uploadFileTest() throws IOException {
        mockFitsServer.when(
                request()
                        .withMethod("POST")
                        .withPath("/examine")
                        .withHeader("\"Content-type\", \"application/json\""))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(VALID_FITS_RESULT)
                );


        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost("http://localhost:8081/rest/upload");

        URL resource = getClass().getClassLoader().getResource("README.md");
        File file = new File(resource.getPath());






        ByteArrayBody body = new ByteArrayBody(Files.readAllBytes(file.toPath()), "NOT_A_REAL_FILE_NAME");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addPart("file", body);
        HttpEntity reqEntity = builder.build();
        httppost.setEntity(reqEntity);

        CloseableHttpResponse response = httpclient.execute(httppost);

        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

        assertEquals(200, statusCode);
        assertNotNull(responseBody);
        System.out.println(responseBody);
    }
}