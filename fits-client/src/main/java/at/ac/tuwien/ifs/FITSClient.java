package at.ac.tuwien.ifs;


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

public class FITSClient {
    private String FITS_HOST = "ttp://localhost:8080/";

    public String getVersion() throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(FITS_HOST + "version");
        CloseableHttpResponse response = httpclient.execute(httpGet);


        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

        if (statusCode > 200) {
            response.close();
            throw new WebApplicationException(responseBody, statusCode);
        }

        return responseBody;
    }

    public String processFile(File file) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(FITS_HOST + "examine");
        FileBody bin = new FileBody(file);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("datafile", bin);
        HttpEntity reqEntity = builder.build();
        httppost.setEntity(reqEntity);

        CloseableHttpResponse response = httpclient.execute(httppost);
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
