package com.game.virtualevil.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EffectAnimation {

	private Vector2 position;
	private Animation animation;
	private float frameTime;
	
	public EffectAnimation(TextureRegion spriteSheet, Vector2 position){
		this.position = new Vector2(position);
		TextureRegion[][] frames = spriteSheet.split(spriteSheet.getRegionWidth() / 7,
				 spriteSheet.getRegionHeight());
		animation = new Animation(0.15f, frames[0]);
	}
	
	protected void updateAnimation(final float delta) {
		frameTime += delta;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(frameTime, false), position.x, position.y);
	}
	
	public boolean isFinished(){
		return animation.isAnimationFinished(frameTime);
	}
}
