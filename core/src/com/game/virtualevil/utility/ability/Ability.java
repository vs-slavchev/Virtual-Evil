package com.game.virtualevil.utility.ability;

import com.badlogic.gdx.Gdx;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.statusEffects.SprintStatusEffect;

public class Ability {
	
	// time is measured in seconds
	private float remainingCooldown;
	// should describe an action
	private String abilityName;
	private GameCharacter character;
	
	public Ability(String abilityName, GameCharacter character) {
		this.abilityName = abilityName;
		this.character = character;
	}
	
	// update is called every frame
	public void update() {
		if (remainingCooldown > 0) {
			remainingCooldown -= Gdx.graphics.getDeltaTime();
		}
	}
	
	public void useAbility() {
		if (remainingCooldown > 0) {
			return;
		}
		switch (abilityName) {
		case "SPRINT":
			character.addStatusEffect(new SprintStatusEffect("Sprinting", character));
			remainingCooldown = AbilityConstants.SPRINT_CD;
			break;
		default:
			System.out.println("using invalid ability");
			break;
		}
	}
}
