package com.game.virtualevil.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Bullet;

public class EntityManager {

	private PlayerCharacter pc;
	private ArrayList<EnemyCharacter> enemiesList = new ArrayList<>();
	private ArrayList<Bullet> bullets = new ArrayList<>();

	public EntityManager(PlayGameState playGameState) {
		pc = new PlayerCharacter(playGameState);
		EnemyFactory enemy1 = new EnemyFactory();
		enemiesList.add(enemy1.createEnemy(playGameState, "Soldier", 3650, 1825));
		enemiesList.add(enemy1.createEnemy(playGameState, "Soldier", 4300, 1300));
	}

	public void updateEntities(final float delta) {
		pc.update(delta);
		for (EnemyCharacter enemy : enemiesList) {
			enemy.update(delta);
		}
		for (Bullet b : bullets) {
			b.move();
		}
	}

	public void drawEntities(SpriteBatch batch) {
		pc.draw(batch);
		for (EnemyCharacter enemy : enemiesList) {
			enemy.draw(batch);
		}
		for (Bullet b : bullets) {
			b.draw(batch);
		}
	}
	public void addBullet(Bullet newBullet)
	{
		bullets.add(newBullet);
	}

	public PlayerCharacter getPlayer() {
		return pc;
	}

	public ArrayList<EnemyCharacter> getEnemiesList() {
		return enemiesList;
	}
	
}
