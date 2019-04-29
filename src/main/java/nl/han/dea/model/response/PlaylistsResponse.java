package nl.han.dea.model.response;


import nl.han.dea.model.Playlist;

import java.util.ArrayList;

public class PlaylistsResponse {
    public ArrayList<Playlist> playlists;
    public int length;
    public PlaylistsResponse(){}
    public PlaylistsResponse(ArrayList<Playlist> playlists, int length){
        this.playlists = playlists;
        this.length = length;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
