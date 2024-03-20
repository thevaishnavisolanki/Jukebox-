package jukebox.ui;

import jukebox.playlist.AudioPlayer;
import jukebox.service.Jukebox;
import java.util.Scanner;

public class JukeboxUI {
    private final Jukebox jukebox;
    private final Scanner scanner;

    public JukeboxUI(Jukebox jukebox, AudioPlayer audioPlayer) {
        this.jukebox = jukebox;
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        String choice = "y";
        while (choice.equalsIgnoreCase("y")) {
            System.out.println("\n--- JUKEBOX MENU ---");
            System.out.println("1. Display All Songs");
            System.out.println("2. Search Songs");
            System.out.println("3. Sort Songs by Artist");
            System.out.println("4. Create Playlist");
            System.out.println("5. Add Song to Playlist");
            System.out.println("6. View Playlist Contents");
            System.out.println("7. Play Playlist");
            System.out.println("8. Stop Song");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    jukebox.displayAllSongs();
                    break;
                case 2:
                    searchSongs();
                    break;
                case 3:
                    jukebox.sortSongsByArtist();
                    break;
                case 4:
                    createPlaylist();
                    break;
                case 5:
                    addSongToPlaylist();
                    break;
                case 6:
                    viewPlaylistContents();
                    break;
                case 7:
                    playPlaylist();
                    break;
                case 8:
                    stopSong();
                    break;
                case 9:
                    System.out.println("Exiting the Jukebox application...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            System.out.print("Do you want to continue (y/n)? ");
            choice = scanner.next();
        }
    }

    private void searchSongs() {
        System.out.print("Enter the search criteria (song name, artist name, genre): ");
        String criteria = scanner.nextLine();
        System.out.print("Enter the search value: ");
        String value = scanner.nextLine();
        jukebox.searchSongs(criteria, value);
    }

    private void createPlaylist() {
        System.out.print("Enter the name of the new playlist: ");
        String playlistName = scanner.nextLine();
        jukebox.createPlaylist(playlistName);
    }

    private void addSongToPlaylist() {
        System.out.print("Enter the ID of the playlist: ");
        int playlistId = scanner.nextInt();
        System.out.print("Enter the ID of the song to add: ");
        int songId = scanner.nextInt();
        jukebox.addSongToPlaylist(playlistId, songId);
    }

    private void viewPlaylistContents() {
        System.out.print("Enter the ID of the playlist: ");
        int playlistId = scanner.nextInt();
        jukebox.viewPlaylistContents(playlistId);
    }

    private void playPlaylist() {
        System.out.print("Enter the ID of the Playlist to play: ");
        int playlistId = scanner.nextInt();
        scanner.nextLine();
        jukebox.playPlaylist(playlistId);
    }

    private void stopSong() {
        jukebox.stopSong();
        System.out.println("Song stopped.");
    }
}