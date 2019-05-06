package nl.han.dea.service;

import nl.han.dea.model.response.TracksResponse;

public interface TracksService {
    TracksResponse getTracks(String token, int forPlaylist);
}
