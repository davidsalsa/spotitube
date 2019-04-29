package nl.han.dea.model.response;

import java.util.ArrayList;

public class TracksResponse {
    ArrayList tracks;

    public TracksResponse(){}
    public TracksResponse(ArrayList tracks){
        this.tracks = tracks;
    }

    public ArrayList getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList tracks) {
        this.tracks = tracks;
    }

}
