package nl.han.dea.service.impl;

import nl.han.dea.model.Playlist;
import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.service.PlaylistsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/playlists")
public class PlaylistsServiceImpl implements PlaylistsService {

    private ArrayList<Playlist> playlists = new ArrayList<>();

    public void initPlaylists(){
        Playlist death_metal = new Playlist(1, "Death metal",  true, new ArrayList());
        Playlist pop = new Playlist(2, "pop",  true, new ArrayList());
        playlists.add(death_metal);
        playlists.add(pop);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlaylistsResponse getPlaylists(@QueryParam("token") String token) {
        try{
            initPlaylists();
            return new PlaylistsResponse(playlists, 1234);
        } catch( Exception e){
            throw e;
        }
    }

    @Override
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public PlaylistsResponse deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        try{
            initPlaylists();
            for(Playlist playlist : playlists){
                if(playlist.id == id){
                    playlists.remove(playlist);
                }
            }
            return new PlaylistsResponse(playlists, 6445);
        } catch( Exception e){
            throw e;
        }
    }

    @Override
    @Path("/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public PlaylistsResponse addPlaylist(@QueryParam("token") String token) {
        try{
            initPlaylists();
            return new PlaylistsResponse(playlists, 6445);
        } catch( Exception e){
            throw e;
        }
    }

    @Override
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public PlaylistsResponse editPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        try{
            initPlaylists();
            return new PlaylistsResponse(playlists, 6445);
        } catch( Exception e){
            throw e;
        }
    }
}
