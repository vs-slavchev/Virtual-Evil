package com.game.virtualevil.utility.ability;

import com.badlogic.gdx.Gdx;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.statusEffects.SprintStatusEffect;

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

	public static StatusEffect createStatusEffectByName(String statusEffectName,
			GameCharacter gameCharacter) {
		switch (statusEffectName) {
		case "Sprinting":
			return new SprintStatusEffect(statusEffectName, gameCharacter);
		default:
			System.out.println("Unhandled status effect in constructor");
			return null;
		}
	}

	// do something when the affected character attacks
	public void onBearerAttack() {
		// empty for the base class
	}

	// do something when the affected character is hit
	public void onBearerHit() {
		// empty for the base class
	}

	// every effect is updated every frame
	public void update() {
		if (remainingDuration >= 0) {
			remainingDuration -= Gdx.graphics.getDeltaTime();
			if (remainingDuration < 0) {
				expire();
			}
		}
	}

	public void expire() {
		// remove this status effect from the character
		character.removeStatusEffect(statusEffect);
	}
}
