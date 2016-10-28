package com.game.virtualevil.entity;

import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.InputController;

/**
 * NPCs should be controlled by changing their input controller states.
 */
public class NonPlayerCharacter extends GameCharacter {

    protected AI_State aiState = AI_State.IDLE;
    protected float timer;

    public NonPlayerCharacter(final int x, final int y) {
        super();
        inputController = new InputController();
        position.x = x;
        position.y = y;
        aiState = AI_State.PATROL;
    }

    public void update(final float delta, PlayGameState playState) {
        /* toggle isActive according to whether character is in view. Here = ! works as XOR */
        if (isActive != playState.isObjectInView(position)) {
            isActive = !isActive;
        }
        super.update(delta, playState);
    }

    @Override
    protected void applyAction(final float delta, PlayGameState playState) {
        if (!isActive) {
            return;
        }
        Vector2 playerPosition = playState.getEntityManager().getPlayer().getPosition();

        super.applyAction(delta, playState);
        switch (aiState) {
            case PATROL:
                double distance = Math.sqrt(Math.pow(position.x - playerPosition.x, 2)
                        + Math.pow(position.y - playerPosition.y, 2));
                if (distance < EntityConstants.ENEMY_ATTACK_RANGE) {
                    aiState = AI_State.ATTACK;
                }
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
                break;
            case ATTACK:
                weapon.fire(playerPosition);
                double distancePatrol = Math.sqrt(Math.pow(position.x - playerPosition.x, 2)
                        + Math.pow(position.y - playerPosition.y, 2));
                if (distancePatrol > EntityConstants.ENEMY_PATROL_RANGE) {
                    aiState = AI_State.PATROL;
                }
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

    public enum AI_State {
        IDLE, PATROL, ATTACK
    }
}