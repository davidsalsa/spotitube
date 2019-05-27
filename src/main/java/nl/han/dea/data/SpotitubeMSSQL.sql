USE master;
DROP DATABASE IF EXISTS Spotitube;
CREATE DATABASE Spotitube;
USE Spotitube;

-- user table token is unique per user
CREATE TABLE [user] (
    [user] varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    token varchar(14) NOT NULL PRIMARY KEY
    );

-- playlistObject table, chose not to include owner, userplaylist determines the owner instead
CREATE TABLE playlists (
                           id int NOT NULL IDENTITY(1,1) PRIMARY KEY,
                           name varchar(255) NOT NULL,
                           owner_token varchar(14) NOT NULL,
                           length int NOT NULL,
                           CONSTRAINT fk_owner_token FOREIGN KEY (owner_token) REFERENCES [user] (token)  ON UPDATE CASCADE ON DELETE NO ACTION
);

-- tracks table
CREATE TABLE tracks (
                        id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
                        title varchar(255) NOT NULL,
                        performer varchar(255) NOT NULL,
                        duration int NOT NULL,
                        album varchar(255) NULL,
                        playcount int DEFAULT NULL,
                        publicationDate date DEFAULT NULL,
                        description varchar(255) DEFAULT NULL,
                        offlineAvailable bit NOT NULL
);

-- stores what track is in which playlistObject
-- make sure to disable safe delete/update in preferences for this to work
CREATE TABLE tracksInPlaylists(
                                  playlistId int NOT NULL,
                                  trackId int NOT NULL,
                                  PRIMARY KEY (playlistId,trackId),
                                  CONSTRAINT fk_playlistId FOREIGN KEY (playlistId) REFERENCES playlists (id) ON UPDATE CASCADE ON DELETE CASCADE,
                                  CONSTRAINT fk_trackId FOREIGN KEY (trackId) REFERENCES tracks (id)  ON UPDATE CASCADE ON DELETE CASCADE
);
GO

-- Stored procedure that updates playlistObject length everytime a track gets added. This happens for every insert/update and delete
CREATE TRIGGER updatePlaylistLengthAfterUpdate
    ON tracksInPlaylists
    AFTER INSERT, DELETE, UPDATE
    AS BEGIN
    IF @@ROWCOUNT = 0 RETURN
SET NOCOUNT ON
BEGIN TRY
UPDATE p
SET p.length = (SELECT SUM(t.duration) FROM tracks t INNER JOIN tracksInPlaylists tp ON t.id = tp.trackId and p.id = tp.playlistId)
    FROM playlists p inner join tracksInPlaylists tp ON p.id = tp.playlistId;
END TRY
BEGIN CATCH
;THROW
	END CATCH
END
GO

INSERT INTO [user] ([user], password,  token) VALUES
    ('meron', 'meron', '0417-d07a-a5e7'),
    ('dennis', 'dennis', '0418-d07a-a5e7'),
    ('michel', 'michel',  '0419-d07a-a5e7');
GO

INSERT INTO playlists (name, owner_token, length) VALUES
('Death metal', '0417-d07a-a5e7', 0),
('pop', '0418-d07a-a5e7', 0);
GO

INSERT INTO tracks (title, performer, duration, album, playcount, publicationDate, description, offlineAvailable) VALUES
('Ocean and a rock', 'Lisa Hannigan',  337, 'Sea sew',  0,null,null, 0),
('So Long, Marianne', 'Leonard Cohen',  546, 'Songs of Leonard Cohen',  0,null,null, 0),
('One', 'Metallica',  423, null,  37, '2001-11-01','Long version', 1);
GO

INSERT INTO tracksInPlaylists (playlistId, trackId) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3);
GO

