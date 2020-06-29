package at.ac.tuwien.ifs;


import at.ac.tuwien.ifs.FITSObjects.FITSPropertyJsonPath;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.MeasuresProducer;
import at.ac.tuwien.ifs.model.Property;
import at.ac.tuwien.ifs.utils.JSONToolkit;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.WebApplicationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FITSClient implements MeasuresProducer {
    private String FITS_URL = "http://localhost:8080/";

    @Override
    public String getVersion() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(getFITS_URL() + "version");
        return getString(httpclient.execute(httpGet));
    }

    @Override
    public List<CharacterisationResult> processFile(File file) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(getFITS_URL() + "examine");
        FileBody bin = new FileBody(file);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("datafile", bin);
        HttpEntity reqEntity = builder.build();
        httppost.setEntity(reqEntity);
        CloseableHttpResponse response = httpclient.execute(httppost);
        String fitsResultXML = getString(response);

        return extractCharacterisationResults(fitsResultXML);

    }

    public List<CharacterisationResult> processFile(byte[] file) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(getFITS_URL() + "examine");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("datafile", file);
        HttpEntity reqEntity = builder.build();
        httppost.setEntity(reqEntity);

        CloseableHttpResponse response = httpclient.execute(httppost);
        String fitsResultXML = getString(response);

        return extractCharacterisationResults(fitsResultXML);
    }

    private List<CharacterisationResult> extractCharacterisationResults(String fitsResultXML) {
        List<CharacterisationResult> results = new ArrayList<>();
        String fitsResultJSON = JSONToolkit.translateXML(fitsResultXML);
        Set<String> availableFitsProperties = JSONToolkit.getAvailableFitsProperties(fitsResultJSON);

        availableFitsProperties.forEach(property -> {
            List<CharacterisationResult> characterisationResults =
                    JSONToolkit.getCharacterisationResults(FITSPropertyJsonPath.valueOf(property.toUpperCase()),
                            fitsResultJSON);
            results.addAll(characterisationResults);
        });

        List<CharacterisationResult> characterisationResults =
                JSONToolkit.getCharacterisationResults(FITSPropertyJsonPath.IDENTIFICATION, fitsResultJSON);
        results.addAll(characterisationResults);

        String filepath = results.stream().filter(result ->
                result.getProperty().equals(Property.FILEPATH)).findFirst().get().getValue().toString();
        addFilepathLabel(results, filepath);


        return results;
    }


    private void addFilepathLabel(List<CharacterisationResult> characterisationResults, String filepath) {
        characterisationResults.stream().forEach(result -> result.setFilePath(filepath));
    }

    private String getString(CloseableHttpResponse execute) throws IOException {
        CloseableHttpResponse response = execute;
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        if (statusCode > 200) {
            response.close();
            throw new WebApplicationException(responseBody, statusCode);
        }

        return responseBody;
    }


    public String getFITS_URL() {
        String fits_host = System.getenv("FITS_HOST");
        String fits_port = System.getenv("FITS_PORT");
        if (fits_host != null && !fits_host.isEmpty() &&
                fits_port != null && !fits_port.isEmpty()) {
            return fits_host + ":" + fits_port;
        }
        return FITS_URL;
    }

    public void setFITS_URL(String FITS_URL) {
        this.FITS_URL = FITS_URL;
    }
}
