package nl.han.dea.data.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.Login;
import nl.han.dea.model.Track;
import nl.han.dea.model.request.PlaylistObject;

import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;

@Named("MSSQLConnection")
public class MSSQLConnection implements Data {
    private String url = "jdbc:sqlserver://localhost:1433;";
    private String user = "sa";
    private String password = "Shadowdavy1";
    private Connection con;

    public MSSQLConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            stmt.execute("USE Spotitube");

        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(1113);
    }


    @Override
    public Login getLogin(String user, String password) {
        try {
            PreparedStatement prep = con.prepareStatement("SELECT name, password, token from users WHERE name = ? AND password = ?");
            prep.setString(1, user);
            prep.setString(2, password);
            ResultSet res = prep.executeQuery();

            while (res.next()) {
                String login_user = res.getString("name");
                String login_password = res.getString("password");
                String login_token = res.getString("token");

                Login login = new Login(login_user, login_password, login_token);
                prep.close();
                return login;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<PlaylistObject> getPlaylists() {
        ArrayList<PlaylistObject> playlistObjectList = new ArrayList<>();
        try {
            PreparedStatement prep = con.prepareStatement("SELECT p.id, p.name, p.owner_token, p.length from playlists p;");
            ResultSet res = prep.executeQuery();
            PreparedStatement getTracks = con.prepareStatement(" SELECT * from tracksInPlaylists tp inner join tracks t on tp.trackId = t.id;");
            ResultSet trackResultSet = getTracks.executeQuery();
            while (res.next()) {
                int playlist_id = res.getInt("id");
                String playlist_name = res.getString("name");
                String playlist_owner_token = res.getString("owner_token");
                int playlist_length = res.getInt("length");

                ArrayList<Track> tracks = new ArrayList<>();

                while(trackResultSet.next()){
                    int track_playlist_id = trackResultSet.getInt("playlistId");
                    int id = trackResultSet.getInt("id");
                    String title = trackResultSet.getString("title");
                    String performer = trackResultSet.getString("performer");
                    int duration= trackResultSet.getInt("duration");;
                    String album = trackResultSet.getString("album");
                    int playcount = trackResultSet.getInt("playcount");;
                    String publicationDate = trackResultSet.getString("publicationDate");
                    String description = trackResultSet.getString("description");
                    Boolean offlineAvailable = trackResultSet.getBoolean("offlineAvailable");

                    if(track_playlist_id == playlist_id){
                        tracks.add(new Track(id, title, performer, duration, album, playcount,
                                publicationDate, description, offlineAvailable));
                    }
                }
                PlaylistObject playlistObject = new PlaylistObject(playlist_id, playlist_name, playlist_owner_token, playlist_length, tracks);
                playlistObjectList.add(playlistObject);
            }
            trackResultSet.close();
            getTracks.close();
            prep.close();
            res.close();
            return playlistObjectList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playlistObjectList;
    }

    @Override
    public void deletePlaylist(String token, int id) throws SQLException {
        try{
            PreparedStatement prep = con.prepareStatement("DELETE FROM playlists where owner_token = ? and id = ?;");
            prep.setString(1, token);
            prep.setInt(2, id);
            prep.execute();
            prep.close();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void addPlaylist(String token, String name) throws SQLException {
        try{
            PreparedStatement prep = con.prepareStatement("INSERT INTO playlists (name, owner_token, length) VALUES (?, ?, 0)");
            prep.setString(1, name);
            prep.setString(2, token);
            prep.execute();
            prep.close();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void editPlaylist(String token, int id, String name) throws SQLException {
        try{
            PreparedStatement prep = con.prepareStatement("UPDATE playlists SET name=? WHERE owner_token=? AND id=?");
            prep.setString(1, name);
            prep.setString(2, token);
            prep.setInt(3, id);
            prep.execute();
            prep.close();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ArrayList<Track> getTracks(String token, int forPlaylist) {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            PreparedStatement prep = con.prepareStatement("SELECT id, title, performer, duration, album, playcount, publicationDate," +
                    "  description, offlineAvailable from tracks WHERE id NOT IN (SELECT trackId FROM tracksinplaylists" +
                    "                                                                WHERE playlistId = ?);");
            prep.setInt(1, forPlaylist);
            ResultSet res = prep.executeQuery();

            extractTrackVariablesFromPrepStatement(tracks, res);
            prep.close();
            res.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracks;
    }

    @Override
    public ArrayList<Track> getTracksFromPlaylist(String token, int forPlaylist) {
        ArrayList<Track> tracks = new ArrayList<>();
        try {
            PreparedStatement prep = con.prepareStatement("SELECT  id, title, performer, duration, album, playcount, publicationDate," +
                    "  description, offlineAvailable from tracks WHERE id IN (SELECT trackId FROM tracksinplaylists" +
                    "                                                                WHERE playlistId = ?);");
            prep.setInt(1, forPlaylist);
            ResultSet res = prep.executeQuery();

            extractTrackVariablesFromPrepStatement(tracks, res);
            prep.close();
            res.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracks;
    }

    @Override
    public void addTracksToPlaylist(String token, int playlistId, int trackId, boolean offlineAvailable) {
        try{
            PreparedStatement prep = con.prepareStatement("INSERT INTO tracksinplaylists (playlistId, trackId) VALUES (?,?)");
            prep.setInt(1, playlistId);
            prep.setInt(2, trackId);
            prep.execute();
            prep.close();
            PreparedStatement update_track = con.prepareStatement("UPDATE tracks SET offlineAvailable=? WHERE id=?");
            update_track.setBoolean(1, offlineAvailable);
            update_track.setInt(2, trackId);
            update_track.execute();
            update_track.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeTracksFromPlaylist(String token, int playlistId, int trackId) {
        try{
            PreparedStatement prep = con.prepareStatement("DELETE FROM tracksinplaylists where playlistId = ? AND trackId =? ;");
            prep.setInt(1, playlistId);
            prep.setInt(2, trackId);
            prep.execute();
            prep.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void extractTrackVariablesFromPrepStatement(ArrayList<Track> tracks, ResultSet res) throws SQLException {
        while (res.next()) {
            int id = res.getInt("id");
            String title = res.getString("title");
            String performer = res.getString("performer");
            int duration = res.getInt("duration");
            String album = res.getString("album");
            int playcount = res.getInt("playcount");
            String publicationDate = res.getString("publicationDate");
            String description = res.getString("description");
            Boolean offlineAvailable = res.getBoolean("offlineAvailable");

            Track track = new Track(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable);
            tracks.add(track);
        }
    }
}
