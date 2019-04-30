package nl.han.dea.service.impl;

import nl.han.dea.model.Playlist;
import nl.han.dea.model.response.PlaylistsResponse;
import nl.han.dea.service.PlaylistsService;
import org.codehaus.jackson.map.ObjectMapper;

import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Named("PlaylistsServiceImpl")
public class PlaylistsServiceImpl implements PlaylistsService{

    private static ArrayList<Playlist> data = new ArrayList<>();;

    public void initPlaylists(){
        Playlist death_metal = new Playlist(1, "Death metal",  true, new ArrayList());
        Playlist pop = new Playlist(2, "pop",  true, new ArrayList());
        data.add(death_metal);
        data.add(pop);
    }

    @Override
    public PlaylistsResponse getPlaylists(String token) {
        if(data.isEmpty()) {
            initPlaylists();
        }
        ArrayList<Playlist> playlistList = new ArrayList<>();
        for(Playlist playlist: data){
            Playlist retrievedPlaylist = new Playlist(playlist.id, playlist.name, playlist.owner, playlist.tracks);
            playlistList.add(retrievedPlaylist);
        }

        return new PlaylistsResponse(playlistList, 123445);
    }

    @Override
    public PlaylistsResponse deletePlaylist(String token, int id) {
        for (Playlist playlist: data) {
            if(playlist.getId() == id){
                data.remove(playlist);
            }
        }
        return new PlaylistsResponse(data, 123445);
    }


    @Override
    public PlaylistsResponse addPlaylist(String token, String name) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String,Object> map = mapper.readValue(name, Map.class);
            Playlist newPlaylist = new Playlist((Integer)map.get("id"), (String) map.get("name"),(Boolean) map.get("owner"), new ArrayList());
            data.add(newPlaylist);
            return new PlaylistsResponse(data, 123445);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public PlaylistsResponse editPlaylist(String token, int id) {
        return null;
    }
}
