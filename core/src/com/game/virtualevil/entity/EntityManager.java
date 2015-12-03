package com.game.virtualevil.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.Game;

public class EntityManager {
	
	private PlayerCharacter pc;
	private ArrayList<EnemyCharacter> enemiesList = new ArrayList<>();
	
	public EntityManager(Game game) {
		pc = new PlayerCharacter(game);
		enemiesList.add(new EnemyCharacter(game, 1000, 3000));
	}
	
	public void updateEntities(float delta) {
		pc.update(delta);
		for (EnemyCharacter enemy : enemiesList) {
			enemy.update(delta);
		}
	}
	
	public void drawEntities(SpriteBatch batch) {
		pc.draw(batch);
		for (EnemyCharacter enemy : enemiesList) {
			enemy.draw(batch);
		}
	}
	
	public PlayerCharacter getPlayer() {
		return pc;
	}
}
