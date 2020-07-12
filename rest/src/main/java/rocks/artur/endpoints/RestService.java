package rocks.artur.endpoints;

//import at.ac.tuwien.ifs.FITSClient;

import at.ac.tuwien.ifs.PropertyPersistenceService;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                "Authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        return response;
    }


    @Path("/upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response ProcessFile(@FormParam("file") String fileUpload) {
        // Map<String, List<InputPart>> formDataMap = fileUpload.getFormDataMap();
        Response response = Response.ok(fileUpload).build();

        return response;
    }

}
