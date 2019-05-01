package nl.han.dea.resource;

import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.TracksService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class TracksResource {
    @Inject
    @Named("TracksServiceImpl")
    private TracksService tracksService;

    @Path("tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks(@QueryParam("token") String token, @QueryParam("forPlayList") int forPlayList) {
        try{
            TracksResponse tracksResponse = tracksService.getTracks(token, forPlayList);
            return Response.status(200).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }

    @Path("/playlists/{playlistId}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId){
        try{
            TracksResponse tracksResponse = tracksService.getTracksFromPlaylist(token, playlistId);
            return Response.status(200).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }

    @Path("/playlists/{playlistId}/tracks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, String body){
        try{
            TracksResponse tracksResponse = tracksService.addTrackToPlaylist(token, playlistId, body);
            return Response.status(201).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }

    @Path("/playlists/{playlistId}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId){
        try{
            TracksResponse tracksResponse = tracksService.removeTrackFromPlaylist(token, playlistId, trackId);
            return Response.status(200).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }

}
