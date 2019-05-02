package nl.han.dea.data.dao;

import nl.han.dea.model.Track;

import java.util.ArrayList;
//New DAO so the database can store and retrieve the owner_token and length per playlist
public class PlaylistDAO {
    public int id;
    public String name;
    public String owner_token;
    public int length;
    public ArrayList<Track> tracks;

    public PlaylistDAO(int id, String name, String owner_token, int length, ArrayList<Track> tracks){
        this.id = id;
        this.name = name;
        this.owner_token = owner_token;
        this.length = length;
        this.tracks = tracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner_token() {
        return owner_token;
    }

    public void setOwner_token(String owner_token) {
        this.owner_token = owner_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getLength() {
        return length;
    }

    public void setTracks(int length) {
        this.length = length;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
