package com.game.virtualevil.entity;

import com.game.virtualevil.Game;

public class NonPlayerCharacter extends GameCharacter{
	
	public enum AI_State {
		IDLE, PATROL, ATTACK, FLEE
	}

	protected boolean isActive;
	protected AI_State aiState;
	
	public NonPlayerCharacter(Game game, int x, int y) {
		super(game);
		position.x = x;
		position.y = y;
	}

	@Override
	protected void applyAction(float delta) {
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
// setter factory? for npcs
