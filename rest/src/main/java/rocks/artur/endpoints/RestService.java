package rocks.artur.endpoints;


import at.ac.tuwien.ifs.FITSClient;
import at.ac.tuwien.ifs.PropertyPersistenceService;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/rest")
public class RestService {

    @Inject
    PropertyPersistenceService propertyPersistenceService;


    @Path("/properties")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProperties() throws JsonProcessingException {
        List<PropertyStatistic> propertyDistribution = propertyPersistenceService.getPropertyDistribution();
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(propertyDistribution);
        Response response = Response.ok().entity(str).build();

        return response;
    }

    //@Inject
    //FITSClient fitsClient;

    @Path("/upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response ProcessFile(@MultipartForm FileUploadFormBean fileUpload) throws IOException {
        // Map<String, List<InputPart>> formDataMap = fileUpload.getFormDataMap();
        FITSClient fitsClient = new FITSClient();
        Integer totalCount = 0;

        String filename = fileUpload.getFilename();
        byte[] bytes = fileUpload.getFile();
        System.out.println(String.format("Processing file { %s }", filename));
        List<CharacterisationResult> results = fitsClient.processFile(bytes, filename);
        results.forEach(propertyPersistenceService::addCharacterisationResult);
        totalCount += results.size();


        Response response =
                Response.ok(totalCount).build();

        return response;
    }

}
