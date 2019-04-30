package nl.han.dea.resource;

import nl.han.dea.model.Track;
import nl.han.dea.model.response.TracksResponse;
import nl.han.dea.service.TracksService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/tracks")
public class TracksResource{

    public ArrayList<Track> tracks = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TracksResponse tracksResponse(@QueryParam("token") String token, @QueryParam("forPlayList") int forPlayList) {
        try{
            tracks.add(new Track(3,"Ocean and a rock", "Lisa Hannigan",  337, "Sea sew",  0,null,null, false));
            tracks.add(new Track(4,"So Long, Marianne", "Leonard Cohen",  546, "Songs of Leonard Cohen",  0,null,null, false));
            tracks.add(new Track(5,"One", "Metallica",  423, "Sea sew",  37,"1-11-2001","Long version", true));
            return new TracksResponse(tracks);
        }catch(Exception e){
            return null;
        }
    }
}
