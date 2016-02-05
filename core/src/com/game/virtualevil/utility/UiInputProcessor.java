package com.game.virtualevil.utility;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.virtualevil.Game;
import com.game.virtualevil.gamestate.GameStateManager;
import com.game.virtualevil.gamestate.GameStateManager.StateType;
import com.badlogic.gdx.Input.Keys;

/**
 * Responsible for UI and overall game related input.
 * Does NOT deal with player character specific input.
 * @author vs */
public class UiInputProcessor  implements InputProcessor{

	private static int screenShotCounter = 1;
	private GameStateManager gsm;
	
	public UiInputProcessor(GameStateManager gsm) {
		super();
		this.gsm = gsm;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.ESCAPE:
			gsm.setCurrentState(StateType.MAIN_MENU);
			return true;
		case Keys.F10:
			saveScreenshot();
			return true;
		case Keys.Z:
			Game.TESTING = !Game.TESTING;
			return true;
		case Keys.X:
			Gdx.app.exit();
			return true;
			
		//examples
		case Keys.TAB:
			//open inventory
			return true;
		case Keys.T:
			//open talents
			return true;
		case Keys.L:
			//open mission/quest/tasks log
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static void saveScreenshot() {
		try {
			FileHandle fh;
			do {
				fh = new FileHandle("screenshots/screenshot" + screenShotCounter++ + ".png");
			} while (fh.exists());
			Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0,
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			/* flip the resulting image as opengl draws the
			 * opposite way of writing to file */
			ByteBuffer pixels = pixmap.getPixels();
            int numBytes = Gdx.graphics.getWidth() * Gdx.graphics.getHeight() * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = Gdx.graphics.getWidth() * 4;
            for (int i = 0; i < Gdx.graphics.getHeight(); i++) {
                pixels.position((Gdx.graphics.getHeight() - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
            pixels.clear();
			
			PixmapIO.writePNG(fh, pixmap);
			pixmap.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
