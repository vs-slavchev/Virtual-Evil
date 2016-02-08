package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.utility.VirtualEvilException;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class Bullet {

	private Vector2 position, delta;
	private float speed;
	private TextureRegion img;

	public Bullet(Vector2 start, Vector2 mousePosition, WeaponType weaponType, TextureRegion img) {
		this.position = new Vector2(start);
		this.img = img;

		float distance = (float) Math.sqrt(
				  (mousePosition.x - start.x) * (mousePosition.x - start.x)
				+ (mousePosition.y - start.y) * (mousePosition.y - start.y));
		delta = new Vector2((mousePosition.x - start.x) / distance, (start.y - mousePosition.y) / distance);		

		switch (weaponType) {
		case PISTOL:
			this.speed = 200;
			break;
		case MACHINE_GUN:
			this.speed = 350;
			break;
		case RPG:
			this.speed = 50;
			break;
		default:
			try {
				throw new VirtualEvilException("Creating bullet from unknown weapon!");
			} catch (VirtualEvilException e) {
				VirtualEvilException.showException(e);
			}
			break;
		}
	}

	public void move() {
		position.x += delta.x * speed * Gdx.graphics.getDeltaTime();
		position.y += delta.y * speed * Gdx.graphics.getDeltaTime();
	}

	public void draw(SpriteBatch sb) {
		sb.draw(img, position.x - img.getRegionWidth()/2, position.y - img.getRegionHeight()/2);
	}
}
