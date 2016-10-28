package com.game.virtualevil.utility.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicManager {

    private static List<Music> songs = new ArrayList<>();
    private static int currentSongIndex = 0;
    private String[] songNames = {"NARC", "Rose the Wraith",
            "Star Eater", "Divide", "Skelletons in the attic",
            "ABIIISMO - Machine Uprising", "Video Stalker"};
    private float VOLUME = 0.6f;

    public static void playNextSong() {
        currentSongIndex++;
        if (currentSongIndex >= songs.size()) {
            currentSongIndex = 0;
        }
        songs.get(currentSongIndex).play();
    }

    public void initialize() {
        for (String name : songNames) {
            songs.add(Gdx.audio.newMusic(Gdx.files.internal("music/" + name + ".mp3")));
        }

        for (Music song : songs) {
            song.setVolume(VOLUME);
            song.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    MusicManager.playNextSong();
                }
            });
        }

        songs.get(0).play();
    }

    public void dispose() {
        for (Music song : songs) {
            song.dispose();
        }
    }
}
