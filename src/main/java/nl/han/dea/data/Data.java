package nl.han.dea.data;

import nl.han.dea.model.Login;
import nl.han.dea.data.dao.PlaylistDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Data {
    Login getLogin(String user, String password);
    ArrayList<PlaylistDAO> getPlaylists();
    void deletePlaylist(String token, int id) throws SQLException;
    void addPlaylist(String token, String name) throws SQLException;
    void editPlaylist(String token, int id, String name) throws SQLException;
}
