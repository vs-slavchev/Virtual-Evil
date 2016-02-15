package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;

public class EnemyCharacter extends NonPlayerCharacter{

	public EnemyCharacter(PlayGameState playGameState, final int x, final int y) {
		super(playGameState, x, y);
		spriteSheet = playGameState.getAssetManager()
				.getTextureManager().getImage("bat");
		setUpAnimation();
	}

	@Override
	protected void applyAction(final float delta) {
		super.applyAction(delta);
	}
}
