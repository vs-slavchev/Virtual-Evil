package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

/**
 * Responsible for UI and overall game related input.
 * Does NOT deal with player character specific input.
 * @author vs */
public class UiInputProcessor  implements InputProcessor{

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.ESCAPE:
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
			//open quest/tasks log
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

}
