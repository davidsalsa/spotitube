DROP DATABASE IF EXISTS Spotitube;
CREATE DATABASE Spotitube;
USE Spotitube;

-- user table, token is unique per user
CREATE TABLE user (
                      user varchar(255) NOT NULL,
                      password varchar(255) NOT NULL,
                      token varchar(14) NOT NULL PRIMARY KEY
);

-- playlist table, chose not to include owner, userplaylist determines the owner instead
CREATE TABLE playlists (
                           id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           name varchar(255) NOT NULL,
                           owner_token varchar(14) NOT NULL,
                           length int(11) NOT NULL,
                           CONSTRAINT fk_owner_token FOREIGN KEY (owner_token) REFERENCES user (token)  ON UPDATE CASCADE ON DELETE RESTRICT
);

-- tracks table
CREATE TABLE tracks (
                        id int(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,
                        title varchar(255) NOT NULL,
                        performer varchar(255) NOT NULL,
                        duration int(11) NOT NULL,
                        album varchar(255) NULL,
                        playcount int(11) DEFAULT NULL,
                        publicationDate date DEFAULT NULL,
                        description varchar(255) DEFAULT NULL,
                        offlineAvailable boolean NOT NULL
);

-- stores what track is in which playlist
CREATE TABLE tracksInPlaylists(
                                  playlistId int(11) NOT NULL,
                                  trackId int(11) NOT NULL,
                                  PRIMARY KEY (playlistId,trackId),
                                  CONSTRAINT fk_playlistId FOREIGN KEY (playlistId) REFERENCES playlists (id) ON UPDATE CASCADE ON DELETE CASCADE,
                                  CONSTRAINT fk_trackId FOREIGN KEY (trackId) REFERENCES tracks (id)  ON UPDATE CASCADE ON DELETE CASCADE
);

-- Stored procedure that updates playlist length everytime a track gets added. This happens for every insert/update and delete
-- after insert
DELIMITER $$
CREATE TRIGGER updatePlaylistLengthAfterInsert
    AFTER INSERT ON tracksInPlaylists
    FOR EACH ROW
BEGIN
    UPDATE playlists p
        inner join tracksInPlaylists tp
    SET p.length = (SELECT SUM(t.duration) FROM tracks t INNER JOIN tracksInPlaylists tp where t.id = tp.trackId and p.id = tp.playlistId)
    WHERE p.id = tp.playlistId;
END$$
DELIMITER ;
-- after delete
DELIMITER $$
CREATE TRIGGER updatePlaylistLengthAfterDelete
    AFTER DELETE ON tracksInPlaylists
    FOR EACH ROW
BEGIN
    UPDATE playlists p
        inner join tracksInPlaylists tp
    SET p.length = (SELECT SUM(t.duration) FROM tracks t INNER JOIN tracksInPlaylists tp where t.id = tp.trackId and p.id = tp.playlistId)
    WHERE p.id = tp.playlistId;
END$$
DELIMITER ;
-- after update
DELIMITER $$
CREATE TRIGGER updatePlaylistLengthAfterUpdate
    AFTER UPDATE ON tracksInPlaylists
    FOR EACH ROW
BEGIN
    UPDATE playlists p
        inner join tracksInPlaylists tp
    SET p.length = (SELECT SUM(t.duration) FROM tracks t INNER JOIN tracksInPlaylists tp where t.id = tp.trackId and p.id = tp.playlistId)
    WHERE p.id = tp.playlistId;
END$$
DELIMITER ;


INSERT INTO user (user, password,  token) VALUES
('meron', 'meron', '0417-d07a-a5e7'),
('dennis', 'dennis', '0418-d07a-a5e7'),
('michel', 'michel',  '0419-d07a-a5e7');

INSERT INTO playlists (name, owner_token, length) VALUES
('Death metal', '0417-d07a-a5e7', 0),
('pop', '0418-d07a-a5e7', 0);

INSERT INTO tracks (title, performer, duration, album, playcount, publicationDate, description, offlineAvailable) VALUES
('Ocean and a rock', 'Lisa Hannigan',  337, 'Sea sew',  0,null,null, false),
('So Long, Marianne', 'Leonard Cohen',  546, 'Songs of Leonard Cohen',  0,null,null, false),
('One', 'Metallica',  423, null,  37, '2001-11-01','Long version', true);

INSERT INTO tracksInPlaylists (playlistId, trackId) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3);
    