package nl.han.dea.resource;

import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.TracksService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class TracksResource {
    @Inject
    @Named("TracksServiceImpl")
    private TracksService tracksService;

    @Path("/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int forPlayList) {
        try{
            TracksResponse tracksResponse = tracksService.getTracks(token, forPlayList);
            return Response.status(200).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }
}
