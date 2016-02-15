package com.game.virtualevil.utility.ability.concrete;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;

public class SoulRipAbility extends Ability {

	public SoulRipAbility(String abilityName, GameCharacter character) {
		super(abilityName, character, AbilityConstants.SOUL_RIP_CD);
	}

	@Override
	public void useAbility() {
		character.modifyHealth(AbilityConstants.SOUL_RIP_HEAL_AMOUNT);
	}
}