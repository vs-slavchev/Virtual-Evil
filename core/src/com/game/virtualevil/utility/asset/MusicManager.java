package com.game.virtualevil.utility.asset;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager  {
	
	private List<Music> songs = new ArrayList<>();
	private String[] songNames = {"NARC", "Rose the Wraith",
			"Star Eater", "Divide", "Skelletons in the attic",
			"ABIIISMO - Machine Uprising", "Video Stalker" };
	private int currentSongIndex = 0;
	private float VOLUME = 0.0f;

	public void initialize() {
		for (String name : songNames){
			songs.add(Gdx.audio.newMusic(Gdx.files.internal("music/" + name + ".mp3")));
		}
		
		for (Music song : songs){
			song.setVolume(VOLUME);
			song.setOnCompletionListener(music -> playNextSong());
		}
		
		songs.get(0).play();
	}
	
	private void playNextSong(){
		currentSongIndex++;
		if (currentSongIndex >= songs.size()){
			currentSongIndex = 0;
		}
		songs.get(currentSongIndex).play();
	}

	public void dispose() {
		for (Music song : songs){
			song.dispose();
		}
	}
}
