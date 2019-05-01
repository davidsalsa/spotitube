package nl.han.dea.service;

import nl.han.dea.model.response.TracksResponse;

public interface TracksService {
    public TracksResponse getTracks(String token, int forPlayList);
    public TracksResponse getTracksFromPlaylist(String token, int playlistId);
    public TracksResponse addTrackToPlaylist(String token, int playlistId, String body);
    public TracksResponse removeTrackFromPlaylist(String token, int playlistId, int trackId);
}
