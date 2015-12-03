package com.game.virtualevil.entity;

import com.game.virtualevil.Game;

public class EnemyCharacter extends NonPlayerCharacter{

	public EnemyCharacter(Game game, int x, int y) {
		super(game, x, y);
		spriteSheet = game.getTextureManager().getImage("bat");
		setUpAnimation();
	}

	@Override
	protected void applyAction(float delta) {
		// TODO
	}
}
