package nl.han.dea.resource;

import nl.han.dea.model.response.PlaylistsResponse;
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
    public Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        try{
            PlaylistsResponse playlistsResponse = playlistsService.getPlaylists(token);
            return Response.status(200).entity(playlistsResponse).build();
        } catch( Exception e){
            return Response.status(400).build();
        }
    }
}
