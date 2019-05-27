package nl.han.dea.service;

import nl.han.dea.data.Data;
import nl.han.dea.model.request.PlaylistObject;
import nl.han.dea.model.Track;
import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.impl.PlaylistsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistsServiceTest {
    @InjectMocks
    private PlaylistsServiceImpl playlistsService;

    @Mock
    private Data data;
    private ArrayList<Track> tracks;
    private ArrayList<PlaylistObject> playlistObjects;
    private PlaylistsResponse playlistsResponse;
    private String token;
    private String name;
    private int playlistId;
    private int trackId;

    @Before
    public void setUp(){
        playlistObjects = new ArrayList<>();
        tracks = new ArrayList<>();
        token = "token";
        name = "name";
        playlistId = 0;
        trackId = 0;
        playlistsResponse = new PlaylistsResponse();
    }


    @Test
    public void getPlaylistsShouldReturnPlaylistsResponse() {
    when(data.getPlaylists()).thenReturn(playlistObjects);
        PlaylistsResponse actual =  playlistsService.getPlaylists(token);
        assertEquals(playlistsResponse.getClass(), actual.getClass());
    }

    @Test
    public void getPlaylistsOwnerShouldReturnTrueWhenTokensAreEquals() {
        boolean expected = true;
        playlistObjects.add(new PlaylistObject(playlistId, name, token, 0, tracks)); //playlistDAO with token
        when(data.getPlaylists()).thenReturn(playlistObjects);
        PlaylistsResponse response =  playlistsService.getPlaylists(token); //same token
        boolean actual = response.playlists.get(0).owner;

        assertEquals(expected, actual);
    }

    @Test
    public void getPlaylistsOwnerShouldReturnFalseWhenTokensDiffer() {
        boolean expected = false;
        String token2 = "different";
        playlistObjects.add(new PlaylistObject(playlistId, name, token, 0, tracks)); //playlistDAO with token
        when(data.getPlaylists()).thenReturn(playlistObjects);
        PlaylistsResponse response =  playlistsService.getPlaylists(token2); //different token
        boolean actual = response.playlists.get(0).owner;

        assertEquals(expected, actual);
    }

    @Test
    public void getPlaylistsReturnsSumOfDurations() {
        int duration1 = 15;
        int duration2 = 25;
        int duration3 = 33;
        int expected = duration1 + duration2 + duration3;
        playlistObjects.add(new PlaylistObject(playlistId, name, token, duration1, tracks));
        playlistObjects.add(new PlaylistObject(playlistId, name, token, duration2, tracks));
        playlistObjects.add(new PlaylistObject(playlistId, name, token, duration3, tracks));
        when(data.getPlaylists()).thenReturn(playlistObjects);
        PlaylistsResponse response =  playlistsService.getPlaylists(token); //different token
        int actual = response.length;

        assertEquals(expected, actual);
    }

    @Test
    public void addPlaylistReturnsPlaylistsResponse() {
        String body =  "{\"id\": 1, \"name\": \"name\", \"owner\": false}";
        PlaylistsResponse actual = playlistsService.addPlaylist(token, body);
        assertEquals(playlistsResponse.getClass(), actual.getClass());
    }

    @Test
    public void deletePlaylistReturnsPlaylistsResponse() {
        PlaylistsResponse actual = playlistsService.deletePlaylist(token, playlistId);
        assertEquals(playlistsResponse.getClass(), actual.getClass());
    }

    @Test
    public void editPlaylistReturnsPlaylistsResponse() {
        String body =  "{\"id\": 1, \"name\": \"name\", \"owner\": false}";
        PlaylistsResponse actual = playlistsService.editPlaylist(token, playlistId, body);
        assertEquals(playlistsResponse.getClass(), actual.getClass());
    }

    @Test
    public void getTracksFromPlaylistShouldReturnTracksResponse(){
        TracksResponse expected = new TracksResponse(tracks);
        when(data.getTracksFromPlaylist(token, playlistId)).thenReturn(tracks);
        TracksResponse actual = playlistsService.getTracksFromPlaylist(token, playlistId);

        assertEquals(expected.getTracks(), actual.getTracks());
        assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void addTracksToPlaylistShouldReturnTracksResponse(){
        TracksResponse expected = new TracksResponse(tracks);
        String body =  "{\"id\":1,\"title\":\"Ocean and a rock\",\"performer\":\"Lisa Hannigan\",\"duration\":0,\"offlineAvailable\":false}\n";
        TracksResponse actual = playlistsService.addTrackToPlaylist(token, playlistId, body);

        assertEquals(expected.getTracks(), actual.getTracks());
        assertEquals(expected.getClass(), actual.getClass());
    }


    @Test
    public void removeTracksFromPlaylistShouldReturnTracksResponse(){
        TracksResponse expected = new TracksResponse(tracks);
        TracksResponse actual = playlistsService.removeTrackFromPlaylist(token, playlistId, trackId);

        assertEquals(expected.getTracks(), actual.getTracks());
        assertEquals(expected.getClass(), actual.getClass());
    }
}
