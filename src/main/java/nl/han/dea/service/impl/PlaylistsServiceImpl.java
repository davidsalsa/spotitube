package nl.han.dea.service.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.Playlist;
import nl.han.dea.data.dao.PlaylistDAO;
import nl.han.dea.model.Track;
import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.service.PlaylistsService;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Named("PlaylistsServiceImpl")
public class PlaylistsServiceImpl implements PlaylistsService{

    @Inject
    //@Named("MysqlConnection")
    @Named("MySQLConnection")
    Data data;

    @Override
    public PlaylistsResponse getPlaylists(String token) {
        int total_length=0;
        ArrayList<Playlist> playlistList = new ArrayList<>();
        for(PlaylistDAO playlist: data.getPlaylists()){
            if(playlist.owner_token.equals(token)) { // if owner, set owner true
                Playlist retrievedPlaylist = new Playlist(playlist.id, playlist.name, true, playlist.tracks);
                playlistList.add(retrievedPlaylist);
            } else { // if not owner, set owner false
                Playlist retrievedPlaylist = new Playlist(playlist.id, playlist.name, false, playlist.tracks);
                playlistList.add(retrievedPlaylist);
            }
            total_length += playlist.length;
        }

        return new PlaylistsResponse(playlistList, total_length);
    }

    @Override
    public PlaylistsResponse deletePlaylist(String token, int id) {
        try {
            data.deletePlaylist(token, id);
            return getPlaylists(token);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getPlaylists(token);
    }


    @Override
    public PlaylistsResponse addPlaylist(String token, String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,Object> map = mapper.readValue(body, Map.class);
            data.addPlaylist(token,(String) map.get("name"));
            return getPlaylists(token);
        } catch (IOException e) {
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getPlaylists(token);
    }

    @Override
    public PlaylistsResponse editPlaylist(String token, int id, String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,Object> map = mapper.readValue(body, Map.class);
            data.editPlaylist(token, id, (String) map.get("name"));
            return getPlaylists(token);
        } catch (IOException e) {
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getPlaylists(token);
    }
}
