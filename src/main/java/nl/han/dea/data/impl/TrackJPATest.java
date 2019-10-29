package nl.han.dea.data.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.JPATrack;
import nl.han.dea.model.Login;
import nl.han.dea.model.Track;
import nl.han.dea.model.request.PlaylistObject;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;

@Named("JPAMySQLConnection")
public class TrackJPATest implements Data {


    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Spotitube");
        EntityManager em = factory.createEntityManager();
        ArrayList<Track> tracks = new ArrayList<>();
        int id = 2;
        String sql = "SELECT t from JPATrack t WHERE t.id NOT IN (SELECT t FROM JPATracksInPlaylists t WHERE t.playlistId = "+ id + ")";
        Query query = em.createQuery(sql);

        for(Object track : query.getResultList()){
            tracks.add((JPATrack) track);
        }

        System.out.println(tracks);

        for(Track track : tracks){
            System.out.println(track.getAlbum());
        }

        em.close();
        factory.close();

    }

    @Override
    public ArrayList<Track> getTracks(String token, int forPlaylist) {
        ArrayList<Track> tracks = new ArrayList<>();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Spotitube");
        EntityManager em = factory.createEntityManager();

        String sql = "SELECT t from JPATrack t WHERE t.id NOT IN (SELECT t FROM JPATracksInPlaylists t WHERE t.playlistId = "+ forPlaylist + ")";
        Query query = em.createQuery(sql);

        for(Object track : query.getResultList()){
            JPATrack newTrack = (JPATrack) track;
            tracks.add(newTrack);
        }

        em.close();
        factory.close();
        return tracks;
    }

    @Override
    public Login getLogin(String user, String password) {
        return null;
    }

    @Override
    public ArrayList<PlaylistObject> getPlaylists() {
        return null;
    }

    @Override
    public void deletePlaylist(String token, int id) throws SQLException {

    }

    @Override
    public void addPlaylist(String token, String name) throws SQLException {

    }

    @Override
    public void editPlaylist(String token, int id, String name) throws SQLException {

    }

    @Override
    public ArrayList<Track> getTracksFromPlaylist(String token, int forPlaylist) {
        return null;
    }

    @Override
    public void addTracksToPlaylist(String token, int playlistId, int trackId, boolean offlineAvailable) {

    }

    @Override
    public void removeTracksFromPlaylist(String token, int playlistId, int trackId) {

    }
}
