package nl.han.dea.resource;

import nl.han.dea.model.Playlist;
import nl.han.dea.model.Track;
import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.PlaylistsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistsResourceTest {
    @InjectMocks
    private PlaylistsResource playlistsResource;

    @Mock
    private PlaylistsService playlistsService;

    private PlaylistsResponse playlistsResponse;
    private TracksResponse tracksResponse;

    @Before
    public void setUp(){
        ArrayList<Playlist> playlists = new ArrayList<>();
        playlistsResponse = new PlaylistsResponse(playlists,1);
        tracksResponse = new TracksResponse();
    }


    @Test
    public void shouldReturnGetPlaylistsResponseWithStatus200(){
        String token = "1234-1234";
        when(playlistsService.getPlaylists(token)).thenReturn(playlistsResponse);
        Response response = playlistsResource.getPlaylists(token);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldReturnAddPlaylistsResponseWithStatus201(){
        String token = "1234-1234";
        String body = "";
        when(playlistsService.addPlaylist(token, body)).thenReturn(playlistsResponse);
        playlistsResponse.playlists.add(new Playlist(0, "name", false,new ArrayList<>())); //new playlist has a name, otherwise SQLexception occurs.
        Response response = playlistsResource.addPlaylist(token, body);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldReturnAddPlaylistsResponseWithStatus400(){ // when playlistsResponse.playlists.get(0).getName().isEmpty() is true SQLException occurs.
        String token = "1234-1234";
        String body = "";
        when(playlistsService.addPlaylist(token, body)).thenReturn(playlistsResponse);
        Response response = playlistsResource.addPlaylist(token, body);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void shouldReturnDeletePlaylistsResponseWithStatus200(){
        String token = "1234-1234";
        int id = 0;
        when(playlistsService.deletePlaylist(token, id)).thenReturn(playlistsResponse);
        Response response = playlistsResource.deletePlaylist(token, id);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldReturnEditPlaylistsResponseWithStatus200(){
        String token = "1234-1234";
        String body = "";
        int id = 0;
        when(playlistsService.editPlaylist(token, id, body)).thenReturn(playlistsResponse);
        Response response = playlistsResource.editPlaylist(token, id, body);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldReturnGetTracksFromPlaylistResponseWithStatus200(){
        String token = "1234-1234";
        int id = 0;
        when(playlistsService.getTracksFromPlaylist(token, id)).thenReturn(tracksResponse);
        Response response = playlistsResource.getTracksFromPlaylist(token, id);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldReturnaddTrackToPlaylistResponseWithStatus201(){
        String token = "1234-1234";
        String body = "";
        int id = 0;
        when(playlistsService.addTrackToPlaylist(token, id, body)).thenReturn(tracksResponse);
        Response response = playlistsResource.addTrackToPlaylist(token, id, body);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldReturnRemoveTrackFromPlaylistResponseWithStatus200(){
        String token = "1234-1234";
        int playlistId = 0;
        int trackId = 0;
        when(playlistsService.removeTrackFromPlaylist(token, playlistId, trackId)).thenReturn(tracksResponse);
        Response response = playlistsResource.removeTrackFromPlaylist(token, playlistId, trackId);

        assertEquals(200, response.getStatus());
    }
}
