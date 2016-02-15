package com.game.virtualevil.utility;

import com.badlogic.gdx.Input.Buttons;
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
			inputController.setNumberKey(1, true);
			return true;
		case Keys.NUM_2:
			inputController.setNumberKey(2, true);
			return true;
		case Keys.NUM_3:
			inputController.setNumberKey(3, true);
			return true;
		case Keys.NUM_4:
			inputController.setNumberKey(4, true);
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
			inputController.setNumberKey(1, false);
			return true;
		case Keys.NUM_2:
			inputController.setNumberKey(2, false);
			return true;
		case Keys.NUM_3:
			inputController.setNumberKey(3, false);
			return true;
		case Keys.NUM_4:
			inputController.setNumberKey(4, false);
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
		switch (button) {
		case Buttons.LEFT:
			inputController.setMousePosition(screenX, screenY);
			inputController.setMouseLeftPressed(true);
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		switch (button) {
		case Buttons.LEFT:
			inputController.setMouseLeftPressed(false);
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		inputController.setMousePosition(screenX, screenY);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		inputController.setMousePosition(screenX, screenY);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
