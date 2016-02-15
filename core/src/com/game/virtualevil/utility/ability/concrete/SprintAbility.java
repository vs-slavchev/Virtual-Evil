package com.game.virtualevil.utility.ability.concrete;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;
import com.game.virtualevil.utility.ability.statuseffects.SprintStatusEffect;

public class SprintAbility extends Ability{

	public SprintAbility(String abilityName, GameCharacter character) {
		super(abilityName, character, AbilityConstants.SPRINT_CD);
	}
	
	@Override
	public void useAbility() {
		character.addStatusEffect(new SprintStatusEffect(character));
		character.modifyHealth(-AbilityConstants.SPRINT_HEALTH_COST);
	}
}
