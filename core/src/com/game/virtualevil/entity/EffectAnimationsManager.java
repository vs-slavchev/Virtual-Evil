package com.game.virtualevil.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;

public class EffectAnimationsManager {
	private PlayGameState playState;
	private ArrayList<EffectAnimation> effectAnimations = new ArrayList<>();
	private ArrayList<EffectAnimation> finishedAnimations = new ArrayList<>();
	
	public EffectAnimationsManager(PlayGameState playState){
		this.playState = playState;
	}
	
	public void add(String name, Vector2 position){
		TextureRegion spriteSheet = playState.getAssetManager()
				.getTextureManager().getImage(name);
		effectAnimations.add(new EffectAnimation(spriteSheet, position));
	}
	
	public void update(float delta){
		for (EffectAnimation animation : effectAnimations){
			animation.updateAnimation(delta);
			if (animation.isFinished()){
				finishedAnimations.add(animation);
			}
		}
		effectAnimations.removeAll(finishedAnimations);
	}
	
	public void draw(SpriteBatch batch){
		for (EffectAnimation animation : effectAnimations){
			animation.draw(batch);
		}
	}
}
