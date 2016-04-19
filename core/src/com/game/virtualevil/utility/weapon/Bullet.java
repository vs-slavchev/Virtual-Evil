package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.entity.GameObject;
import com.game.virtualevil.utility.VirtualEvilError;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class Bullet extends GameObject{

	private Vector2 delta;
	private float speed;
	private TextureRegion img;
	
	private boolean isActive;

	public Bullet() {
		isActive = false;
		
	}

	public void move() {
		position.x += delta.x * speed * Gdx.graphics.getDeltaTime();
		position.y += delta.y * speed * Gdx.graphics.getDeltaTime();
	}

	public void draw(SpriteBatch sb) {
		sb.draw(img, position.x - img.getRegionWidth()/2, position.y - img.getRegionHeight()/2);
	}
	
	public void activate(Vector2 start, Vector2 target, WeaponType weaponType, TextureRegion img){
		isActive = true;
		
		this.position = calculateStartPosition(start, target);
		this.img = img;

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
	
	// calculates and offsets the spawn position of the bullets 
	public Vector2 calculateStartPosition (Vector2 start, Vector2 target){
		
		int offset = 20;
		float marginX = start.x - target.x;
		float marginY = start.y - target.y;
		
		/*
		System.out.println("Initial marginX: "+ marginX);
		System.out.println("Initial marginY: "+ marginY);
		int offsetRatio = (Math.abs(marginX) > Math.abs(marginY)) ? (int) (marginX*(radius+20)) : (int) (marginY*(radius+20));

		marginX = offsetRatio / marginX;
		marginY = offsetRatio / marginY;*/
		
/*		float ratio = marginX / marginY;
		System.out.println("Ratio: " + ratio);
		
		if(Math.abs(marginX) > Math.abs(marginY)){
			marginX = marginX < 0 ? (radius + 20) : ((radius + 20)*(-1));
		
			marginY = ratio * marginX;
		}
		else{
			marginY = marginY < 0 ? (radius + 20) : ((radius + 20)*(-1));
			marginX = ratio * marginX;
		}
		System.out.println("Post marginX: " + marginX);
		System.out.println("Post marginY: " + marginY);
		System.out.println("---------------------------------");*/
		float ratio = 0;
		if (Math.abs(marginX) > offset){
			ratio = offset / marginX;
		}
		if (Math.abs(marginY) > offset){
			ratio = offset / marginY;
		}
		marginY = marginY * ratio;
		marginX = marginX * ratio;
		
		return new Vector2(start.x + marginX, start.y + marginY);
	}

	public boolean isActive() {
		return isActive;
	}
	
	public void deactivate(){
		isActive = false;		
	}

	public Vector2 getPosition() {
		return position;
	}
	
}
