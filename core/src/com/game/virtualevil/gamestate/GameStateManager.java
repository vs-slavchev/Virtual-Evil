package com.game.virtualevil.gamestate;

import java.util.EnumMap;

import com.game.virtualevil.Game;

public class GameStateManager {
	
	public enum StateType {
		MAIN_MENU, PLAY
	}

	// an EnumMap containing the states in the game
	private EnumMap<StateType, GameState> stateMap = new EnumMap<>(StateType.class);
	// a reference to the current state
	private GameState currentState;
	
	public GameStateManager(Game game) {
		stateMap.put(StateType.PLAY, new PlayGameState(this, game));
		stateMap.put(StateType.MAIN_MENU, new MainMenuGameState(this, game));
		setState(StateType.PLAY);
	}
	
	public void setState(StateType state) {
		currentState = stateMap.get(state);
	}
	
	public void update(float delta) {
		currentState.update(delta);
	}
	
	public void draw() {
		currentState.draw();
	}
	
	public void disposeAllStates() {
		for (GameState state : stateMap.values()) {
			state.dispose();
		}
	}
}
