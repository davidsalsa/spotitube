package nl.han.dea.service.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.Playlist;
import nl.han.dea.model.PlaylistDAO;
import nl.han.dea.model.Track;
import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.service.PlaylistsService;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
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
//        for (Playlist playlist: data) {
//            if(playlist.getId() == id){
//                data.remove(playlist);
//            }
//        }
//        return new PlaylistsResponse(data, 123445);
        return null;
    }


    @Override
    public PlaylistsResponse addPlaylist(String token, String body) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Map<String,Object> map = mapper.readValue(body, Map.class);
//            Playlist newPlaylist = new Playlist((Integer)map.get("id"), (String) map.get("name"), true, new ArrayList<Track>());
//            data.add(newPlaylist);
//            return new PlaylistsResponse(data, 123445);
//        } catch (IOException e) {
//            return null;
//        }
        return null;
    }

    @Override
    public PlaylistsResponse editPlaylist(String token, int id, String body) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Map<String,Object> map = mapper.readValue(body, Map.class);
//            for(Playlist playlist:data){
//                if(playlist.getId() == id){
//                    playlist.setName((String) map.get("name"));
//                }
//            }
//            return new PlaylistsResponse(data, 123445);
//        } catch (IOException e) {
//            return null;
//        }
        return null;
    }
}
