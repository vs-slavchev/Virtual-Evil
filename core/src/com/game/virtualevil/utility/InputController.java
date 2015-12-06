package com.game.virtualevil.utility;

/**
 * Contains information about which action is currently
 * active according to the player input.
 * @author vs */
public class InputController {

	private boolean left, right, up, down,
					one, two, three, four,
					switchWeapon, weaponAbility;
	
	/** Used to avoid glitches when going back to 
	 * a state and input information is outdated. */
	public void reset() {
		left = right = up = down =
		one = two = three = four =
		switchWeapon = weaponAbility = false;
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

	public boolean isOne() {
		return one;
	}

	public void setOne(boolean one) {
		this.one = one;
	}

	public boolean isTwo() {
		return two;
	}

	public void setTwo(boolean two) {
		this.two = two;
	}

	public boolean isThree() {
		return three;
	}

	public void setThree(boolean three) {
		this.three = three;
	}

	public boolean isFour() {
		return four;
	}

	public void setFour(boolean four) {
		this.four = four;
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
