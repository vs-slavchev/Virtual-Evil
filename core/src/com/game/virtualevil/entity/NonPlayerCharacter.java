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
	protected float timer;
	
	public NonPlayerCharacter(PlayGameState playGameState, int x, int y) {
		super(playGameState);
		inputController = new InputController();
		position.x = x;
		position.y = y;
		inputController.setDown(true);
		aiState = AI_State.PATROL;
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
		switch (aiState) {
		case PATROL:
			timer += delta;
			if (timer > 1.0) {
				timer = 0.0f;
				inputController.reset();
				switch ((int) (Math.random() * 4)) {
				case 0:
					inputController.setDown(true);
					break;
				case 1:
					inputController.setLeft(true);
					break;
				case 2:
					inputController.setRight(true);
					break;
				default:
					inputController.setUp(true);
					break;
				}
			}
			/* TODO change to attack when player is near:
			 * use the playGameState to access the entity manager and get
			 * a player reference to get player position...
			 */
			break;
		case ATTACK:
			// go towards player/attack
			break;
		case FLEE:
			// leg it
			break;
		default:
			// intentionally left empty
			break;
		}
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