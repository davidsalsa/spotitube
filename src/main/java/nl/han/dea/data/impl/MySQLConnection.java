package nl.han.dea.data.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.Login;
import nl.han.dea.data.dao.PlaylistDAO;
import nl.han.dea.model.Track;

import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;

@Named("MySQLConnection")
public class MySQLConnection implements Data{
    private String url = "jdbc:mysql://localhost:3306/Spotitube";
    private String user = "root";
    private String password = "Shadowdavy1";
    private Connection con;

    public MySQLConnection() {
        System.out.println(1113);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Login getLogin(String user, String password) {
        try {
            PreparedStatement prep = con.prepareStatement("SELECT u.user, u.password, u.token from user u where u.user = ? and u.password = ?");
            prep.setString(1, user);
            prep.setString(2, password);
            ResultSet res = prep.executeQuery();

            while (res.next()) {
                String login_user = res.getString("u.user");
                String login_password = res.getString("u.password");
                String login_token = res.getString("u.token");

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
    public ArrayList<PlaylistDAO> getPlaylists() {
        ArrayList<PlaylistDAO> playlistList = new ArrayList<>();
        try {
            PreparedStatement prep = con.prepareStatement("SELECT p.id, p.name, p.owner_token, p.length from playlists p;");
            ResultSet res = prep.executeQuery();
            PreparedStatement getTracks = con.prepareStatement(" SELECT * from tracksInPlaylists tp inner join tracks t on tp.trackId = t.id;");
            ResultSet trackResultSet = getTracks.executeQuery();
            while (res.next()) {
                int playlist_id = res.getInt("p.id");
                String playlist_name = res.getString("p.name");
                String playlist_owner_token = res.getString("p.owner_token");
                int playlist_length = res.getInt("p.length");

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
                    Boolean offlineAvailable = trackResultSet.getBoolean("offlineAvailable");;

                    if(track_playlist_id == playlist_id){
                        tracks.add(new Track(id, title, performer, duration, album, playcount,
                                publicationDate, description, offlineAvailable));
                    }
                }
                PlaylistDAO playlistDAO = new PlaylistDAO(playlist_id, playlist_name, playlist_owner_token, playlist_length, tracks);
                playlistList.add(playlistDAO);
            }
            trackResultSet.close();
            getTracks.close();
            prep.close();
            res.close();
            return playlistList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playlistList;
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
    public void editPlaylist(String token, int id, String name) throws SQLException{
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
            PreparedStatement prep = con.prepareStatement("SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publicationDate," +
                    "  t.description, t.offlineAvailable from tracks t WHERE t.id NOT IN (SELECT trackId FROM tracksinplaylists" +
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
            PreparedStatement prep = con.prepareStatement("SELECT  t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publicationDate," +
                    "  t.description, t.offlineAvailable from tracks t WHERE t.id IN (SELECT trackId FROM tracksinplaylists" +
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

    private void extractTrackVariablesFromPrepStatement(ArrayList<Track> tracks, ResultSet res) throws SQLException {
        while (res.next()) {
            int id = res.getInt("t.id");
            String title = res.getString("t.title");
            String performer = res.getString("t.performer");
            int duration = res.getInt("t.duration");
            String album = res.getString("t.album");
            int playcount = res.getInt("t.playcount");
            String publicationDate = res.getString("t.publicationDate");
            String description = res.getString("t.description");
            Boolean offlineAvailable = res.getBoolean("t.offlineAvailable");

            Track track = new Track(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable);
            tracks.add(track);
        }
    }
}
