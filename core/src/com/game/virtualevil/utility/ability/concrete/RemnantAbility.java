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
public class RemnantAbility extends Ability{
	
	private Vector2 position;

	public RemnantAbility(String abilityName, GameCharacter character) {
		super(abilityName, character);
	}
	
	@Override
	public void useAbility() {
		if (position == null) {
			position = new Vector2(character.getPosition());
			/* TODO: add an indication on the ground where the char
			 * will be returned to */
		} else {
			character.setPosition(position);
			position = null;
			remainingCooldown = AbilityConstants.POSITION_RETURN_CD;
			/* TODO: 1. remove the indication;
			 * 2. maybe forbid returning if the location is very far
			 * away for balance purposes, and give indication to the
			 * player why they are not allowed back
			 * 3. check if going back will put the player into another
			 * entity or solid terrain */
		}
	}

}
