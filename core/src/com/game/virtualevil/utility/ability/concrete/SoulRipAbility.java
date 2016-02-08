package com.game.virtualevil.utility.ability.concrete;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;

public class SoulRipAbility extends Ability {

	public SoulRipAbility(GameCharacter character) {
		super("SoulRip", character);
	}

	@Override
	public void useAbility() {
		character.modifyHealth(20);
		remainingCooldown = AbilityConstants.SPRINT_CD;
	}
}