package com.game.virtualevil;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.game.virtualevil.gamestate.GameStateManager;
import com.game.virtualevil.utility.VirtualEvilException;
import com.game.virtualevil.utility.asset.AssetManager;

public class Game extends ApplicationAdapter {
	
	private GameStateManager gsm;
	private AssetManager assetManager;
	
	// only for debug version
	public static boolean TESTING = true;

	@Override
	public void create() {
		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width,
				Gdx.graphics.getDesktopDisplayMode().height, true);
		Gdx.graphics.setVSync(true);
		
		// change the cursor; last 2 params are the cursor hotspot
		/*Cursor customCursor = Gdx.graphics.newCursor(
				new Pixmap(Gdx.files.internal("images/crosshair1.png")), 32, 32);
		Gdx.graphics.setCursor(customCursor);*/
		
		assetManager = new AssetManager();
		gsm = new GameStateManager(this);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		gsm.update(delta);
		gsm.draw();
	}

	@Override
	public void dispose() {
		gsm.disposeAllStates();
		assetManager.disposeAllAssets();
	}

	public GameStateManager getGsm() {
		return gsm;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}