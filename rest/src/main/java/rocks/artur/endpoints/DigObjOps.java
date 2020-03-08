package rocks.artur.endpoints;

import at.ac.tuwien.ifs.CharacterisationResultService;
import at.ac.tuwien.ifs.model.statistics.PropertyStatistics;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ops")
public class DigObjOps {

    @Inject
    CharacterisationResultService characterisationResultService;

    @Path("/properties")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        List<PropertyStatistics> propertyDistribution = characterisationResultService.getPropertyDistribution();


        return Response.ok(propertyDistribution).build();
    }
}
