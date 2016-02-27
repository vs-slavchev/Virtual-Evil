package com.game.virtualevil.utility;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.utility.asset.TextureManager;

public class UserInterface {
	
	private TextureRegion healthAndEnergyInterface, piston, pistonArm, healthBar, energyBar,
							weaponsInterface, pistol, shotgun, ak47, minigun, katana, lightSabre, axe,
							minimapInterface, 
							abilitiesInterface, BoT, remnants, invulnerability, robot;

	private HashMap<String, TextureRegion> abilitiesMap = new HashMap<>();
	
	public UserInterface (TextureManager tm){
		healthAndEnergyInterface = new TextureRegion(tm.getImage("HPETileSet"),
				0, 0, 384, 191);
		healthBar = new TextureRegion(tm.getImage("HPETileSet"), 0, 281, 3, 39);
		piston = new TextureRegion(tm.getImage("HPETileSet"), 0, 217, 29, 39);
		pistonArm = new TextureRegion(tm.getImage("HPETileSet"), 0, 265, 3, 7);
		energyBar = new TextureRegion(tm.getImage("HPETileSet"), 0, 329, 294,
				78);

		weaponsInterface = new TextureRegion(tm.getImage("HPETileSet"), 0, 411,
				207, 96);
		ak47 = new TextureRegion(tm.getImage("weaponsTileSet"), 0, 128, 64, 64);
		katana = new TextureRegion(tm.getImage("weaponsTileSet"), 0, 256, 64, 64);

		minimapInterface = new TextureRegion(tm.getImage("HPETileSet"), 0, 518,
				120, 120);
		
		abilitiesInterface = new TextureRegion(tm.getImage("HPETileSet"), 0, 657,
				298, 112);
		BoT = new TextureRegion(tm.getImage("abilitiesTileSet"),0 ,0 ,32, 32);
		remnants = new TextureRegion(tm.getImage("abilitiesTileSet"),0 ,32 ,32, 32);
		invulnerability = new TextureRegion(tm.getImage("abilitiesTileSet"),0 ,64 ,32, 32);
		robot = new TextureRegion(tm.getImage("abilitiesTileSet"),0 ,96 ,32, 32);
		
		abilitiesMap.put("Sprint", BoT);
		abilitiesMap.put("Return", remnants);
		abilitiesMap.put("Invulnerability", invulnerability);
		abilitiesMap.put("Robot", robot);
	}
	


	
	public TextureRegion getAbilitiesInterface() {
		return abilitiesInterface;
	}



	public TextureRegion getMinimapInterface() {
		return minimapInterface;
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
	
	public HashMap<String, TextureRegion> getAbilitiesMap() {
		return abilitiesMap;
	}

	
}
