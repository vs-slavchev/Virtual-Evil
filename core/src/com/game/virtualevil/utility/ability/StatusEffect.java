package com.game.virtualevil.utility.ability;

import com.badlogic.gdx.Gdx;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.statusEffects.SprintStatusEffect;

/**
 * The main status effect class. The ConcreteAbility
 * class covers the more basic status effects. For
 * more complex implementations extend this class.
 * @author vs */
public abstract class StatusEffect {

	// time is measured in seconds
	protected float remainingDuration;
	protected StatusEffect statusEffect;
	protected GameCharacter character;
	// the unique name should describe a process
	protected String statusEffectName;

	public StatusEffect(String statusEffectName, GameCharacter character) {
		this.statusEffectName = statusEffectName;
		this.character = character;
	}

	/**
	 * A creating method for the factory for status effects.
	 * @param gameCharacter the bearer of the effect
	 * @return a status effect *//*
	public static StatusEffect createAndApplyStatusEffect(String statusEffectName,
			GameCharacter gameCharacter) {
		switch (statusEffectName) {
		case "Sprinting":
			return new SprintStatusEffect(statusEffectName, gameCharacter);
		default:
			System.out.println("Unhandled status effect in constructor");
			return null;
		}
	} */

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
	public void update() {
		if (remainingDuration >= 0) {
			remainingDuration -= Gdx.graphics.getDeltaTime();
			if (remainingDuration < 0) {
				// remove the status effect from the character
				character.removeStatusEffect(statusEffect);
				expire();
			}
		}
	}

}
