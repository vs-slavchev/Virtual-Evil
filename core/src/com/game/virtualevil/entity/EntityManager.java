package com.game.virtualevil.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Bullet;

public class EntityManager {

	private PlayerCharacter pc;
	private ArrayList<EnemyCharacter> enemiesList = new ArrayList<>();
	private BulletPool bulletPool;

	public EntityManager(PlayGameState playGameState) {
		pc = new PlayerCharacter(playGameState);
		bulletPool = new BulletPool(100, playGameState);
		EnemyFactory enemyFactory = new EnemyFactory();
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Soldier", 3650, 1825));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Suit", 4300, 1300));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Heavy", 3962, 2768));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Mecha", 4176, 3630));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Mecha", 730, 2959));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Soldier", 590, 3549));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Suit", 2252, 2206));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Soldier", 1578, 590));
		enemiesList.add(enemyFactory.createEnemy(playGameState, "Heavy", 1477, 1443));
	}

	public void updateEntities(final float delta) {
		pc.update(delta);
		bulletPool.deactivateBullet();
		for (EnemyCharacter enemy : enemiesList) {
			enemy.update(delta);
		}
		bulletPool.moveBullets();
	}

	public void drawEntities(SpriteBatch batch) {
		pc.draw(batch);
		for (EnemyCharacter enemy : enemiesList) {
			enemy.draw(batch);
		}
		
		bulletPool.drawBullets(batch);
	}

	public PlayerCharacter getPlayer() {
		return pc;
	}

	public ArrayList<EnemyCharacter> getEnemiesList() {
		return enemiesList;
	}

	public BulletPool getBulletPool() {
		return bulletPool;
	}
	
	
}
