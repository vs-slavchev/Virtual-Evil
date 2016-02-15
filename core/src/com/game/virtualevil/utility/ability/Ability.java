package com.game.virtualevil.utility.ability;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.utility.VirtualEvilError;
import com.game.virtualevil.utility.ability.concrete.ReturnAbility;
import com.game.virtualevil.utility.ability.concrete.SoulRipAbility;
import com.game.virtualevil.utility.ability.concrete.SprintAbility;

/**
 * The main ability class. Concrete implementations are in the
 * ability.concrete package.
 * Use attemptToUse() when trying to use an Ability and the update()
 * method to tick the cooldown.
 * @author vs */
public abstract class Ability {
	
	// time is measured in seconds
	protected float remainingCooldown;
	protected final float cooldown;
	protected final String abilityName;
	protected final GameCharacter character;
	
	public Ability(String abilityName, GameCharacter character, float cooldown) {
		this.abilityName = abilityName;
		this.character = character;
		this.cooldown = cooldown;
	}
	
	public static Ability create(final String abilityName, GameCharacter character) {
		switch (abilityName) {
		case "Sprint":
			return new SprintAbility(abilityName, character);
		case "Soul Rip":
			return new SoulRipAbility(abilityName, character);
		case "Return":
			return new ReturnAbility(abilityName, character);
		default:
			VirtualEvilError.show("creating an unknown ability:\n" + abilityName);
			// unreachable code
			return null;
		}
	}
	
	/**
	 * This is the method to be called when an ability
	 * <em>should<em> be used. */
	public final void attemptToUse() {
		if (isOffCooldown()) {
			remainingCooldown = cooldown;
			useAbility();
		}
	}
	
	/**
	 * The most important method in the Ability class.
	 * Must be overridden by implementing classes. */
	public abstract void useAbility();
	
	/**
	 *  This method is called every frame. */
	public final void update(final  float delta) {
		if (remainingCooldown > 0) {
			remainingCooldown -= delta;
		}
	}
	
	public final boolean isOffCooldown() {
		if (remainingCooldown > 0) {
			return false;
		}
		return true;
	}
	
	public void drawIcon(SpriteBatch batch) {
		// TODO
		// draw icon on interface
		// draw cooldown animation
		// draw a tooltip on mouseover? player should know what the ability does
		// reuse some code from here in subclasses if needed
	}
}
