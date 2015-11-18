package com.game.virtualevil.entity;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.Game;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.StatusEffect;

public abstract class GameCharacter {

	public enum Direction {
		DOWN, LEFT, RIGHT, UP
	}
	
	protected float x, y, moveSpeed = 100f;
	protected Direction prevDirection, direction = Direction.DOWN;
	protected Vector2 collisionBoxVector;
	protected CopyOnWriteArrayList<StatusEffect> statusEffects= new CopyOnWriteArrayList<>();
	protected ArrayList<Ability> abilities = new ArrayList<>();
	
	protected Animation animation;
	protected Texture spriteSheet;
	protected TextureRegion[][] frames;
	protected float frameTime;
	protected Game game;
	
	public GameCharacter(Game game) {
		this.game = game;
	}

	public void update(float delta) {
		prevDirection = direction;
		applyAction(delta);
		if (prevDirection != direction) {
			animation = new Animation(0.15f, frames[direction.ordinal()]);
			frameTime = 0.0f;
		}
		
		for (StatusEffect effect : statusEffects) {
			effect.update();
		}
		for (Ability ability : abilities) {
			ability.update();
		}
	}
	
	// needs to be overridden for the character implementation
	protected abstract void applyAction(float delta);
	
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(frameTime, true), x, y);
	}
	
	public void modifyMoveSpeed(float amount) {
		moveSpeed += amount;
	}
	
	public void addStatusEffect(StatusEffect statusEffect) {
		statusEffects.add(statusEffect);
	}
	
	public void removeStatusEffect(StatusEffect statusEffect) {
		statusEffects.remove(statusEffect);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
