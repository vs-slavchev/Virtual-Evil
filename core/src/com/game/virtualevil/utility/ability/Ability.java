package com.game.virtualevil.utility.ability;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.entity.GameCharacter;

/**
 * The main ability class. It uses the Factory
 * design pattern. Use createAbility() statically
 * to create an Ability instance; attemptToUse()
 * when trying to use an Ability and the update()
 * method to tick the cooldown.
 * @author vs */
public abstract class Ability {
	
	// time is measured in seconds
	protected float remainingCooldown;
	protected final String abilityName;
	protected final GameCharacter character;
	
	public Ability(String abilityName, GameCharacter character) {
		this.abilityName = abilityName;
		this.character = character;
	}
	
	/**
	 * This is the method to be called when an ability
	 * <em>should<em> be used. */
	public final void attemptToUse() {
		if (isOffCooldown()) {
			useAbility();
		}
	}
	
	/**
	 * The most important method in the Ability class.
	 * Must be overridden by implementing classes. */
	public abstract void useAbility();
	
	/**
	 *  This method is called every frame. */
	public final void update() {
		if (remainingCooldown > 0) {
			remainingCooldown -= Gdx.graphics.getDeltaTime();
		}
	}
	
	/**
	 * Checks whether the ability is not on cooldown.
	 * @return true - off cooldown; false - on cooldown. */
	public final boolean isOffCooldown() {
		if (remainingCooldown > 0) {
			return false;
		}
		return true;
	}
	
	public void drawIcon(SpriteBatch batch) {
		// TODO
		// draw icon on interface
		// draw cooldown animation
		// reuse some code from here in subclasses if needed
	}
}
