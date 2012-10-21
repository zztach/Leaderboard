package org.zisist.webservice;

import org.springframework.stereotype.Component;
import org.zisist.model.TopPlayer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo RESTful web service that publishes a fixed list of winners<br>
 * Used for demonstration purposes<br>
 * Created zis.tax@gmail.com at 10/01/12 10:46 PM
 */
@Component
@Path("players")
public class WebService {

    @GET
    @Produces("application/json")
    @Path("/leaders")
    public Response getLeaders() {

        List<TopPlayer> topPlayers = new ArrayList<TopPlayer>();
        topPlayers.add(new TopPlayer("ZISIS", 100, 500, "GR"));
        topPlayers.add(new TopPlayer("IRAKLIS", 200, 600, "EN"));
        return Response.ok(topPlayers, MediaType.APPLICATION_JSON_TYPE).status(200).build();
    }

}
