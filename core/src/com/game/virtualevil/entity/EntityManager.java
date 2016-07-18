package com.game.virtualevil.entity;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.gamestate.PlayGameState;

public class EntityManager {

	private PlayerCharacter pc;
	private ArrayList<GameCharacter> characterList = new ArrayList<>();
	private ArrayList<GameCharacter> toRemove = new ArrayList<>();
	private EnemyFactory enemyFactory;
	private BulletPool bulletPool;
	private EffectAnimationsManager effectAnimationsManager;
	

	public EntityManager(PlayGameState playGameState) {
		pc = new PlayerCharacter(playGameState);
		bulletPool = new BulletPool(100, playGameState);
		effectAnimationsManager = new EffectAnimationsManager(playGameState);
		characterList.add(pc);
		
		enemyFactory = new EnemyFactory(playGameState);
		addCharacter("Soldier", 590, 3549);
		addCharacter("Mecha", 730, 2959);
		addCharacter("Heavy", 1350, 3650);
		addCharacter("Soldier", 3650, 1825);
		addCharacter("Soldier", 3650, 1825);
		addCharacter("Soldier", 1116, 562);
		addCharacter("Soldier", 3118, 2168);
		addCharacter("Soldier", 462, 1468);
		addCharacter("Soldier", 1578, 590);
		addCharacter("Soldier", 1569, 2872);
		addCharacter("Suit", 4300, 1300);
		addCharacter("Suit", 2252, 2206);
		addCharacter("Heavy", 3962, 2768);
		addCharacter("Mecha", 4176, 3630);
	}

	public void updateEntities(final float delta, PlayGameState playState) {
		bulletPool.deactivateBullet();
		for (GameCharacter gameCharacter : characterList) {
			gameCharacter.update(delta, playState);
		}

		for (GameCharacter gameCharacter : characterList) {
			for (int i = 0; i < bulletPool.getSize(); i++) {
				if (!bulletPool.getBullet(i).isActive()) {
					continue;
				}
				if (gameCharacter.overlap(bulletPool.getBullet(i))) {
					gameCharacter.modifyCurrentHealth(EntityConstants.BULLET_DAMAGE);
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
						A.modifyCurrentHealth(EntityConstants.BUMP_DAMAGE);
						B.modifyCurrentHealth(EntityConstants.BUMP_DAMAGE);
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

	private void addCharacter(String name, int x, int y){
		characterList.add(enemyFactory.createEnemy(name, x, y));
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
