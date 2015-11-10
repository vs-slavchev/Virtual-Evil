package com.game.virtualevil.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

	private Map<String, Texture> textures = new HashMap<String, Texture>();
	
	private final String path = "";
	private final String ext = ".png";
	
	public TextureManager() {
		String[] imgNames = {
				"hero",
				"forest_tiles"
		};

		for (int i = 0; i < imgNames.length;i++) {
			loadTexture(imgNames[i]);
		}
	}

	public Texture loadTexture(String fname) {
		Texture texture = new Texture(path + fname + ext);
		/*try {
			texture = new Texture(path + fname + ext);
		} catch (GdxRuntimeException e) {
			JOptionPane.showMessageDialog(null, "Error: \n" + path + fname + ext + "\nmissing!", "Error loading image!",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}*/
		textures.put(fname, texture);
		return texture;
	}

	public Texture getImage(String s) {
		if (!textures.containsKey(s)) {
			System.out.println(" Image not found in hashmap. key=\"" + s + "\"");
		}
		return textures.get(s);
	}
	
	public void disposeAllTextures() {
		for (Texture tex : textures.values()) {
			tex.dispose();
		}
	}

}
