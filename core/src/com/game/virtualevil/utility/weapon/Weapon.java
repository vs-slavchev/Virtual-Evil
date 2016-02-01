package com.game.virtualevil.utility.weapon;

public class Weapon {

	private String Weapon;
	private int DMG;
	private int Ammonition, maxMagazine, curMagazine;

	private float rateOfFire;

	public Weapon(String cWeapon, int cDMG, int cAmmonition, int ccurMagazine, int cmaxMagazine, float crateOfFire) {
		this.Weapon = cWeapon;
		this.DMG = cDMG;
		this.Ammonition = cAmmonition;
		this.curMagazine = ccurMagazine;
		this.maxMagazine = cmaxMagazine;
		this.rateOfFire = crateOfFire;
		ccurMagazine = cmaxMagazine;
	}

	public void Fire() {
		if (Ammonition >= 1) {
			// Creating a bullet
			// 
		}
	}

	public void Reload() {
		if (curMagazine != maxMagazine) {
			int toAdd = maxMagazine - curMagazine;
			Ammonition -= toAdd;
			curMagazine += toAdd;
		}
	}

	public String getWeapon() {
		return Weapon;
	}

	public void setWeapon(String weapon) {
		Weapon = weapon;
	}

	public int getDMG() {
		return DMG;
	}

	public void setDMG(int dMG) {
		DMG = dMG;
	}

	public int getAmmonition() {
		return Ammonition;
	}

	public void setAmmonition(int ammonition) {
		Ammonition = ammonition;
	}

	public float getRateOfFire() {
		return rateOfFire;
	}

	public void setRateOfFire(float rateOfFire) {
		this.rateOfFire = rateOfFire;
	}
}