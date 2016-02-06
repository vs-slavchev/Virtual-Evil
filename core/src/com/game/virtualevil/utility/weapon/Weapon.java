package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;

public class Weapon {
	public enum WeaponType {
		PISTOL, MACHINE_GUN, RPG
	}

	private WeaponType weaponType;
	private String name;
	private int ammonition, maxMagazine, curMagazine, damage;
	private float rateOfFire;
	private PlayGameState playGameState;

	public Weapon(WeaponType cWeapon, PlayGameState playState) {
		this.playGameState = playState;
		
		switch (cWeapon) {
		case PISTOL:
			this.name = "Makarov";
			this.damage = 2;
			this.ammonition = 45;
			this.curMagazine = 8;
			this.maxMagazine = 8;
			this.rateOfFire = 15;
			break;
		case MACHINE_GUN:
			this.name = "AK - 47";
			this.damage = 8;
			this.ammonition = 90;
			this.curMagazine = 30;
			this.maxMagazine = 30;
			this.rateOfFire = 100;
			break;
		case RPG:
			this.name = "CG";
			this.damage = 50;
			this.ammonition = 5;
			this.curMagazine = 1;
			this.maxMagazine = 1;
			this.rateOfFire = 1;
			break;
		}
	}

	public void Fire() {
		if (ammonition >= 1) {
			Vector2 playerPosition = playGameState.getEntityManager().getPlayer().getPosition();
			Vector2 mousePosition = playGameState.getMouseWorldCoords();
			Bullet Go6u = new Bullet(playerPosition, mousePosition, weaponType);
			playGameState.getEntityManager().AddBullet(Go6u);
		}

	}

	public void Reload() {
		if (curMagazine != maxMagazine) {
			int toAdd = maxMagazine - curMagazine;
			ammonition -= toAdd;
			curMagazine += toAdd;
		}
	}
}