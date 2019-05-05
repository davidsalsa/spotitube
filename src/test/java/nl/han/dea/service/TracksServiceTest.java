package nl.han.dea.service;

import nl.han.dea.data.Data;
import nl.han.dea.model.Login;
import nl.han.dea.model.Track;
import nl.han.dea.model.request.LoginRequest;
import nl.han.dea.model.response.LoginResponse;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.impl.LoginServiceImpl;
import nl.han.dea.service.impl.TracksServiceImpl;
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
public class TracksServiceTest {
    @InjectMocks
    private TracksServiceImpl tracksService;

    @Mock
    private Data data;
    private TracksResponse expected;
    private ArrayList<Track> tracks;
    private String token;
    private int forPlaylist;


    @Before
    public void setUp(){
        tracks = new ArrayList<>();
        token = "token";
        forPlaylist = 0;
        expected = new TracksResponse(tracks);
    }

    @Test
    public void getTracksshouldReturnTracksResponse(){
        when(data.getTracks(token, forPlaylist)).thenReturn(tracks);
        TracksResponse actual = tracksService.getTracks(token, forPlaylist);

        assertEquals(expected.getTracks(), actual.getTracks());
    }

}
