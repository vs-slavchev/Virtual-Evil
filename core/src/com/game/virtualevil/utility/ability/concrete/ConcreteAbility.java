package com.game.virtualevil.utility.ability.concrete;

import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;
import com.game.virtualevil.utility.ability.statusEffects.SprintStatusEffect;

/**
 * This class should cover most abilities or at least the
 * ones without a complex effect and a need to store additional
 * variables.
 * @author vs */
public class ConcreteAbility extends Ability{

	public ConcreteAbility(String abilityName, GameCharacter character) {
		super(abilityName, character);
	}
	
	@Override
	public void useAbility() {
		switch (abilityName) {
		case AbilityConstants.SPRINT_NAME:
			character.addStatusEffect(new SprintStatusEffect("Sprinting", character));
			remainingCooldown = AbilityConstants.SPRINT_CD;
			break;
		default:
			System.out.println("using invalid ability");
			break;
		}
	}
}
