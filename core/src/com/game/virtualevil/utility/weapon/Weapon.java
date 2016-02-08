package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.VirtualEvilException;

/* TODO:
 * 1. lose ammo when shooting
 * 2. use reloading method
 * 3. delete bullets when off-screen
 * 4. draw weapon/ammo info in interface 
 * 5. throw exception in default case
 */

public class Weapon {
	public enum WeaponType {
		PISTOL, MACHINE_GUN, RPG
	}

	private WeaponType weaponType;
	private String name;
	private int ammonition, maxMagazine, curMagazine, damage;
	private float rateOfFire, timer;
	
	private PlayGameState playGameState;

	public Weapon(WeaponType cWeapon, PlayGameState playState) {
		this.playGameState = playState;
		this.weaponType = cWeapon;

		switch (cWeapon) {
		case PISTOL:
			this.name = "Makarov";
			this.damage = 2;
			this.ammonition = 45;
			this.curMagazine = 8;
			this.maxMagazine = 8;
			this.rateOfFire = 0.8f;
			break;
		case MACHINE_GUN:
			this.name = "AK - 47";
			this.damage = 8;
			this.ammonition = 90;
			this.curMagazine = 30;
			this.maxMagazine = 30;
			this.rateOfFire = 0.2f;
			break;
		case RPG:
			this.name = "CG";
			this.damage = 50;
			this.ammonition = 5;
			this.curMagazine = 1;
			this.maxMagazine = 1;
			this.rateOfFire = 5;
			break;
		default:
			try {
				throw new VirtualEvilException("Creating an unknown weapon!");
			} catch (VirtualEvilException e) {
				VirtualEvilException.showException(e);
			}
			break;
		}
	}

	public void fire() {
		if (ammonition >= 1 && timer > rateOfFire) {
			timer = 0;
			Vector2 playerPosition = playGameState.getEntityManager().getPlayer().getPosition();
			Vector2 mousePosition = playGameState.getMouseWorldCoords();
			Bullet Go6u = new Bullet(playerPosition, mousePosition, weaponType,
					playGameState.getAssetManager().getTextureManager().getImage("Projectile"));
			playGameState.getEntityManager().AddBullet(Go6u);
		}
	}

	public void updateTimer(){
		timer += Gdx.graphics.getDeltaTime();
	}
	
	public void reload() {
		if (curMagazine != maxMagazine) {
			int toAdd = maxMagazine - curMagazine;
			ammonition -= toAdd;
			curMagazine += toAdd;
		}
	}
}