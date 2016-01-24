package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;
import com.game.virtualevil.utility.ability.AbilityConstants;

/**
 * Contains information about which action is currently
 * active according to the player input.
 * @author vs */
public class InputController {

	private boolean left, right, up, down,
					switchWeapon, weaponAbility;
	
	private boolean[] numberKeys = new boolean[AbilityConstants.ABILITIES_COUNT];
	
	/** Used to avoid glitches when going back to 
	 * a state and input information is outdated. */
	public void reset() {
		left = right = up = down =
		switchWeapon = weaponAbility = false;
		for (int i = 0; i < numberKeys.length; i++) {
			numberKeys[i] = false;
		}
	}
	
	/* game crashing is ok, invalid array indexing error
	 * should not happen in production */
	public void setNumberKey(int keyNum, boolean value) {
		if (keyNum - 1  >= numberKeys.length) {
			Gdx.app.log("InputController error", "setNumberKey() parameter is invalid: "
					+ keyNum + " > " + numberKeys.length);
		}
		this.numberKeys[keyNum-1] = value;
	}
	
	public boolean getNumberKey(int keyNum) {
		if (keyNum - 1  >= numberKeys.length) {
			Gdx.app.log("InputController error", "setNumberKey() parameter is invalid: "
					+ keyNum + " > " + numberKeys.length);
		}
		return numberKeys[keyNum-1];
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isSwitchWeapon() {
		return switchWeapon;
	}

	public void setSwitchWeapon(boolean switchWeapon) {
		this.switchWeapon = switchWeapon;
	}

	public boolean isWeaponAbility() {
		return weaponAbility;
	}

	public void setWeaponAbility(boolean weaponAbility) {
		this.weaponAbility = weaponAbility;
	}

}
