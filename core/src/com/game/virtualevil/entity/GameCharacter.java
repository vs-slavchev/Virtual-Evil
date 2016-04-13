package com.game.virtualevil.entity;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.InputController;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;
import com.game.virtualevil.utility.ability.statuseffects.StatusEffect;
import com.game.virtualevil.utility.weapon.Weapon;

/**
 * The basic Character class. Entities which can move,
 * play a walking animation, have status effects and
 * abilities and generally act in the game world should
 * inherit from this class.
 * @author vs */

public abstract class GameCharacter {

	public enum Direction {
		DOWN, LEFT, RIGHT, UP
	}
	
	// entity related fields
	protected final int maxHealth;
	protected int currentHealth = 100;
	protected boolean isActive = false;
	protected float moveSpeed = 100f;
	protected Vector2 position, collisionBoxVector;
	protected CopyOnWriteArrayList<StatusEffect> statusEffects = new CopyOnWriteArrayList<>();
	protected ArrayList<Ability> abilities = new ArrayList<>(AbilityConstants.ABILITIES_COUNT);
	protected Weapon weapon;

	// animation related fields
	protected Direction prevDirection, spriteDirection;
	protected Animation animation;
	protected TextureRegion spriteSheet;
	protected TextureRegion[][] frames;
	protected float frameTime;
	protected boolean characterMoved;
	
	// control related fields
	protected PlayGameState playGameState;
	protected InputController inputController;
	
	/**
	 * Used only for testing setup. */
	public GameCharacter(){
		maxHealth = 100;
	}
	
	public GameCharacter(PlayGameState playGameState) {
		this.maxHealth = 100;
		this.playGameState = playGameState;
		position = new Vector2();
		spriteDirection = Direction.DOWN;
	}

	protected void setUpAnimation() {
		frames = spriteSheet.split(spriteSheet.getRegionWidth() / 3,
				 spriteSheet.getRegionHeight() / 4);
		animation = new Animation(0.15f, frames[0]);
		collisionBoxVector = new Vector2(spriteSheet.getRegionWidth()/3 - 8,
				spriteSheet.getRegionHeight()/4 - 16);
	}
	
	public void update(final float delta) {
		if (isActive) {
			prevDirection = spriteDirection;
			applyAction(delta);
			if (prevDirection != spriteDirection) {
				animation = new Animation(0.15f, frames[spriteDirection.ordinal()]);
				frameTime = 0.0f;
			}
			
			for (StatusEffect effect : statusEffects) {
				effect.update(delta);
			}
			for (Ability ability : abilities) {
				ability.update(delta);
			}
		}
	}
	
	protected void updateAnimation(final float delta) {
		if (characterMoved) {
			frameTime += delta;
		}
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(frameTime, true), position.x, position.y);
	}
	
	/** Contains the character actions implementation.
	 * Extend to add action logic for AI or player input processing.
	 * @param delta the time delta */
	protected void applyAction(final float delta) {
		/* check if moving in the desired direction is possible;
		 * if it is - do move. The characterMoved boolean is used
		 * to advance the animation of the player. */
		characterMoved = false;
		if (inputController.isUp()) {
			performMovement(Direction.UP, delta);
		} else if (inputController.isDown()) {
			performMovement(Direction.DOWN, delta);
		}
		if (inputController.isLeft()) {
			performMovement(Direction.LEFT, delta);
		} else if (inputController.isRight()) {
			performMovement(Direction.RIGHT, delta);
		}
		weapon.updateTimer();
		updateAnimation(delta);
	}
	
	protected void performMovement(Direction dir, final float delta){
		boolean vertical = false;
		if (dir == Direction.DOWN || dir == Direction.UP){
			vertical = true;
		}
		float offset = moveSpeed * delta;
		if (dir == Direction.DOWN || dir == Direction.LEFT){
			offset = -offset;
		}
		Rectangle colRect = createColRect(position.x + (vertical ? 0 : offset),
										  position.y + (vertical ? offset : 0));
		if (!playGameState.getMap().collidesWithTerrain(colRect)) {
			if (vertical) {
				position.y += offset;
			}
			else {
				position.x += offset;
			}
			characterMoved = true;
			spriteDirection = dir;
		}
	}
	
	/**
	 * A helper method: creates a rectangle for the terrain collision.
	 * @param x left x coordinate
	 * @param y bottom y coordinate
	 * @return the collision rectangle */
	protected Rectangle createColRect(final float x, final float y) {
		return new Rectangle(x + 4, y - 20,
				collisionBoxVector.x, collisionBoxVector.y);
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

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = new Vector2(position);
	}

	public Direction getSpriteDirection() {
		return spriteDirection;
	}
	
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void modifyHealth(int amount){
		currentHealth += amount;
	}

	public void setCurrentHealth(final int currentHealth) {
		if (currentHealth < 0) {
			this.currentHealth = 0;
		}
		if (currentHealth > maxHealth) {
			this.currentHealth = maxHealth;
		}
		this.currentHealth = currentHealth;
	}
}
