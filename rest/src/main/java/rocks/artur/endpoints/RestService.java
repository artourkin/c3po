package rocks.artur.endpoints;

//import at.ac.tuwien.ifs.FITSClient;
import at.ac.tuwien.ifs.FITSClient;
import at.ac.tuwien.ifs.PropertyPersistenceService;
import at.ac.tuwien.ifs.model.CharacterisationResult;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/rest")
@Produces(MediaType.APPLICATION_JSON)
public class RestService {

    @Inject
    PropertyPersistenceService propertyPersistenceService;



    @Path("/properties")
    @GET
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


    @Path("/file")
    @POST
    public Response ProcessFile() throws JsonProcessingException {
        List<PropertyStatistic> propertyDistribution = propertyPersistenceService.getPropertyDistribution();
        ObjectMapper objectMapper = new ObjectMapper();
        String str = objectMapper.writeValueAsString(propertyDistribution);

        FITSClient fitsClient = new FITSClient();

        List<CharacterisationResult> results = new ArrayList<>();

        results.forEach(result -> propertyPersistenceService.addCharacterisationResult(result));

        Response response = Response.ok().build();

        return response;
    }

}
