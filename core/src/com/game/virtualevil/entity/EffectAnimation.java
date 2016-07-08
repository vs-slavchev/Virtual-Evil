package com.game.virtualevil.entity;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EffectAnimation {

	private Vector2 position;
	private Animation animation;
	private float frameTime;
	private float width, height;
	
	public EffectAnimation(TextureRegion spriteSheet, Vector2 position){
		this.position = new Vector2(position);
		TextureRegion[][] frames = spriteSheet.split(spriteSheet.getRegionWidth() / 7,
				 spriteSheet.getRegionHeight());
		animation = new Animation(0.15f, frames[0]);
		
		float scale = 1.0f + new Random().nextFloat() * 2;
		width = scale * animation.getKeyFrame(frameTime, false).getRegionWidth();
		height = scale * animation.getKeyFrame(frameTime, false).getRegionHeight();
	}
	
	protected void updateAnimation(final float delta) {
		frameTime += delta;
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion frame = animation.getKeyFrame(frameTime, false);
		batch.draw(frame, position.x, position.y, width, height);
	}
	
	public boolean isFinished(){
		return animation.isAnimationFinished(frameTime);
	}
}
