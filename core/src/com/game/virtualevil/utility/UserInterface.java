package com.game.virtualevil.utility;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.utility.asset.TextureManager;

public class UserInterface {
	
	private TextureRegion healthAndEnergyInterface, piston, pistonArm, healthBar, energyBar;
	
	public UserInterface (TextureManager tm){
		healthAndEnergyInterface = new TextureRegion(tm.getImage("HPETileSet"),
				0, 0, 384, 191);
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

	public TextureRegion getEnergyBar() {
		return energyBar;
	}
	
}
