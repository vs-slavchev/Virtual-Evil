package com.game.virtualevil.utility.asset;

public class AssetManager {

	private TextureManager textureManager;
	private FontManager fontManager;
	private SoundManager soundManager;
	private MusicManager musicManager;
	
	public AssetManager() {
		textureManager = new TextureManager();
		fontManager = new FontManager();
		// TODO init sound/music managers
	}

	public void disposeAllAssets() {
		fontManager.disposeAllFonts();
		textureManager.disposeAllTextures();
	}
	
	public TextureManager getTextureManager() {
		return textureManager;
	}

	public FontManager getFontManager() {
		return fontManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public MusicManager getMusicManager() {
		return musicManager;
	}
}
