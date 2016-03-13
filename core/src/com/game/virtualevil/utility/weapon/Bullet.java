package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.utility.VirtualEvilError;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class Bullet {

	private Vector2 position, delta;
	private float speed;
	private TextureRegion img;

	public Bullet(Vector2 start, Vector2 target, WeaponType weaponType, TextureRegion img) {
		this.position = new Vector2(start);
		this.img = img;
		//System.out.println("Bullet from: " + start + "Bullet target: " + target);
		float distance = (float) Math.sqrt(
				  (target.x - start.x) * (target.x - start.x)
				+ (target.y - start.y) * (target.y - start.y));
		delta = new Vector2((target.x - start.x) / distance, (target.y - start.y) / distance);		

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
			VirtualEvilError.show("Creating bullet from unknown weapon:\n" + weaponType);
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
