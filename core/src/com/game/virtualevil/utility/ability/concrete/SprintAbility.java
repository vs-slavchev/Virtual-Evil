package com.game.virtualevil.utility.ability.concrete;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;
import com.game.virtualevil.utility.ability.statuseffects.SprintStatusEffect;

/**
 * This class should cover most abilities or at least the
 * ones without a complex effect and a need to store additional
 * variables.
 * @author vs */
public class SprintAbility extends Ability{

	public SprintAbility(GameCharacter character) {
		super("Sprint", character);
	}
	
	@Override
	public void useAbility() {
		character.addStatusEffect(new SprintStatusEffect(character));
		character.modifyHealth(-20);
		remainingCooldown = AbilityConstants.SPRINT_CD;
	}
}
