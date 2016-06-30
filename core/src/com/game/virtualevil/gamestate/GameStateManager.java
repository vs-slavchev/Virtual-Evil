package com.game.virtualevil.gamestate;

import java.util.EnumMap;

import com.game.virtualevil.Game;

public class GameStateManager {
	
	public enum StateType {
		MAIN_MENU, PLAY
	}

	private final Game game;
	private final EnumMap<StateType, GameState> statesMap = new EnumMap<>(StateType.class);
	private GameState currentState;
	
	public GameStateManager(Game game) {
		this.game = game;
		statesMap.put(StateType.PLAY, new PlayGameState(this, game));
		statesMap.put(StateType.MAIN_MENU, new MainMenuGameState(this, game));
		setCurrentState(StateType.PLAY);
	}
	
	public void setCurrentState(StateType state) {
		if (statesMap.containsKey(state) && statesMap.get(state) != null) {
			currentState = statesMap.get(state);			
		}
	}
	
	public void startNewGame() {
		statesMap.put(StateType.PLAY, new PlayGameState(this, game));
		setCurrentState(StateType.PLAY);
	}
	
	public void update(final float delta) {
		currentState.update(delta);
	}
	
	public void draw() {
		currentState.draw();
	}
	
	public void disposeAllStates() {
		for (GameState state : statesMap.values()) {
			state.dispose();
		}
	}

	public EnumMap<StateType, GameState> getStateMap() {
		return statesMap;
	}
	
}
