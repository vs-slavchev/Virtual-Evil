package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.InputController;

/**
 * NPCs should be controlled by changing their
 * input controller states.
 * @author vs */
public class NonPlayerCharacter extends GameCharacter{
	
	public enum AI_State {
		IDLE, PATROL, ATTACK, FLEE
	}

	protected AI_State aiState = AI_State.IDLE;
	
	public NonPlayerCharacter(PlayGameState playGameState, int x, int y) {
		super(playGameState);
		inputController = new InputController();
		position.x = x;
		position.y = y;
	}

	public void update(float delta) {
		/* toggle isActive according to whether character is in view.
		 * Here != works as XOR */
		if (isActive != playGameState.isCharacterInView(this)){
			isActive = !isActive;
		}
		super.update(delta);
	}
	
	@Override
	protected void applyAction(float delta) {
		super.applyAction(delta);
		/* transfer here some ai logic applicable
		 * for all npcs */
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