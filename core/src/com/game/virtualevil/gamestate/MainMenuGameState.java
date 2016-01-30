package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.Game;

public class MainMenuGameState extends GameState{
	
	private TextureRegion background;
	private TextureRegion[][] chipsImages;
	private float[][] chipsXcoords;
	private final int numRows = 4, numCols = 6;
	private final int baseScrollSpeed = 3;

	public MainMenuGameState(GameStateManager gsm, Game game) {
		super(gsm, game);
	}

	@Override
	public void initialize() {
		camera = new OrthographicCamera(
				Gdx.graphics.getWidth()/8,
				Gdx.graphics.getHeight()/8);
		camera.setToOrtho(false, camera.viewportWidth/2, camera.viewportHeight/2);
		camera.update();
		background = assetManager.getTextureManager().getImage("startScreenBackground");
		background.setRegion(0, 0, 120, 68);
		
		TextureRegion chipsTileset = assetManager.getTextureManager()
				.getImage("chipBuildings");
		
		/* chop the tileset with chips and put each separate chip
		 * in the 2D array called chipsImages */
		chipsImages = chipsTileset.split(chipsTileset.getRegionWidth()/numCols,
				chipsTileset.getRegionHeight()/numRows);
		
		/* the chipsXcoords 2D array holds the X position of each chip
		 * on the screen. The for loop initializes the chips to be
		 * evenly spread. */
		chipsXcoords = new float[numRows][numCols];
		for (int row = 0; row < chipsXcoords.length; row++) {
			for (int col = 0; col < chipsXcoords[row].length; col++) {
				chipsXcoords[row][col] = col * (chipsImages[row][col].getRegionWidth() + 4);
			}
		}
	}

	@Override
	public void update(float delta) {
		for (int row = 0; row < chipsXcoords.length; row++) {
			for (int col = 0; col < chipsXcoords[row].length; col++) {
				/* move right and check if out of screen. In that case
				 * set x to negative image width, so the rightmost
				 * part of the image is out of the screen. */
				chipsXcoords[row][col] += baseScrollSpeed * (numRows - row) * delta;
				if (chipsXcoords[row][col] > background.getRegionWidth()) {
					chipsXcoords[row][col] = -chipsImages[row][col].getRegionWidth();
				}
			}
		}
		
	}

	@Override
	public void draw() {
		super.draw();
		batch.begin();
		batch.draw(background, 0, 0);
		for (int row = chipsXcoords.length - 1; row >= 0; row--) {
			for (int col = 0; col < chipsXcoords[row].length; col++) {
				// draw each chip image using its respective x coordinate
				batch.draw(chipsImages[row][col], chipsXcoords[row][col], 0);		
			}
		}
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		
	}
}
