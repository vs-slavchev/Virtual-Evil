package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.Game;
import com.game.virtualevil.utility.asset.AssetManager;

public abstract class GameState {

	protected final Game game;
	protected final GameStateManager gsm;
	protected AssetManager assetManager;
	
	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	
	public GameState(GameStateManager gsm, Game game) {
		this.gsm = gsm;
		this.game = game;
		batch = new SpriteBatch();
		assetManager = game.getAssetManager();
		initialize();
	}
	
	/**
	 * Initializes the game state. Do not call
	 * it in subclass constructors, as it is invoked
	 * in superclass constructor. Only override. */
	public abstract void initialize();
	
	public abstract void update(final float delta);
	
	/**
	 * Implements the screen clearing and
	 * setting up for drawing. Should be called
	 * in a subclass draw() method implementation. */
	public void draw() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void dispose() {
		batch.dispose();
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}
}
