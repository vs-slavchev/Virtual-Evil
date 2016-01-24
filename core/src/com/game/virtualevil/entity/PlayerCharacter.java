package com.game.virtualevil.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.Game;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.ability.concrete.ReturnAbility;
import com.game.virtualevil.utility.ability.concrete.SprintAbility;

public final class PlayerCharacter extends GameCharacter{

	public PlayerCharacter(PlayGameState playGameState) {
		super(playGameState);
		inputController = playGameState.getInputContrller();
		setPosition(new Vector2(200, playGameState.getMap().getTotalHeight() - 250));
		spriteSheet = playGameState.getAssetManager().getTextureManager().getImage("hero");
		setUpAnimation();
		isActive = true;
		
		abilities.add(0, new SprintAbility(this));
		abilities.add(1, new ReturnAbility(this));
	}
	
	/**
	 * Sets the player and camera positions as the
	 * player should always be in the center of the camera. */
	@Override
	public void setPosition(Vector2 position) {
		this.position.x = position.x;
		this.position.y = position.y;
		centerCameraOnPlayer();
	}

	private void centerCameraOnPlayer() {
		playGameState.getCamera().position.set(this.position.x, this.position.y, 0);
		playGameState.getCamera().update();
	}

	@Override
	public void applyAction(float delta) {
		super.applyAction(delta);
		
		// check the ability related input		
		for (int i = 0; i < abilities.size(); i++) {
			if (inputController.getNumberKey(i+1)) {
				abilities.get(i).attemptToUse();
			}			
		}
	}
	
	/**
	 * Overriden because the camera needs to be on the player. */
	@Override
	protected void updateAnimation(float delta) {
		if (characterMoved) {
			centerCameraOnPlayer();
			frameTime += delta;
		}
	}

	/** the camera offset is the distance from 0,0 to the lower left corner of
	 * the camera currently */
	public void drawUI(SpriteBatch batch) {
		float cameraOffsetX = playGameState.getCamera().position.x
					- playGameState.getCamera().viewportWidth / 2f;
		float cameraOffsetY = playGameState.getCamera().position.y
					- playGameState.getCamera().viewportHeight*3/2;

		// draw debugging info top left
		if (Game.TESTING) {
			BitmapFont debugFont = playGameState.getAssetManager()
					.getFontManager().getDebugFont();
			
			debugFont.draw(batch, "x: " + (int) position.x + "; y: "
					+ (int) position.y, cameraOffsetX + 5f,
					cameraOffsetY + Gdx.graphics.getHeight() - 5f);
			debugFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
					cameraOffsetX + 5f, cameraOffsetY + Gdx.graphics.getHeight() - 20f);
			debugFont.draw(batch, "dir: " + spriteDirection, cameraOffsetX + 5f,
					cameraOffsetY + Gdx.graphics.getHeight() - 35f);
		}
	}
}
