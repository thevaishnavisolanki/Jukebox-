package jukebox.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import jukebox.catalog.Song;
import jukebox.playlist.AudioPlayer;

public class Jukebox {
    private final Connection connection;
    private final Scanner scanner;
    private AudioPlayer audioPlayer = new AudioPlayer();

    public Jukebox(Connection connection ,AudioPlayer audioPlayer ) {
        this.connection = connection;
        this.audioPlayer = audioPlayer;
        this.scanner = new Scanner(System.in);
    }
    public void displayAllSongs() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM songs");
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("All Songs:");
            while (resultSet.next()) {
                System.out.println("Song ID: " + resultSet.getInt("id"));
                System.out.println("Song Title: " + resultSet.getString("Song_title"));
                System.out.println("Artist: " + resultSet.getString("artist"));
                System.out.println("Album: " + resultSet.getString("album"));
                System.out.println("Duration: " + resultSet.getString("duration"));
                System.out.println("Genre: " + resultSet.getString("genre"));
                System.out.println("------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchSongs(String criteria, String value) {
        String query = "";
        if (criteria.equals("song name")) {
            query = "SELECT * FROM songs WHERE Song_title LIKE ?";
        } else if (criteria.equals("artist name")) {
            query = "SELECT * FROM songs WHERE artist LIKE ?";
        } else if (criteria.equals("genre")) {
            query = "SELECT * FROM songs WHERE genre LIKE ?";
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + value + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("Search Results:");
                while (resultSet.next()) {
                    System.out.println("Song ID: " + resultSet.getInt("id"));
                    System.out.println("Song Title: " + resultSet.getString("Song_title"));
                    System.out.println("Artist: " + resultSet.getString("artist"));
                    System.out.println("Album: " + resultSet.getString("album"));
                    System.out.println("Duration: " + resultSet.getString("duration"));
                    System.out.println("Genre: " + resultSet.getString("genre"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortSongsByArtist() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM songs ORDER BY artist");
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("Songs Sorted by Artist:");
            while (resultSet.next()) {
                System.out.println("Song ID: " + resultSet.getInt("id"));
                System.out.println("Song Title: " + resultSet.getString("Song_title"));
                System.out.println("Artist: " + resultSet.getString("artist"));
                System.out.println("Album: " + resultSet.getString("album"));
                System.out.println("Duration: " + resultSet.getString("duration"));
                System.out.println("Genre: " + resultSet.getString("genre"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlaylist(String playlistName) {
        String query = "INSERT INTO playlists (playlist_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playlistName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Playlist created successfully: " + playlistName);
            } else {
                System.out.println("Failed to create playlist: " + playlistName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSongToPlaylist(int playlistId, int songId) {
        String query = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlistId);
            statement.setInt(2, songId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Song added to playlist successfully.");
            } else {
                System.out.println("Failed to add song to playlist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewPlaylistContents(int playlistId) {
        String query = "SELECT s.Song_title, s.artist, s.album, s.duration, s.genre " +
                "FROM songs s " +
                "JOIN playlist_songs ps ON s.id = ps.song_id " +
                "WHERE ps.playlist_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlistId);
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("Playlist Contents:");
                while (resultSet.next()) {
                    System.out.println("Song Name: " + resultSet.getString("Song_title"));
                    System.out.println("Artist: " + resultSet.getString("artist"));
                    System.out.println("Album: " + resultSet.getString("album"));
                    System.out.println("Duration: " + resultSet.getString("duration"));
                    System.out.println("Genre: " + resultSet.getString("genre"));
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void playSong(Song song) {
        audioPlayer.play(song);
    }

    public void stopSong() {
        audioPlayer.stop();
    }




    public List<Song> getSongsByPlaylistId(int playlistId) {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT s.id, s.Song_title, s.artist, s.album, s.duration, s.genre, s.file_path " +
                "FROM songs s " +
                "JOIN playlist_songs ps ON s.id = ps.song_id " +
                "WHERE ps.playlist_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playlistId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("Song_title");
                    String artist = resultSet.getString("artist");
                    String album = resultSet.getString("album");
                    String duration = resultSet.getString("duration");
                    String genre = resultSet.getString("genre");
                    String filepath = resultSet.getString("file_path");
                    Song song = new Song(title, artist, album, duration, resultSet.getInt("id"), genre , filepath);
                    songs.add(song);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }
    public void playPlaylist(int playlistId) {
        List<Song> songs = getSongsByPlaylistId(playlistId);
        if (!songs.isEmpty()) {
            for (Song song : songs) {
                playSong(song);
                while (audioPlayer.isPlaying()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!continuePlaying()) {
                        stopSong();
                        return;
                    }
                }
                System.out.println("Playing: " + song.getSongName());
            }
        } else {
            System.out.println("No songs found in the playlist.");
        }
    }

    private boolean continuePlaying() {
        System.out.print("Do you want to continue playing the playlist (y/n)? ");
        String choice = scanner.next();
        return choice.equalsIgnoreCase("y");
    }

}
