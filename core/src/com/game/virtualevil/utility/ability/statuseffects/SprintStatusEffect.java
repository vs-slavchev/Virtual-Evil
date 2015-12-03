package com.game.virtualevil.utility.ability.statuseffects;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.AbilityConstants;

public class SprintStatusEffect extends StatusEffect{

	public SprintStatusEffect(GameCharacter character) {
		super("Sprinting", character);
		
		this.character.modifyMoveSpeed(AbilityConstants.SPRINT_BONUS);
		remainingDuration = AbilityConstants.SPRINT_DURATION;
	}
	
	@Override
	protected void expire() {
		character.modifyMoveSpeed( -AbilityConstants.SPRINT_BONUS);
	}

	@Override
	protected void onBearerAttack() {
		// intentionally empty for this class
	}

	@Override
	protected void onBearerHit() {
		// intentionally empty for this class
	}
}
