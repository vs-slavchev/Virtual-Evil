package com.game.virtualevil.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.Game;

public class EntityManager {
	private Game game;
	private PlayerCharacter pc;
	
	public EntityManager(Game game) {
		this.game = game;
		pc = new PlayerCharacter(game);
	}
	
	public void updateEntities(float delta) {
		pc.update(delta);
	}
	
	public void drawEntities(SpriteBatch batch) {
		pc.draw(batch);
	}
	
	public PlayerCharacter getPlayer() {
		return pc;
	}
}
