package main.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.controllers.Login;

import java.util.Objects;

/**
 * A utility class that manages all the functionality for setting up a media view in javaFx
 */
public final class MediaManager {
    /**
     * The location of the resource to be shown represented as a string path
     */
    public static final String resource = Objects.requireNonNull(Login.class.getResource("/Images/notesWriting.mp4")).toString();
    /**
     * A mediaPlayer for playing media from the javaFx libary
     */
    private static MediaPlayer mediaPlayer;

    /**
     * Private constructor so that this object cannot be instantiated
     */
    private MediaManager() {

    }

    /**
     * Returns a reference to the current mediaPlayer or a null value
     *
     * @return an active mediaPlayer or null
     */
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * Assigns an instantiated mediaPlayer to the local reference in MediaManager so that the methods within apply to the
     * instantiated object.
     *
     * @param mediaPlayer A mediaPlayer for playing media from the javaFx libary
     */
    static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MediaManager.mediaPlayer = mediaPlayer;
    }

    /**
     * Initializes the mediaPlayer. If you here no volume this is not an error the volume is muted, autoplay is enabled
     * and the video will loop indefinitely until the scene is transitioned off the active scene.
     */
    public static void initMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(resource));
        mediaPlayer.isMute();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        setMediaPlayer(mediaPlayer);
    }
}
