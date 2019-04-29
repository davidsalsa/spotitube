package nl.han.dea.service;

import nl.han.dea.model.response.TracksResponse;

public interface TracksService {
    public TracksResponse tracksResponse(String token, int forPlayList);
}
