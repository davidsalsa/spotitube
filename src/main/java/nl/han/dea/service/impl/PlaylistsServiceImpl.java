package nl.han.dea.service.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.request.PlaylistObject;
import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.model.response.TracksResponse;
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
    @Named("MSSQLConnection")
    Data data;

    @Override
    public PlaylistsResponse getPlaylists(String token) {
        int total_length=0;
        ArrayList<nl.han.dea.model.Playlist> playlistList = new ArrayList<>();
        for(PlaylistObject playlistObject : data.getPlaylists()){
            if(playlistObject.owner_token.equals(token)) { // if owner, set owner true
                nl.han.dea.model.Playlist retrievedPlaylist = new nl.han.dea.model.Playlist(playlistObject.id, playlistObject.name, true, playlistObject.tracks);
                playlistList.add(retrievedPlaylist);
            } else { // if not owner, set owner false
                nl.han.dea.model.Playlist retrievedPlaylist = new nl.han.dea.model.Playlist(playlistObject.id, playlistObject.name, false, playlistObject.tracks);
                playlistList.add(retrievedPlaylist);
            }
            total_length += playlistObject.length;
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
            Map map = mapper.readValue(body, Map.class);
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
            Map map = mapper.readValue(body, Map.class);
            data.editPlaylist(token, id, (String) map.get("name"));
            return getPlaylists(token);
        } catch (IOException e) {
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getPlaylists(token);
    }

    @Override
    public TracksResponse getTracksFromPlaylist(String token, int playlistId) {
        return new TracksResponse(data.getTracksFromPlaylist(token, playlistId));
    }

    @Override
    public TracksResponse addTrackToPlaylist(String token, int playlistId, String body) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(body, Map.class);
            data.addTracksToPlaylist(token, playlistId, (int) map.get("id"), (Boolean) map.get("offlineAvailable"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getTracksFromPlaylist(token, playlistId);
    }

    @Override
    public TracksResponse removeTrackFromPlaylist(String token, int playlistId, int trackId) {
        data.removeTracksFromPlaylist(token, playlistId, trackId);
        return getTracksFromPlaylist(token, playlistId);
    }
}
