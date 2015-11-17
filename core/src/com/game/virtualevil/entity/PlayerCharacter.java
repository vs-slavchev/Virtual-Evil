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

public class PlayerCharacter extends GameCharacter{

	

	public PlayerCharacter(Game game) {
		super(game);
		
		game.getMap().getHeight();
		game.getCamera().position.set(game.getCamera().viewportWidth,
				game.getMap().getTotalHeight() - game.getCamera().viewportHeight, 0);
		game.getCamera().update();
        
        
		x = game.getCamera().viewportWidth;
		y = game.getMap().getTotalHeight() - game.getCamera().viewportHeight;
		spriteSheet = game.getTextureManager().getImage("hero");
		frames = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 4);
		animation = new Animation(0.15f, frames[0]);
		collisionBoxVector = new Vector2(spriteSheet.getWidth()/3 - 5,
				spriteSheet.getHeight()/4 - 5);
		
		abilities.add(0, new Ability("SPRINT", this));
	}

	public void applyAction(float delta) {
		processInput(delta);
	}

	public void processInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			abilities.get(0).useAbility();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			abilities.get(1).useAbility();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
			abilities.get(2).useAbility();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
			abilities.get(3).useAbility();
		}
		
		boolean playerMoved = false;
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			float futureY = y + moveSpeed * delta;
			Rectangle colRect = new Rectangle(x + 2, futureY - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				y = futureY;
				direction = 3;
				playerMoved = true;
				game.getCamera().translate(0, moveSpeed * delta);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			float futureY = y - moveSpeed * delta;
			Rectangle colRect = new Rectangle(x + 2, futureY - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				y = futureY;
				direction = 0;
				playerMoved = true;
				game.getCamera().translate(0, -moveSpeed * delta);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			float futureX = x - moveSpeed * delta;
			Rectangle colRect = new Rectangle(futureX + 2, y - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				x = futureX;
				direction = 1;
				playerMoved = true;
				game.getCamera().translate(-moveSpeed * delta, 0);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			float futureX = x + moveSpeed * delta;
			Rectangle colRect = new Rectangle(futureX + 2, y - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				x = futureX;
				direction = 2;
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
	
	/* the camera offset is the distance from 0,0 to the lower left corner of
	 * the camera currently */
	private void drawUI(SpriteBatch batch) {
		float cameraOffsetX = game.getCamera().position.x - game.getCamera().viewportWidth / 2f;
		float cameraOffsetY = game.getCamera().position.y - game.getCamera().viewportHeight*3/2;
		// or - game.getCamera().viewportHeight/2 for normal scale

		// draw debugging info top left
		if (game.isTesting()) {
			BitmapFont debugFont = game.getFontManager().getDebugFont();
			debugFont.draw(batch, "x: " + (int) x + "; y: " + (int) y, cameraOffsetX + 5f,
					cameraOffsetY + Gdx.graphics.getHeight() - 5f);
			debugFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), cameraOffsetX + 5f,
					cameraOffsetY + Gdx.graphics.getHeight() - 20f);
			debugFont.draw(batch, "dir: " + direction, cameraOffsetX + 5f,
					cameraOffsetY + Gdx.graphics.getHeight() - 35f);
		}
	}
}
