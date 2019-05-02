package nl.han.dea.service;


import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.model.response.TracksResponse;

public interface PlaylistsService {
    PlaylistsResponse getPlaylists(String token);
    PlaylistsResponse deletePlaylist(String token, int id);
    PlaylistsResponse addPlaylist(String token, String body);
    PlaylistsResponse editPlaylist(String token, int id, String body);
    TracksResponse getTracksFromPlaylist(String token, int playlistId);
    TracksResponse addTrackToPlaylist(String token, int playlistId, String body);
    TracksResponse removeTrackFromPlaylist(String token, int playlistId, int trackId);
}
