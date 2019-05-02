package nl.han.dea.service;

import nl.han.dea.model.response.TracksResponse;

import java.io.IOException;

public interface TracksService {
    public TracksResponse getTracks(String token, int forPlaylist);
}
