package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.VirtualEvilError;

/* TODO:
 * lose ammo when shooting
 * use reloading method
 * draw weapon/ammo info in interface 
 */

public class Weapon {
	public enum WeaponType {
		PISTOL, MACHINE_GUN, RPG
	}

	private WeaponType weaponType;

	private int currentAmmo, maxAmmo, totalAmmo, damage;
	private float rateOfFire, timer;
	
	private GameCharacter gameCharacter;
	private PlayGameState playGameState;

	public Weapon(WeaponType cWeapon, GameCharacter character, PlayGameState playState) {
		this.gameCharacter = character;
		this.weaponType = cWeapon;
		this.playGameState = playState;
		
		switch (cWeapon) {
		case PISTOL:
			this.damage = 2;
			this.currentAmmo = 8;
			this.totalAmmo = 45;
			this.rateOfFire = 0.7f;
			break;
		case MACHINE_GUN:
			this.damage = 8;
			this.totalAmmo = 90;
			this.maxAmmo = 30;
			this.rateOfFire = 0.2f;
			break;
		case RPG:
			this.damage = 50;
			this.totalAmmo = 5;
			this.maxAmmo = 1;
			this.rateOfFire = 5;
			break;
		default:
			VirtualEvilError.show("Creating unknown weapon:\n" + cWeapon);
			break;
		}
		currentAmmo = maxAmmo;
	}

	public void fire(Vector2 target) {
		if (currentAmmo >= 1 && timer > rateOfFire) {
			timer = 0;
			playGameState.getEntityManager().getBulletPool().activateBullet(
					gameCharacter.getPosition(), new Vector2(target), weaponType,
					playGameState.getAssetManager().getTextureManager().getImage("Projectile"));
		}
	}
	
	public void updateTimer(){
		timer += Gdx.graphics.getDeltaTime();
	}
	
	public void reload() {
		if (currentAmmo != maxAmmo) {
			int toAdd = maxAmmo - currentAmmo;
			totalAmmo -= toAdd;
			currentAmmo += toAdd;
		}
	}
	
	public WeaponType getWeaponType() {
		return weaponType;
	}
	
	public String getAmmoInfo(){
		return String.format("%d / %d", currentAmmo, maxAmmo);
	}
}