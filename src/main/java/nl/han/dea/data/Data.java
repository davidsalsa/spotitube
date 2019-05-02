package nl.han.dea.data;

import nl.han.dea.model.Login;
import nl.han.dea.data.dao.PlaylistDAO;
import nl.han.dea.model.Track;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Data {
    Login getLogin(String user, String password);
    ArrayList<PlaylistDAO> getPlaylists();
    void deletePlaylist(String token, int id) throws SQLException;
    void addPlaylist(String token, String name) throws SQLException;
    void editPlaylist(String token, int id, String name) throws SQLException;
    ArrayList<Track> getTracks(String token, int forPlay);
    ArrayList<Track> getTracksFromPlaylist(String token, int playlistId);
    void addTracksToPlaylist(String token, int playlistId, int trackId, boolean offlineAvailable);
    void removeTracksFromPlaylist(String token, int playlistId, int trackId);
}
