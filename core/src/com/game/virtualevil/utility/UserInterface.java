package com.game.virtualevil.utility;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.utility.asset.TextureManager;

public class UserInterface {
	
	private TextureRegion healthAndEnergyInterface, piston, pistonArm, healthBar, energyBar,
							weaponsInterface, pistol, shotgun, ak47, minigun, katana, lightSabre, axe;
	public UserInterface (TextureManager tm){
		healthAndEnergyInterface = new TextureRegion(tm.getImage("HPETileSet"),
				0, 0, 384, 191);
		healthBar = new TextureRegion(tm.getImage("HPETileSet"),
				0, 281, 3, 39 );
		piston = new TextureRegion(tm.getImage("HPETileSet"),
				0, 217, 29, 39);
		pistonArm = new TextureRegion(tm.getImage("HPETileSet"),
				0, 265, 3, 7);
		energyBar = new TextureRegion(tm.getImage("HPETileSet"),
				0, 329, 294, 78);
		
		weaponsInterface = new TextureRegion(tm.getImage("HPETileSet"),
				0, 411, 207, 96 );
		
		ak47 = new TextureRegion(tm.getImage("weaponsTileSet"),
				0, 128, 64, 64 );
		katana = new TextureRegion(tm.getImage("weaponsTileSet"),
				0, 256, 64, 64);
	}
	


	public TextureRegion getHealthAndEnergyInterface() {
		return healthAndEnergyInterface;
	}

	public TextureRegion getPiston() {
		return piston;
	}

	public TextureRegion getPistonArm() {
		return pistonArm;
	}

	public TextureRegion getHealthBar() {
		return healthBar;
	}

	public TextureRegion getEnergyBar(int current, int max) {
		if(current<0){
			current = 0;
		}
		if(current > max){
			current =max;
		}
		return new TextureRegion(energyBar,
				0, 0, (int) (294*((double)current/max)), 78);
	}
	
	public TextureRegion getWeaponsInterface() {
		return weaponsInterface;
	}

	public TextureRegion getAk47() {
		return ak47;
	}

	public TextureRegion getKatana() {
		return katana;
	}
	
	
	
}
