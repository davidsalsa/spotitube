package nl.han.dea.data.impl;

import nl.han.dea.data.Data;
import nl.han.dea.model.Login;
import nl.han.dea.model.PlaylistDAO;
import nl.han.dea.model.Track;

import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            PreparedStatement prep = con.prepareStatement("SELECT p.id, p.name, p.owner_token, p.length from playlists p");
            ResultSet res = prep.executeQuery();
            PreparedStatement getTracks = con.prepareStatement(" SELECT * from tracksInPlaylists tp inner join tracks t on tp.trackId = t.id");
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
}
