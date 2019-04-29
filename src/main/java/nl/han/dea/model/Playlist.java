package nl.han.dea.model;

import java.util.ArrayList;

public class Playlist {
    public int id;
    public String name;
    public boolean owner;
    public ArrayList tracks;

    public Playlist(){}
    public Playlist(int id, String name, boolean owner, ArrayList tracks){
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public ArrayList getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList tracks) {
        this.tracks = tracks;
    }
}
