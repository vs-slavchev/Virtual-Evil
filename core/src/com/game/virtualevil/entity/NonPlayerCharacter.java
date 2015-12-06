package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.InputController;

/**
 * NPCs should be controlled by changing their
 * input controller states.
 * @author vs
 *
 */
public class NonPlayerCharacter extends GameCharacter{
	
	public enum AI_State {
		IDLE, PATROL, ATTACK, FLEE
	}

	protected boolean isActive;
	protected AI_State aiState;
	
	public NonPlayerCharacter(PlayGameState playGameState, int x, int y) {
		super(playGameState);
		inputController = new InputController();
		position.x = x;
		position.y = y;
	}

	@Override
	protected void applyAction(float delta) {
		super.applyAction(delta);
		// TODO
		// activate when in viewport
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public AI_State getAiState() {
		return aiState;
	}
}