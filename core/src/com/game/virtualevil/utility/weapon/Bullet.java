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

	public Bullet(Vector2 start, Vector2 mousePosition, WeaponType weaponType, TextureRegion img) {

		float distance = (float) Math.sqrt((mousePosition.x - start.x) * (mousePosition.x - start.x)
				+ (mousePosition.y - start.y) * (mousePosition.y - start.y));
		delta = new Vector2((mousePosition.x - start.x) / distance, (start.y - mousePosition.y) / distance);
		this.position = new Vector2(start);
		this.img = img;

		switch (weaponType) {
		case PISTOL:

			this.speed = 20;
			break;
		case MACHINE_GUN:
			this.speed = 50;
			break;
		case RPG:
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
