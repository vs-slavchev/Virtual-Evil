package com.game.virtualevil.utility.ability.statuseffects;

import com.game.virtualevil.entity.GameCharacter;

/**
 * The main status effect class. Extend it to implement
 * concrete status effects.
 * @author vs */
public abstract class StatusEffect {

	// time is measured in seconds
	protected float remainingDuration;
	protected final GameCharacter character;
	// the unique name should describe a process
	protected final String statusEffectName;

	public StatusEffect(final String statusEffectName, GameCharacter character) {
		this.statusEffectName = statusEffectName;
		this.character = character;
	}

	/**
	 * Specifies action when the status effect bearer
	 * attacks. */
	protected abstract void onBearerAttack();

	/**
	 * Specifies action when the status effect bearer
	 * receives a hit. */
	protected abstract void onBearerHit();

	/**
	 * Specifies the end effect of the status effect:
	 * returning values to their previous state,
	 * starting an animation, activating some
	 * other effect or ability. */
	protected abstract void expire();
	
	/** Every effect is updated every frame */
	public void update(final float delta) {
		if (remainingDuration >= 0) {
			remainingDuration -= delta;
			if (remainingDuration < 0) {
				// remove the status effect from the character
				character.removeStatusEffect(this);
				expire();
			}
		}
	}

}
