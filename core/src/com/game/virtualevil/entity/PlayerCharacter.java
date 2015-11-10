package com.game.virtualevil.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.Game;
import com.game.virtualevil.utility.TextureManager;

public class PlayerCharacter {

	private float x, y, moveSpeed = 100f;
	private int direction = 0, prevDirection;
	private Vector2 collisionBoxVector;

	private Animation animation;
	private Texture spriteSheet;
	private TextureRegion[][] frames;
	private float frameTime;
	private Game game;

	public PlayerCharacter(Game game) {
		this.game = game;
		x = Gdx.graphics.getWidth()/2;
		y = Gdx.graphics.getHeight()/2;
		spriteSheet = game.getTextureManager().getImage("hero");
		frames = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 4);
		animation = new Animation(0.15f, frames[0]);
		collisionBoxVector = new Vector2(spriteSheet.getWidth()/3 - 5,
				spriteSheet.getHeight()/4 - 5);
	}

	public void update(float delta) {
		prevDirection = direction;
		processInput(delta);
		if (prevDirection != direction) {
			animation = new Animation(0.15f, frames[direction]);
			frameTime = 0.0f;
		}

	}

	public void processInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			float futureY = y + moveSpeed * delta;
			Rectangle colRect = new Rectangle(x + 2, futureY - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				y = futureY;
				direction = 3;
				frameTime += delta;
				game.getCamera().translate(0, moveSpeed * delta);
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			float futureY = y - moveSpeed * delta;
			Rectangle colRect = new Rectangle(x + 2, futureY - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				y = futureY;
				direction = 0;
				frameTime += delta;
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
				frameTime += delta;
				game.getCamera().translate(-moveSpeed * delta, 0);
				//return;
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			float futureX = x + moveSpeed * delta;
			Rectangle colRect = new Rectangle(futureX + 2, y - 2,
					collisionBoxVector.x, collisionBoxVector.y);
			if (!game.getMap().collidesWithTerrain(colRect)) {
				x = futureX;
				direction = 2;
				frameTime += delta;
				game.getCamera().translate(moveSpeed * delta, 0);
				//return;
			}
		}
	}

	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(frameTime, true), x, y);
		drawUI(batch);
	}

	/* the camera offset is the distance from 0,0 to the lower left corner of
	 * the camera currently */
	private void drawUI(SpriteBatch batch) {
		float cameraOffsetX = game.getCamera().position.x - game.getCamera().viewportWidth / 2f;
		float cameraOffsetY = game.getCamera().position.y - game.getCamera().viewportHeight / 2f;

		// draw debugging info top left
		if (game.isTesting()) {
			BitmapFont debugFont = game.getFontManager().getDebugFont();
			debugFont.draw(batch, "x: " + (int) x + "; y: " + (int) y, cameraOffsetX + 10,
					cameraOffsetY + Gdx.graphics.getHeight() - 10);
			debugFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), cameraOffsetX + 10,
					cameraOffsetY + Gdx.graphics.getHeight() - 25);
			debugFont.draw(batch, "dir: " + direction, cameraOffsetX + 10,
					cameraOffsetY + Gdx.graphics.getHeight() - 40);
		}
	}
}
