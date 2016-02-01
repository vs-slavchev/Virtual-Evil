package com.game.virtualevil.utility.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/* The FontManager class contains all the fonts.
 * If there are more fonts: create a hashmap
 * and access them by a string. */
public class FontManager {

	private final String path = "fonts/", format = ".fnt";
	private BitmapFont debugFont;
	private BitmapFont droidSansMono14;
	
	public FontManager() {
		debugFont = new BitmapFont();
		droidSansMono14 = new BitmapFont(Gdx.files.internal(path + "droidSansMono14" + format));
	}
	
	public BitmapFont getDebugFont() {
		return debugFont;
	}
	
	public BitmapFont getDroidSansMono14() {
		return droidSansMono14;
	}
	
	public void disposeAllFonts() {
		debugFont.dispose();
		droidSansMono14.dispose();
	}
}
