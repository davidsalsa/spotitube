package nl.han.dea.resource;

import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.PlaylistsService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/playlists")
public class PlaylistsResource{

    @Inject
    @Named("PlaylistsServiceImpl")
    private PlaylistsService playlistsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        try{
            PlaylistsResponse playlistsResponse = playlistsService.getPlaylists(token);
            return Response.status(200).entity(playlistsResponse).build();
        } catch( Exception e){
            return Response.status(400).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, String body) {
        try{
            PlaylistsResponse playlistsResponse = playlistsService.addPlaylist(token,body);
            if(playlistsResponse.playlists.get(0).getName().isEmpty()){
                throw new SQLException("Er is niks toegevoegd");
            }
            return Response.status(201).entity(playlistsResponse).build();
        } catch( Exception e){
            return Response.status(400).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        try{
            PlaylistsResponse playlistsResponse = playlistsService.deletePlaylist(token, id);
            return Response.status(200).entity(playlistsResponse).build();
        } catch( Exception e){
            return Response.status(400).build();
        }
    }


    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int id, String body) {
        try{
            PlaylistsResponse playlistsResponse = playlistsService.editPlaylist(token, id, body);
            return Response.status(200).entity(playlistsResponse).build();
        } catch( Exception e){
            return Response.status(400).build();
        }
    }

    @Path("/{playlistId}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId){
        try{
            TracksResponse tracksResponse = playlistsService.getTracksFromPlaylist(token, playlistId);
            return Response.status(200).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }

    @Path("/{playlistId}/tracks")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, String body){
        try{
            TracksResponse tracksResponse = playlistsService.addTrackToPlaylist(token, playlistId, body);
            return Response.status(201).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }

    @Path("/{playlistId}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId){
        try{
            TracksResponse tracksResponse = playlistsService.removeTrackFromPlaylist(token, playlistId, trackId);
            return Response.status(200).entity(tracksResponse).build();
        }catch(Exception e){
            return Response.status(400).build();
        }
    }
}
