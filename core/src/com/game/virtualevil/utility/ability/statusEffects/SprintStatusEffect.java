package com.game.virtualevil.utility.ability.statusEffects;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.AbilityConstants;
import com.game.virtualevil.utility.ability.StatusEffect;

public class SprintStatusEffect extends StatusEffect{

	public SprintStatusEffect(String statusEffectName, GameCharacter character) {
		super(statusEffectName, character);
		
		this.character.modifyMoveSpeed(AbilityConstants.SPRINT_BONUS);
		remainingDuration = AbilityConstants.SPRINT_DURATION;
	}
	
	@Override
	public void expire() {
		super.expire();
		character.modifyMoveSpeed( -AbilityConstants.SPRINT_BONUS);
	}
}
