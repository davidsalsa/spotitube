package nl.han.dea.data;

import nl.han.dea.model.Login;
import nl.han.dea.model.PlaylistDAO;

import java.util.ArrayList;

public interface Data {
    Login getLogin(String user, String password);
    ArrayList<PlaylistDAO> getPlaylists();
}
