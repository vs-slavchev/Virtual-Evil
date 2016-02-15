package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.Game;
import com.game.virtualevil.gamestate.GameStateManager.StateType;
import com.game.virtualevil.utility.asset.TextureManager;

/**
 * One instance of this class is responsible for
 * all of the main menu button management.
 * @author vs */
public class MenuButtons {

	private final Game game;
	private final TextureManager tm;
	private TextureRegion[] textures;
	public final static int NUM_BUTTONS = 4, BUTTON_HEIGHT = 70,
			SPACING = Gdx.graphics.getHeight() / 10;

	public MenuButtons(Game game) {
		this.game = game;
		this.tm = game.getAssetManager().getTextureManager();
		TextureRegion buttonsTileset = tm.getImage("mainMenuButtons");
		
		/* setup the textures array to contain the texture
		 * regions of the buttons */
		textures = new TextureRegion[buttonsTileset.getRegionHeight() / BUTTON_HEIGHT];
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new TextureRegion();
			textures[i].setRegion(tm.getImage("mainMenuButtons"),
					0, i * BUTTON_HEIGHT,
					buttonsTileset.getRegionWidth(), BUTTON_HEIGHT);
		}
	}
	
	/* Check all buttons for clicks and invoke the
	 * corresponding action. */
	public void checkClicks() {
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			for (int id = 0; id < NUM_BUTTONS; id++) {
				if (isCursorOnButton(id)) {
					switch (id) {
					case 0: // play
						game.getGsm().setCurrentState(StateType.PLAY);
						break;
					case 1: // new game
						game.getGsm().startNewGame();
						break;
					case 2: // options
						// TODO
						break;
					case 3: // quit
						Gdx.app.exit();
						break;
					default:
						break;
					}
					// at most 1 button should be pressed with a single click
					break;
				}
			}
		}
	}
	
	public void drawButtons(SpriteBatch batch) {
		for (int id = 0; id < NUM_BUTTONS; id++) {
			int textureIndex = isCursorOnButton(id) ? id + NUM_BUTTONS : id;
			batch.draw(textures[textureIndex], getX(id), getY(id));
		}
	}
	
	/* Check whether the cursor is inside the button
	 * bounding box established by the button's
	 * texture region dimensions. */
	private boolean isCursorOnButton(final int id) {
		int mouseX = Gdx.input.getX(),
				mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		if (getX(id) < mouseX
				&& getX(id) + textures[id].getRegionWidth() > mouseX
				&& getY(id) < mouseY
				&& getY(id) + BUTTON_HEIGHT > mouseY) {
			return true;
		}
		return false;
	}

	/**
	 * @param id of the button
	 * @return the left boundary of a button corresponding to that id
	 */
	private int getX(final int id) {
		return Gdx.graphics.getWidth()/2 - textures[id].getRegionWidth()/2;
	}
	
	/**
	 * @param id of the button
	 * @return the lower boundary of a button corresponding to that id
	 */
	@SuppressWarnings("static-method")
	private int getY(final int id) {
		return (NUM_BUTTONS - id) * SPACING
				+ (Gdx.graphics.getHeight() - 768) / 10;
		// 768 is the minimum supported screen height
	}
}
