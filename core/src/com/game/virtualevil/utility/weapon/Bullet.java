package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class Bullet {

	public Vector2 position, delta;
	public float speed;
	TextureRegion img;

	public Bullet(Vector2 start, Vector2 delta, WeaponType weaponType) {

		this.position = start;
		this.delta = delta;

		switch (weaponType) {
		case PISTOL:
			this.img = null;
			this.speed = 20;
			break;
		case MACHINE_GUN:
			this.img = null;
			this.speed = 20;
			break;
		case RPG:
			this.img = null;
			this.speed = 20;
			break;
		}

	}

	public void Move() {
		position.x += delta.x * speed * Gdx.graphics.getDeltaTime();
		position.y += delta.y * speed * Gdx.graphics.getDeltaTime();

	}

	public void Draw(SpriteBatch sb) {
		sb.draw(img, position.x, position.y);

	}
}
