package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.entity.EntityManager;
import com.game.virtualevil.entity.PlayerCharacter;
import com.game.virtualevil.gamestate.PlayGameState;
import com.sun.javafx.scene.EnteredExitedHandler;

public class Weapon {
	public enum WeaponType {
		Pistol, MachineGun, RPG
	}

	private WeaponType weaponType;
	private String name;
	private int dMG;
	private int ammonition, maxMagazine, curMagazine;
	private PlayGameState playGameState;
	private float rateOfFire;

	public Weapon(WeaponType cWeapon, PlayGameState x) {
		this.playGameState = x;
		switch (cWeapon) {

		case Pistol:

			this.name = "Makarov";
			this.dMG = 2;
			this.ammonition = 45;
			this.curMagazine = 8;
			this.maxMagazine = 8;
			this.rateOfFire = 15;
			break;

		case MachineGun:
			this.name = "AK - 47";
			this.dMG = 8;
			this.ammonition = 90;
			this.curMagazine = 30;
			this.maxMagazine = 30;
			this.rateOfFire = 100;
			break;

		case RPG:
			this.name = "CG";
			this.dMG = 50;
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