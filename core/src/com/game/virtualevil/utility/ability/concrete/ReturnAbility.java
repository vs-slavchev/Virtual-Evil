package com.game.virtualevil.utility.ability.concrete;

import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.ability.AbilityConstants;

/**
 * The position vector should be null before selecting the 
 * position and should be nulled after the position has been
 * used to go back to.
 * @author vs */
public class ReturnAbility extends Ability{
	
	private Vector2 position;

	public ReturnAbility(String abilityName, GameCharacter character) {
		super(abilityName, character, 0);
	}
	
	/**
	 * remaining cooldown is set here, because we need to have
	 * 2 different cooldowns for the different elements
	 * of the ability.
	 */
	@Override
	public void useAbility() {
		if (position == null) {
			position = new Vector2(character.getPosition());
			remainingCooldown = AbilityConstants.INITIAL_RETURN_CD;
			/* TODO: add an indication on the ground where the char
			 * will be returned to */
		} else {
			character.setPosition(position);
			position = null;
			remainingCooldown = AbilityConstants.RETURN_CD;
			/* TODO: 1. remove the indication;
			 * 2. maybe forbid returning if the location is very far
			 * away for balance purposes, and tell the player why
			 * they are not allowed back;
			 * 3. check if going back will put the player into another
			 * entity or solid terrain;
			 * 4. is this ability too complex? it has 2 CDs and maybe
			 * 2 different images/tooltip texts? maybe we need to
			 * separate it in two abilities and swap them? would the
			 * solution be more complex and error prone?*/
		}
	}

}
