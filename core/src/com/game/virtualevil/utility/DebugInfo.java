package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;
import com.game.virtualevil.Game;
import com.game.virtualevil.gamestate.GameState;

public class DebugInfo {

	private static int line = 0;
	private static GameState gameState;
	
	public static void setUp(GameState gameState) {
		DebugInfo.gameState = gameState;
	}
	
	public static void start() {
		line = 0;
	}
	
	public static void draw(String text) {
		if (Game.TESTING) {
			gameState.getAssetManager().getFontManager().getDebugFont().draw(gameState.getBatch(),
					text, 10, Gdx.graphics.getHeight() - (5 + line++ * 16));
		}
	}
}
