package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.entity.GameCharacter;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.VirtualEvilError;

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
	
	private GameCharacter gameCharacter;
	private PlayGameState playGameState;

	public Weapon(WeaponType cWeapon, GameCharacter character, PlayGameState playState) {
		this.gameCharacter = character;
		this.weaponType = cWeapon;
		this.playGameState = playState;
		
		switch (cWeapon) {
		case PISTOL:
			this.name = "Makarov";
			this.damage = 2;
			this.ammonition = 45;
			this.curMagazine = 8;
			this.maxMagazine = 8;
			this.rateOfFire = 0.7f;
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
			VirtualEvilError.show("Creating an unknown weapon:\n" + cWeapon);
			break;
		}
	}

	public void fire(Vector2 target) {
		if (ammonition >= 1 && timer > rateOfFire) {
			timer = 0;
			playGameState.getEntityManager().getBulletPool().activateBullet(gameCharacter.getPosition(), new Vector2(target), weaponType,
			playGameState.getAssetManager().getTextureManager().getImage("Projectile"));
			
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
	
	public WeaponType getWeaponType() {
		return weaponType;
	}
}