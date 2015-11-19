package com.game.virtualevil.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.Game;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;

public class PlayerCharacter extends GameCharacter{

	public PlayerCharacter(Game game) {
		super(game);
		
		setPosition(new Vector2(game.getCamera().viewportWidth,
				game.getMap().getTotalHeight() - game.getCamera().viewportHeight));
        
		spriteSheet = game.getTextureManager().getImage("hero");
		frames = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 4);
		animation = new Animation(0.15f, frames[0]);
		collisionBoxVector = new Vector2(spriteSheet.getWidth()/3 - 5,
				spriteSheet.getHeight()/4 - 5);
		
		abilities.add(0, Ability.createAbility(AbilityConstants.SPRINT_NAME, this));
		abilities.add(1, Ability.createAbility(AbilityConstants.RETURN_NAME, this));
	}
	
	/**
	 * Sets the player and camera positions as the
	 * player should always be in the center of the camera. */
	@Override
	public void setPosition(Vector2 position) {
		this.position.x = position.x;
		this.position.y = position.y;
		
		game.getCamera().position.set(this.position.x, this.position.y, 0);
		game.getCamera().update();
	}

	public void applyAction(float delta) {
		processInput(delta);
	}

	/**
	 * Checks for all kinds of gameplay related
	 * player input.
	 * @param delta	 the time delta*/
	public void processInput(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			abilities.get(0).attemptToUse();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			abilities.get(1).attemptToUse();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
			abilities.get(2).attemptToUse();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
			abilities.get(3).attemptToUse();
		}
		
		/* check if moving in the desired direction is possible;
		 * if it is - do move, else don't.The boolean used to
		 * advance the animation of the player */
		boolean playerMoved = false;
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			float futureY = position.y + moveSpeed * delta;
			Rectangle colRect = new Rectangle(position.x + 2, futureY - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				position.y = futureY;
				direction = Direction.UP;
				playerMoved = true;
				game.getCamera().translate(0, moveSpeed * delta);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			float futureY = position.y - moveSpeed * delta;
			Rectangle colRect = new Rectangle(position.x + 2, futureY - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				position.y = futureY;
				direction = Direction.DOWN;
				playerMoved = true;
				game.getCamera().translate(0, -moveSpeed * delta);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			float futureX = position.x - moveSpeed * delta;
			Rectangle colRect = new Rectangle(futureX + 2, position.y - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				position.x = futureX;
				direction = Direction.LEFT;
				playerMoved = true;
				game.getCamera().translate(-moveSpeed * delta, 0);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			float futureX = position.x + moveSpeed * delta;
			Rectangle colRect = new Rectangle(futureX + 2, position.y - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				position.x = futureX;
				direction = Direction.RIGHT;
				playerMoved = true;
				game.getCamera().translate(moveSpeed * delta, 0);
			}
		}
		if (playerMoved) {
			frameTime += delta;
		}
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		drawUI(batch);
	}
	
	/** the camera offset is the distance from 0,0 to the lower left corner of
	 * the camera currently */
	private void drawUI(SpriteBatch batch) {
		float cameraOffsetX = game.getCamera().position.x - game.getCamera().viewportWidth / 2f;
		float cameraOffsetY = game.getCamera().position.y - game.getCamera().viewportHeight*3/2;

		// draw debugging info top left
		if (game.isTesting()) {
			BitmapFont debugFont = game.getFontManager().getDebugFont();
			debugFont.draw(batch, "x: " + (int) position.x + "; y: "
					+ (int) position.y, cameraOffsetX + 5f,
					cameraOffsetY + Gdx.graphics.getHeight() - 5f);
			debugFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
					cameraOffsetX + 5f, cameraOffsetY + Gdx.graphics.getHeight() - 20f);
			debugFont.draw(batch, "dir: " + direction, cameraOffsetX + 5f,
					cameraOffsetY + Gdx.graphics.getHeight() - 35f);
		}
	}
}
