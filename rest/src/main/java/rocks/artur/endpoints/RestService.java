package rocks.artur.endpoints;

import at.ac.tuwien.ifs.PropertyPersistenceService;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistics;

import javax.inject.Inject;
import javax.ws.rs.GET;
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
    public Response getProperties() {
        List<PropertyStatistics> propertyDistribution = propertyPersistenceService.getPropertyDistribution();


        return Response.ok(propertyDistribution).build();
    }
}
