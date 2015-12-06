package com.game.virtualevil.utility;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * Responsible for handling input specific to
 * the player character only.
 * @author vs */
public class GameInputProcessor implements InputProcessor {

	private InputController inputController;

	public GameInputProcessor(InputController inputController) {
		this.inputController = inputController;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.A:
			inputController.setLeft(true);
			return true;
		case Keys.D:
			inputController.setRight(true);
			return true;
		case Keys.W:
			inputController.setUp(true);
			return true;
		case Keys.S:
			inputController.setDown(true);
			return true;
		case Keys.NUM_1:
			inputController.setOne(true);
			return true;
		case Keys.NUM_2:
			inputController.setTwo(true);
			return true;
		case Keys.NUM_3:
			inputController.setThree(true);
			return true;
		case Keys.NUM_4:
			inputController.setFour(true);
			return true;
		case Keys.Q:
			inputController.setSwitchWeapon(true);
			return true;
		case Keys.E:
			inputController.setWeaponAbility(true);
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.A:
			inputController.setLeft(false);
			return true;
		case Keys.D:
			inputController.setRight(false);
			return true;
		case Keys.W:
			inputController.setUp(false);
			return true;
		case Keys.S:
			inputController.setDown(false);
			return true;
		case Keys.NUM_1:
			inputController.setOne(false);
			return true;
		case Keys.NUM_2:
			inputController.setTwo(false);
			return true;
		case Keys.NUM_3:
			inputController.setThree(false);
			return true;
		case Keys.NUM_4:
			inputController.setFour(false);
			return true;
		case Keys.Q:
			inputController.setSwitchWeapon(false);
			return true;
		case Keys.E:
			inputController.setWeaponAbility(false);
			return true;
		default:
			return false;
		}
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
