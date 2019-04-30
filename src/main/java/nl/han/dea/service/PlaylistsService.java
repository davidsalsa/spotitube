package nl.han.dea.service;


import nl.han.dea.model.response.PlaylistsResponse;

public interface PlaylistsService {
    public PlaylistsResponse getPlaylists(String token);
    public PlaylistsResponse deletePlaylist(String token, int id);
    public PlaylistsResponse addPlaylist(String token, String name);
    public PlaylistsResponse editPlaylist(String token, int id);
}
