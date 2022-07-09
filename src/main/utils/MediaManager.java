package main.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.controllers.Login;

import java.util.Objects;

public final class MediaManager {
    private MediaManager() {

    }

    public static final String resource = Objects.requireNonNull(Login.class.getResource("/Images/notesWriting.mp4")).toString();
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MediaManager.mediaPlayer = mediaPlayer;
    }

    public static void initMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(resource));
        mediaPlayer.isMute();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        setMediaPlayer(mediaPlayer);
    }
}
