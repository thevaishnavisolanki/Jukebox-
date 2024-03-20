package jukebox.playlist;

import jukebox.catalog.Song;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private boolean isPlaying;
    private boolean isPaused;
    private int currentTime;
    private Clip audioClip;

    public AudioPlayer() {
        isPlaying = false;
        isPaused = false;
        currentTime = 0;

    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play(Song song) {
        System.out.println("Trying to play: " +song.getSongName());

        if (audioClip != null) {
            audioClip.stop();
        }
        String filePath = song.getFilePath();
        if (filePath != null && !filePath.isEmpty()) {
            try {
                File audioFile = new File(filePath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
                if (audioClip != null) {
                    audioClip.setMicrosecondPosition(0);
                    audioClip.start();
                    System.out.println("Playing: " + song.getSongName());
                } else {
                    System.out.println("Error loading audio clip.");
                }
                isPlaying = true;
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
                System.out.println("Error playing the audio: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid file path for the song.");
        }
    }

    public void stop() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.close();
        }
        isPlaying = false;
        isPaused = false;
    }


}
