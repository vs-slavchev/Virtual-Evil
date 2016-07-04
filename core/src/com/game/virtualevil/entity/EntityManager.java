package com.game.virtualevil.entity;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.gamestate.PlayGameState;

public class EntityManager {

	private PlayerCharacter pc;
	private ArrayList<GameCharacter> characterList = new ArrayList<>();
	private ArrayList<GameCharacter> toRemove = new ArrayList<>();
	private BulletPool bulletPool;
	private EffectAnimationsManager effectAnimationsManager;
	

	public EntityManager(PlayGameState playGameState) {
		pc = new PlayerCharacter(playGameState);
		bulletPool = new BulletPool(100, playGameState);
		effectAnimationsManager = new EffectAnimationsManager(playGameState);
		characterList.add(pc);
		
		EnemyFactory enemyFactory = new EnemyFactory(playGameState);
		characterList.add(enemyFactory.createEnemy("Soldier", 3650, 1825));
		characterList.add(enemyFactory.createEnemy("Suit", 4300, 1300));
		characterList.add(enemyFactory.createEnemy("Heavy", 3962, 2768));
		characterList.add(enemyFactory.createEnemy("Mecha", 4176, 3630));
		characterList.add(enemyFactory.createEnemy("Mecha", 730, 2959));
		characterList.add(enemyFactory.createEnemy("Soldier", 590, 3549));
		characterList.add(enemyFactory.createEnemy("Suit", 2252, 2206));
		characterList.add(enemyFactory.createEnemy("Soldier", 1578, 590));
		characterList.add(enemyFactory.createEnemy("Heavy", 1477, 1443));
		characterList.add(enemyFactory.createEnemy("Soldier", 1569, 2872));
		characterList.add(enemyFactory.createEnemy("Soldier", 1116, 562));
		characterList.add(enemyFactory.createEnemy("Soldier", 3118, 2168));
		characterList.add(enemyFactory.createEnemy("Soldier", 462, 1468));
	}

	public void updateEntities(final float delta) {
		
		bulletPool.deactivateBullet();
		for (GameCharacter gameCharacter : characterList) {
			gameCharacter.update(delta);
		}

		for (GameCharacter gameCharacter : characterList) {
			for (int i = 0; i < bulletPool.getSize(); i++) {
				if (!bulletPool.getBullet(i).isActive()) {
					continue;
				}
				if (gameCharacter.overlap(bulletPool.getBullet(i))) {
					gameCharacter.modifyCurrentHealth(EntityConstants.DEFALUT_BULLET_DAMAGE);
					bulletPool.getBullet(i).deactivate();
				}
			}
		}
		
		for (int i = 0; i < characterList.size(); i++) {
			GameCharacter A = characterList.get(i);
			for (int j = i + 1; j < characterList.size() - 1; j++) {
				GameCharacter B = characterList.get(j);
				if (A.overlap(B)) {
					if (!A.hasBeenHitRecently()) {
						A.modifyCurrentHealth(EntityConstants.DEFALUT_BUMP_DAMAGE);
						B.modifyCurrentHealth(EntityConstants.DEFALUT_BUMP_DAMAGE);
						A.setHasBeenHitRecently(true);
						B.setHasBeenHitRecently(true);
					}
				}
			}
		}
		
		characterList.removeAll(toRemove);
		bulletPool.moveBullets();
		effectAnimationsManager.update(delta);
	}
	

	public void drawEntities(SpriteBatch batch) {
		pc.draw(batch);
		for (GameCharacter gameCharacter : characterList) {
			gameCharacter.draw(batch);
		}
		
		bulletPool.drawBullets(batch);
		
		effectAnimationsManager.draw(batch);
	}
	
	public void removeCharacter(GameCharacter character){
		toRemove.add(character);
		effectAnimationsManager.add("explosion", character.position);
	}

	public PlayerCharacter getPlayer() {
		return pc;
	}

	public ArrayList<GameCharacter> getCharacterList() {
		return characterList;
	}

	public BulletPool getBulletPool() {
		return bulletPool;
	}
	
	
}
