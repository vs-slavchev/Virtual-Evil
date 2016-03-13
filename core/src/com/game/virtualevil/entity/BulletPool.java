package com.game.virtualevil.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Bullet;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class BulletPool {

	private Bullet[] bulletPool;
	private PlayGameState playGameState;
	private int size;
	// inactivePointer points at the first inactive bullet in the array
	private int inactivePointer = 0;

	public BulletPool(int poolSize, PlayGameState playState) {
		this.size = poolSize;
		bulletPool = new Bullet[size];
		this.playGameState = playState;

		for (int i = 0; i < size; i++) {
			bulletPool[i] = new Bullet();
		}
	}

	public void activateBullet(Vector2 start, Vector2 target, WeaponType type,
			TextureRegion region) {
		bulletPool[inactivePointer++].activate(start, target, type, region);
		if (inactivePointer >= size) {
			inactivePointer = 0;
		}
	}

	public void deactivateBullet() {
		for (int i = 0; i < size; i++) {
			if (bulletPool[i].isActive()
					&& !playGameState.isObjectInView(bulletPool[i]
							.getPosition())) {
				bulletPool[i].deactivate();
			}
		}
	}

	public void moveBullets() {
		for (int i = 0; i < size; i++) {
			if (bulletPool[i].isActive()) {
				bulletPool[i].move();
			}
		}
	}

	public void drawBullets(SpriteBatch batch) {
		for (int i = 0; i < size; i++) {
			if (bulletPool[i].isActive()) {
				bulletPool[i].draw(batch);
			}
		}
	}
}
