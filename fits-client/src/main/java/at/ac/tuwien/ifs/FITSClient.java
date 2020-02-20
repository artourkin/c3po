package at.ac.tuwien.ifs;


import at.ac.tuwien.ifs.FITSObjects.FITSPropertyJsonPath;
import at.ac.tuwien.ifs.model.CharacterisationResult;
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

public class FITSClient {
    private String FITS_HOST = "ttp://localhost:8080/";

    public String getVersion() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(FITS_HOST + "version");
        return getString(httpclient.execute(httpGet));
    }

    public List<CharacterisationResult> processFile(File file) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(FITS_HOST + "examine");
        FileBody bin = new FileBody(file);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("datafile", bin);
        HttpEntity reqEntity = builder.build();
        httppost.setEntity(reqEntity);

        String fitsResultXML = getString(httpclient.execute(httppost));

        String fitsResultJSON = JSONToolkit.translateXML(fitsResultXML);

        Set<String> availableFitsProperties = JSONToolkit.getAvailableFitsProperties(fitsResultJSON);

        List<CharacterisationResult> results = new ArrayList<>();

        availableFitsProperties.forEach(property -> {
            List<CharacterisationResult> characterisationResults = JSONToolkit.getCharacterisationResults(FITSPropertyJsonPath.valueOf(property.toUpperCase()), fitsResultJSON);
            results.addAll(characterisationResults);
        });

        List<CharacterisationResult> characterisationResults = JSONToolkit.getCharacterisationResults(FITSPropertyJsonPath.IDENTIFICATION, fitsResultJSON);
        results.addAll(characterisationResults);

        return results;

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


    public String getFITS_HOST() {
        return FITS_HOST;
    }

    public void setFITS_HOST(String FITS_HOST) {
        this.FITS_HOST = FITS_HOST;
    }
}
