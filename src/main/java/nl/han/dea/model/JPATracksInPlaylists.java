package nl.han.dea.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tracksInPlaylists")
public class JPATracksInPlaylists {
    private int playlistId;
    private int trackId;

    public JPATracksInPlaylists(int playlistId, int trackId){
        this.playlistId = playlistId;
        this.trackId = trackId;
    }

    public JPATracksInPlaylists() {
    }

    @Column(name = "playlistId")
    @Id
    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Column(name = "trackId")
    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }


}
