package com.game.virtualevil.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.virtualevil.Game;

public class DesktopLauncher {
	@SuppressWarnings("unused")
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Virtual Evil";
		//config.addIcon(path, fileType);
		//config.fullscreen = true;
		config.vSyncEnabled = true;
		new LwjglApplication(new Game(), config);
	}
}
