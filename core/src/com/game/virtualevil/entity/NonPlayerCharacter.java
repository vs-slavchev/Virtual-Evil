package com.game.virtualevil.entity;

import com.badlogic.gdx.math.Vector2;
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
	
	public NonPlayerCharacter(PlayGameState playGameState, final int x, final int y) {
		super(playGameState);
		inputController = new InputController();
		position.x = x;
		position.y = y;
		aiState = AI_State.ATTACK;
	}

	public void update(final float delta) {
		/* toggle isActive according to whether character is in view.
		 * Here != works as XOR */
		if (isActive != playGameState.isCharacterInView(this)){
			isActive = !isActive;
		}
		super.update(delta);
	}
	
	@Override
	protected void applyAction(final float delta) {
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
			weapon.fire(playGameState.getEntityManager().getPlayer().getPosition());
			//if(playGameState.isCharacterInView(this)){
				/*this.weapon.fire(new Vector2(playGameState.getEntityManager().getPlayer().getPosition().x ,
						playGameState.getMap().getTotalHeight() - 
						playGameState.getEntityManager().getPlayer().getPosition().y));*/
			//}
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