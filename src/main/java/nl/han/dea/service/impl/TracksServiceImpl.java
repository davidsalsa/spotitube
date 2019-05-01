package nl.han.dea.service.impl;

import nl.han.dea.model.Track;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.TracksService;

import javax.inject.Named;
import java.util.ArrayList;

@Named("TracksServiceImpl")
public class TracksServiceImpl implements TracksService {

    private static ArrayList<Track> data = new ArrayList<>();

    private void initTracks(){
        data.add(new Track(3,"Ocean and a rock", "Lisa Hannigan",  337, "Sea sew",  0,null,null, false));
        data.add(new Track(4,"So Long, Marianne", "Leonard Cohen",  546, "Songs of Leonard Cohen",  0,null,null, false));
        data.add(new Track(5,"One", "Metallica",  423, "Sea sew",  37,"1-11-2001","Long version", true));
    }

    @Override
    public TracksResponse getTracks(String token, int forPlayList) {
        if(data.isEmpty()) {
            initTracks();
        }

        ArrayList<Track> tracks = new ArrayList<>();
        for(Track track: data){
            Track retrievedTrack = new Track(track.getId(), track.getTitle(), track.getPerformer(),
                    track.getDuration(), track.getAlbum(), track.getPlaycount(),
                    track.getPublicationDate(), track.getDescription(),
                    track.isOfflineAvailable());
            tracks.add(retrievedTrack);
        }
        return new TracksResponse(tracks);
    }

    @Override
    public TracksResponse getTracksFromPlaylist(String token, int playlistId) {
        ArrayList<Track> tracks = new ArrayList<>();
        for(Track track : data){
            if(playlistId == 1){
                tracks.add(track);
            }
        }
        return new TracksResponse(tracks);
    }

    @Override
    public TracksResponse addTrackToPlaylist(String token, int playlistId, String body) {
        return null;
    }

    @Override
    public TracksResponse removeTrackFromPlaylist(String token, int playlistId, int trackId) {
        return null;
    }
}
