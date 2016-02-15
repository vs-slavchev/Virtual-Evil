package com.game.virtualevil.utility;

import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.utility.ability.AbilityConstants;

/**
 * Contains information about which action is currently
 * active according to the player input.
 * @author vs */
public class InputController {

	private boolean left, right, up, down,
					switchWeapon, weaponAbility,
					mouseLeftPressed;
	private boolean[] numberKeys = new boolean[AbilityConstants.ABILITIES_COUNT];
	private Vector2 mousePosition = new Vector2();
	
	/** Use to when going back to a state and input
	 * information is outdated. */
	public void reset() {
		left = right = up = down =
		switchWeapon = weaponAbility = 
		mouseLeftPressed = false;
		mousePosition.x = mousePosition.y = -1;
		
		for (int i = 0; i < numberKeys.length; i++) {
			numberKeys[i] = false;
		}
	}
	
	public void setNumberKey(final int keyNum, final boolean value) {
		if (keyNum >= numberKeys.length || keyNum < 0) {
			VirtualEvilError.show("Inputcontroller's setNumberKey() parameter is invalid:\n"
					+ keyNum);
		}
		this.numberKeys[keyNum-1] = value;
	}
	
	public boolean getNumberKey(final int keyNum) {
		if (keyNum >= numberKeys.length || keyNum < 0) {
			VirtualEvilError.show("Inputcontroller's getNumberKey() parameter is invalid:\n"
					+ keyNum);
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
	
	public void setMousePosition(int x, int y) {
		mousePosition.x = x;
		mousePosition.y = y;
	}
	
	public Vector2 getMousePosition() {
		return mousePosition;
	}

	public boolean isMouseLeftPressed() {
		return mouseLeftPressed;
	}

	public void setMouseLeftPressed(boolean mouseLeft) {
		this.mouseLeftPressed = mouseLeft;
	}

}
