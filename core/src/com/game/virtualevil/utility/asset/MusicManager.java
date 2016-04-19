package com.game.virtualevil.utility.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager  {
	public Music song1, song2, song3, song4, song5, song6, song7;

	public void initialize() {
		song1 = Gdx.audio.newMusic(Gdx.files.internal("NARC.mp3"));
		song2 = Gdx.audio.newMusic(Gdx.files.internal("Rose the Wraith.mp3"));
		song3 = Gdx.audio.newMusic(Gdx.files.internal("Star Eater.mp3"));
		song4 = Gdx.audio.newMusic(Gdx.files.internal("Divide.mp3"));
		song5 = Gdx.audio.newMusic(Gdx.files.internal("Skelletons in the attic.mp3"));
		song6 = Gdx.audio.newMusic(Gdx.files.internal("ABIIISMO - Machine Uprising.mp3"));
		song7 = Gdx.audio.newMusic(Gdx.files.internal("Video Stalker.mp3"));

		song1.setVolume(0.5f);
		song1.play();

		// This makes song2 play after song1 finishes
		song1.setOnCompletionListener(new Music.OnCompletionListener() {

			@Override
			public void onCompletion(Music music) {
				song2.setVolume(0.5f);
				song2.play();
			}
		});
		
		song2.setOnCompletionListener(new Music.OnCompletionListener() {
			
			@Override
			public void onCompletion(Music music) {
				song3.setVolume(0.5f);
				song3.play();
				
			}
		});
		
		song3.setOnCompletionListener(new Music.OnCompletionListener() {
			
			@Override
			public void onCompletion(Music music) {
				song4.setVolume(0.5f);
				song4.play();
				
			}
		});
		
		song4.setOnCompletionListener(new Music.OnCompletionListener() {
			
			@Override
			public void onCompletion(Music music) {
				song5.setVolume(0.5f);
				song5.play();
				
			}
		});
		
		song5.setOnCompletionListener(new Music.OnCompletionListener() {
			
			@Override
			public void onCompletion(Music music) {
				song6.setVolume(0.5f);
				song6.play();
				
			}
		});
		
		song6.setOnCompletionListener(new Music.OnCompletionListener() {
			
			@Override
			public void onCompletion(Music music) {
				song7.setVolume(0.5f);
				song7.play();
				
			}
		});
		
		song7.setOnCompletionListener(new Music.OnCompletionListener() {
			
			@Override
			public void onCompletion(Music music) {
				song1.setVolume(0.5f);
				song1.play();
				
			}
		});
	}

	public void dispose() {
		song1.dispose();
		song2.dispose();
		song3.dispose();
	}
}
