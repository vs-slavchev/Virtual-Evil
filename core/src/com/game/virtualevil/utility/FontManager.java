package com.game.virtualevil.utility;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/* The FontManager class contains all the fonts.
 * If there are more fonts: create a hashmap
 * and access them by a string. */
public class FontManager {

	private BitmapFont debugFont;
	
	public FontManager() {
		debugFont = new BitmapFont();
	}
	
	public BitmapFont getDebugFont() {
		return debugFont;
	}
	
	public void disposeFonts() {
		debugFont.dispose();
	}	
}
