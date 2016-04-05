package com.game.virtualevil.gamestate;

import java.util.EnumMap;

import com.game.virtualevil.Game;

public class GameStateManager {
	
	public enum StateType {
		MAIN_MENU, PLAY
	}

	private final Game game;
	// an EnumMap containing the states in the game
	private final EnumMap<StateType, GameState> stateMap = new EnumMap<>(StateType.class);
	// a reference to the current state
	private GameState currentState;
	
	public GameStateManager(Game game) {
		this.game = game;
		stateMap.put(StateType.PLAY, new PlayGameState(this, game));
		stateMap.put(StateType.MAIN_MENU, new MainMenuGameState(this, game));
		setCurrentState(StateType.PLAY);
	}
	
	public void setCurrentState(StateType state) {
		if (stateMap.containsKey(state) && stateMap.get(state) != null) {
			currentState = stateMap.get(state);			
		}
	}
	
	public void startNewGame() {
		// replace the old playgamestate instance
		stateMap.put(StateType.PLAY, new PlayGameState(this, game));
		setCurrentState(StateType.PLAY);
	}
	
	public void update(final float delta) {
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
