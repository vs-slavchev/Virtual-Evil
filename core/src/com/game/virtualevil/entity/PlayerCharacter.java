package com.game.virtualevil.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.Game;
import com.game.virtualevil.utility.TextureManager;

public class PlayerCharacter {

	private float x, y, moveSpeed = 100f;
	private int direction = 0, prevDirection;

	private Animation animation;
	private Texture spriteSheet;
	private TextureRegion[][] frames;
	private float frameTime;
	private Game game;

	public PlayerCharacter(Game game) {
		this.game = game;
		spriteSheet = game.getTextureManager().getImage("hero");
		frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 4);
		animation = new Animation(0.15f, frames[0]);
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
			y += moveSpeed * delta;
			direction = 3;
			frameTime += delta;
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			y -= moveSpeed * delta;
			direction = 0;
			frameTime += delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			x -= moveSpeed * delta;
			direction = 1;
			frameTime += delta;
			return;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			x += moveSpeed * delta;
			direction = 2;
			frameTime += delta;
			return;
		}
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(frameTime, true), x, y);
		if (game.isTesting()) {
			BitmapFont debugFont = game.getFontManager().getDebugFont();
			debugFont.draw(batch, "x: " + (int)x + "; y: " + (int)y, 10, Gdx.graphics.getHeight() - 0);
			debugFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 15);
			debugFont.draw(batch, "dir: " + direction, 10, Gdx.graphics.getHeight() - 30);
		}
	}
}
