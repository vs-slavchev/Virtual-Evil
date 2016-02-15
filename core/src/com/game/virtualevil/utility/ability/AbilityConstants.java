package com.game.virtualevil.utility.ability;

/**
 * Stores information about the abilities and the
 * related StatusEffects.
 * <p>
 * Duration and cooldown time is measured in seconds.
 * @author vs */
public class AbilityConstants {

	public static final int ABILITIES_COUNT = 4;
	
	// Sprint
	public static final float SPRINT_BONUS = 200f;
	public static final float SPRINT_DURATION = 5f;
	public static final float SPRINT_CD = 7f;
	public static final int SPRINT_HEALTH_COST = 20;
	
	// Return
	public static final float INITIAL_RETURN_CD = 2f;
	public static final float RETURN_CD = 5f;
	
	// Soul Rip
	public static final float SOUL_RIP_CD = 5f;
	public static final int SOUL_RIP_HEAL_AMOUNT = 20;
}
