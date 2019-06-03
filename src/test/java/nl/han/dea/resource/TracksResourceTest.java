package nl.han.dea.resource;

import nl.han.dea.model.Track;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.TracksService;
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
public class TracksResourceTest {
    @InjectMocks
    private TracksResource tracksResource;

    @Mock
    private TracksService tracksService;

    private TracksResponse tracksResponse;

    @Before
    public void setUp(){
        ArrayList<Track> tracks = new ArrayList<>();
        tracksResponse = new TracksResponse(tracks);
    }

    @Test
    public void shouldReturnGetTracksResponseWithStatus200(){
        String token = "1234-1234";
        int forPlaylist = 1;
        when(tracksService.getTracks(token, forPlaylist)).thenReturn(tracksResponse);
        Response response = tracksResource.getTracks(token, forPlaylist);

        assertEquals(200, response.getStatus());
    }

    @Test(expected = Exception.class)
    public void shouldReturnGetTracksResponseWithStatus400(){
        String token = "1234-1234";
        int forPlaylist = 1;
        when(tracksService.getTracks(token, forPlaylist)).thenThrow(new Exception());
        Response response = tracksResource.getTracks(token, forPlaylist);

        assertEquals(400, response.getStatus());
    }
}
