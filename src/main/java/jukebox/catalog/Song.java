package jukebox.catalog;

public class Song {
    private String songName;
    private String artistName;
    private String album;
    private String duration;
    private String genre;
    private String filePath;

    public Song(String songName, String artistName, String album, String duration, int id, String genre, String filePath) {
        this.songName = songName;
        this.artistName = artistName;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
        this.filePath = filePath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", album='" + album + '\'' +
                ", duration='" + duration + '\'' +
                ", genre='" + genre + '\'' +
                ", filePath='" + filePath +
                '}';
    }
}
