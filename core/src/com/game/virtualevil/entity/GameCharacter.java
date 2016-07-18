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
import com.game.virtualevil.utility.Map;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;
import com.game.virtualevil.utility.ability.statuseffects.StatusEffect;
import com.game.virtualevil.utility.weapon.Weapon;

/**
 * The basic Character class. Entities which can move,
 * play a walking animation, have status effects and
 * abilities and generally act in the game world should
 * inherit from this class.
 */

public class GameCharacter extends GameObject {

	public enum Direction {
		DOWN, LEFT, RIGHT, UP
	}
	
	protected final int maxHealth;
	protected int currentHealth;
	protected float invulnerabilityTimer = 0.5f;
	protected boolean hasBeenHitRecently = false;
	protected boolean isActive = false;
	protected float moveSpeed = 100f;
	protected Vector2 collisionBoxVector;
	protected CopyOnWriteArrayList<StatusEffect> statusEffects = new CopyOnWriteArrayList<>();
	protected ArrayList<Ability> abilities = new ArrayList<>(AbilityConstants.ABILITIES_COUNT);
	protected Weapon weapon;

	protected Direction prevDirection, spriteDirection;
	protected Animation animation;
	protected TextureRegion spriteSheet;
	protected TextureRegion[][] frames;
	protected float frameTime;
	protected boolean characterMoved;
	protected InputController inputController;
	
	public GameCharacter() {
		this.radius = 16;
		this.maxHealth = EntityConstants.CHARACTER_MAX_HP;
		this.currentHealth = maxHealth;
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
	
	public boolean overlap(GameObject otherObject) {
		double marginX = Math.abs(otherObject.getPosition().x - this.getPosition().x);
		double marginY = Math.abs(otherObject.getPosition().y - this.getPosition().y);

		double distance = Math.sqrt(Math.pow(marginX, 2) + Math.pow(marginY, 2));
		if (distance < this.radius + otherObject.radius) {
			return true;
		}
		return false;
	}
	
	public void update(final float delta, PlayGameState playState) {
		if (isActive) {
			prevDirection = spriteDirection;
			applyAction(delta, playState);
			if (prevDirection != spriteDirection) {
				animation = new Animation(0.15f, frames[spriteDirection.ordinal()]);
				frameTime = 0.0f;
			}
			
			if(hasBeenHitRecently){
				invulnerabilityTimer -= delta;
				if(invulnerabilityTimer == 0){
					hasBeenHitRecently = false;
				}
			}
			
			if (currentHealth <= 0){
				isActive = false;
				playState.getEntityManager().removeCharacter(this);
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
		if (isActive){
			batch.draw(animation.getKeyFrame(frameTime, true), position.x, position.y);
		}
	}
	
	/** Contains the character actions implementation.
	 * Extend to add action logic for AI or player input processing. */
	protected void applyAction(final float delta, PlayGameState playState) {
		prepareMovement(delta, playState);
		updateAnimation(delta);
	}

	/** Check if moving in the desired direction is possible;
	 * if it is - do move. The characterMoved boolean is used
	 * to advance the animation of the player. */
	protected void prepareMovement(final float delta, PlayGameState playState) {
		characterMoved = false;
		Map map = playState.getMap();
		if (inputController.isUp()) {
			performMovement(Direction.UP, delta, map);
		} else if (inputController.isDown()) {
			performMovement(Direction.DOWN, delta, map);
		}
		if (inputController.isLeft()) {
			performMovement(Direction.LEFT, delta, map);
		} else if (inputController.isRight()) {
			performMovement(Direction.RIGHT, delta, map);
		}
		weapon.updateTimer();
	}
	
	protected void performMovement(Direction dir, final float delta, Map map){
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
		if (!map.collidesWithTerrain(colRect)) {
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
	
	/** A helper method: creates a rectangle for the terrain collision. */
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
		Vector2 positionCenter = new Vector2(position);
		positionCenter.x += 16;
		positionCenter.y += 16;
		return positionCenter;
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
	
	public void modifyCurrentHealth(final int modifyHealth){
		setCurrentHealth(this.currentHealth + modifyHealth);
	}

	public void setCurrentHealth(final int currentHealth) {
		if (currentHealth < 0) {
			this.currentHealth = 0;
			return;
		}
		if (currentHealth > maxHealth) {
			this.currentHealth = maxHealth;
			return;
		}
		this.currentHealth = currentHealth;
	}

	public boolean hasBeenHitRecently() {
		return hasBeenHitRecently;
	}

	public void setHasBeenHitRecently(boolean hasBeenHitRecently) {
		this.hasBeenHitRecently = hasBeenHitRecently;
	}

	public Weapon getWeapon() {
		return weapon;
	}
}
