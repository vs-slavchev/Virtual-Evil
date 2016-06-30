package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.game.virtualevil.Game;
import com.game.virtualevil.entity.PlayerCharacter;
import com.game.virtualevil.gamestate.PlayGameState;

public class DebugInfo {

	private static PlayGameState playGameState;
	private static int line = 0;
	private final static int LINE_HEIGHT = 20;
	
	public static void setUp(PlayGameState playGameState) {
		DebugInfo.playGameState = playGameState;
	}
	
	public static void start() {
		line = 0;
	}
	
	public static void draw(String text) {
		if (Game.TESTING) {
			BitmapFont debugFont = playGameState.getAssetManager().getFontManager().getDebugFont();
			debugFont.draw(playGameState.getBatch(),
					text, 10, Gdx.graphics.getHeight() - (line++ * LINE_HEIGHT));
		}
	}
	
	public static void drawBasicDebugInfo(PlayerCharacter player, InputController playerInputController){
		draw("debug info:");
		draw("x: " + (int) player.getPosition().x + "; y: " + (int) player.getPosition().y);
		draw("FPS: " + Gdx.graphics.getFramesPerSecond());
		draw("direction: " + player.getSpriteDirection());
		draw("Screen: mouseX: " + (int) playerInputController.getMousePosition().x);
		draw("Screen: mouseY: " + (int) playerInputController.getMousePosition().y);
		draw("World: mouseX: "
				+ (int) playGameState.screenToWorldCoords(playerInputController.getMousePosition()).x);
		draw("World: mouseY: "
				+ (int) playGameState.screenToWorldCoords(playerInputController.getMousePosition()).y);
		draw("mouseLeft pressed?: " + playerInputController.isMouseLeftPressed());
	}
}
