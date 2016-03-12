package com.game.virtualevil.utility.asset;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager  {
	public Music song1, song2, song3;
	//boolean isPlaying1 = song1.isPlaying(); 


	public void initialize() {
		song1 = Gdx.audio.newMusic(Gdx.files.internal("NARC.mp3"));
		song2 = Gdx.audio.newMusic(Gdx.files.internal("Rose the Wraith.mp3"));
		song3 = Gdx.audio.newMusic(Gdx.files.internal("ABIIISMO - Machine Uprising.mp3"));

		//song3.play();
		song1.setVolume(0.2f);

		// This makes song2 play after song1 finishes
		song1.setOnCompletionListener(new Music.OnCompletionListener() {

			@Override
			public void onCompletion(Music music) {
				song1.play();
				song3.play();
			}
		});
	}

	public void dispose() {
		song1.dispose();
		song2.dispose();
		song3.dispose();
	}
}
