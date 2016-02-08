package com.game.virtualevil.utility.asset;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.game.virtualevil.utility.VirtualEvilException;

public class TextureManager {

	private Map<String, TextureRegion> texRegions = new HashMap<String, TextureRegion>();
	
	private final String path = "images/";
	private final String ext = ".png";
	
	public TextureManager() {
		String[] imgNames = {
				"weaponsTileSet",
				"HPETileSet",
				"hero",
				"cyber_tileset",
				"bat",
				"startScreenBackground",
				"chipBuildings",
				"mainMenuButtons",
		        "Projectile"
		};

		for (int i = 0; i < imgNames.length; i++) {
			loadTexture(imgNames[i]);
		}
		// fix bleeding for some tilesets
		fixBleeding(texRegions.get("cyber_tileset"));
		fixBleeding(texRegions.get("chipBuildings"));
	}

	@SuppressWarnings("null")
	public void loadTexture(String fname) {
		TextureRegion textureRegion = new TextureRegion(new Texture(path + fname + ext));		
		if (textureRegion != null) {
			texRegions.put(fname, textureRegion);
		}
	}

	public TextureRegion getImage(String s) {
		try {
			if (!texRegions.containsKey(s)) {
				throw new VirtualEvilException("Image not found in hashmap. key=\""
						+ s + "\"");
			}
		} catch (VirtualEvilException e) {
			VirtualEvilException.showException(e);
		}
		return texRegions.get(s);
	}
	
	public void disposeAllTextures() {
		for (TextureRegion tex : texRegions.values()) {
			tex.getTexture().dispose();
		}
	}
	
	// fix tilemap bleeding; thanks to awilki01 and Hollowbit for the solution
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
