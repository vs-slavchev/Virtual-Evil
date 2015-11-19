package com.game.virtualevil.utility.ability;

import com.badlogic.gdx.Gdx;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.ability.concrete.ConcreteAbility;
import com.game.virtualevil.utility.ability.concrete.RemnantAbility;
import com.game.virtualevil.utility.ability.statusEffects.SprintStatusEffect;

/**
 * The main ability class. It uses the Factory
 * design pattern. Use createAbility() statically
 * to create an Ability instance; attemptToUse()
 * when trying to use an Ability and the update()
 * method to tick the cooldown.
 * @author vs */
public abstract class Ability {
	
	// time is measured in seconds
	protected float remainingCooldown;
	// should describe an action
	protected String abilityName;
	protected GameCharacter character;
	
	public Ability(String abilityName, GameCharacter character) {
		this.abilityName = abilityName;
		this.character = character;
	}
	
	/**
	 * The creating method for the factory.
	 * @param abilityName the name of the ability
	 * @param character a reference to the character
	 * @return the ability that was created */
	public static Ability createAbility(String abilityName, GameCharacter character) {
		switch (abilityName) {
		case AbilityConstants.SPRINT_NAME:
			return new ConcreteAbility(abilityName, character);
		case AbilityConstants.RETURN_NAME:
			return new RemnantAbility(abilityName, character);
		default:
			System.out.println("using invalid ability");
			return null;
		}
	}
	
	/**
	 * This is the method to be called when an ability
	 * <em>should<em> be used. */
	public void attemptToUse() {
		if (isOffCooldown()) {
			useAbility();
		}
	}
	
	/**
	 * The most important method in the Ability class.
	 * Must be overridden by implementing classes. */
	public abstract void useAbility();
	
	/**
	 *  This method is called every frame. */
	public void update() {
		if (remainingCooldown > 0) {
			remainingCooldown -= Gdx.graphics.getDeltaTime();
		}
	}
	
	/**
	 * Checks whether the ability is not on cooldown.
	 * @return true - off cooldown; false - on cooldown. */
	public boolean isOffCooldown() {
		if (remainingCooldown > 0) {
			return false;
		}
		return true;
	}
}
