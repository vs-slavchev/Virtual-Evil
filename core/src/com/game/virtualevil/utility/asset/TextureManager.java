package com.game.virtualevil.utility.asset;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.utility.VirtualEvilError;

public class TextureManager {

	private Map<String, TextureRegion> texRegions = new HashMap<String, TextureRegion>();
	
	private final String path = "images/";
	private final String ext = ".png";
	private final String[] imgNames = {
			"enemy1",
			"enemy2",
			"enemy3",
			"enemy4",
			"abilitiesTileSet",
			"weaponsTileSet",
			"HPETileSet",
			"hero",
			"cyber_tileset",
			"cyber_minimap",
			"startScreenBackground",
			"chipBuildings",
			"mainMenuButtons",
			"Projectile",
			"explosion"
	};
	
	private final String[] abilityNames = {
			"Sprint",
			"Return",
			"Invulnerability",
			"Robot"
	};
	
	private final String[] weaponNames = {
			"PISTOL",
			"SHOTGUN",
			"AK47",
			"MACHINEGUN",
			"KATANA",
			"LIGHTSABRE",
			"AXE"
	};
	
	public TextureManager() {
		for (int i = 0; i < imgNames.length; i++) {
			loadTexture(imgNames[i]);
		}
		
		fixBleeding(texRegions.get("cyber_tileset"));
		fixBleeding(texRegions.get("chipBuildings"));

		cropInterfaceRegions();
	}

	public void loadTexture(String fname) {
		TextureRegion textureRegion = new TextureRegion(new Texture(path + fname + ext));		
		texRegions.put(fname, textureRegion);
	}

	public TextureRegion getImage(String s) {
		if (!texRegions.containsKey(s)) {
			VirtualEvilError.show("Image not found. File name:\n" + s);			
		}
		return texRegions.get(s);
	}
	
	public void disposeAllTextures() {
		for (TextureRegion tex : texRegions.values()) {
			tex.getTexture().dispose();
		}
	}

	private void cropInterfaceRegions() {
		TextureRegion healthAndEnergyInterface, piston, pistonArm, healthBar, energyBar,
				weaponsInterfacePanel, minimapInterface, abilitiesInterface;
		
		healthAndEnergyInterface = new TextureRegion(
				getImage("HPETileSet"), 0, 0, 384, 196);
		minimapInterface = new TextureRegion(
				getImage("HPETileSet"), 0, 518, 120, 120);
		abilitiesInterface = new TextureRegion(
				getImage("HPETileSet"), 0, 657, 298, 112);
		weaponsInterfacePanel = new TextureRegion(
				getImage("HPETileSet"), 0, 411, 207, 96);
		
		healthBar = new TextureRegion(getImage("HPETileSet"), 0, 281, 3, 39);
		piston = new TextureRegion(getImage("HPETileSet"), 0, 217, 29, 39);
		pistonArm = new TextureRegion(getImage("HPETileSet"), 0, 265, 3, 7);
		energyBar = new TextureRegion(getImage("HPETileSet"), 0, 329, 294, 78);
		
		texRegions.put("healthAndEnergy", healthAndEnergyInterface);
		texRegions.put("minimap", minimapInterface);
		texRegions.put("abilitiesPanel", abilitiesInterface);
		texRegions.put("weaponsPanel", weaponsInterfacePanel);
		texRegions.put("healthBar", healthBar);
		texRegions.put("piston", piston);
		texRegions.put("pistonArm", pistonArm);
		texRegions.put("energyBar", energyBar);
		
		for (int i = 0; i < abilityNames.length; i++){
			TextureRegion image = new TextureRegion(
					texRegions.get("abilitiesTileSet"),0 ,i * 32 ,32, 32);
			texRegions.put(abilityNames[i], image);
		}
		
		for (int j = 0; j < weaponNames.length; j++){
			TextureRegion sprite = new TextureRegion(
					texRegions.get("weaponsTileSet"), 0, j * 64, 64, 64);
			texRegions.put(weaponNames[j], sprite);
		}
	}

	/**
	* Magical code to fix the obscure tilemap bleeding bug;
	* thanks to awilki01 and Hollowbit for the solution */
	private static void fixBleeding(TextureRegion region) {
		float fix = 0.01f;
		float x = region.getRegionX();
		float y = region.getRegionY();
		float width = region.getRegionWidth();
		float height = region.getRegionHeight();
		float invTexWidth = 1f / region.getTexture().getWidth();
		float invTexHeight = 1f / region.getTexture().getHeight();
		region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight,
				(x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight);
	}
}
