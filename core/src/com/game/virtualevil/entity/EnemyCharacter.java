package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class EnemyCharacter extends NonPlayerCharacter {

	public EnemyCharacter(PlayGameState playGameState, final int x, final int y) {
		super(playGameState, x, y);
		
		weapon = new Weapon(WeaponType.MACHINE_GUN, this, playGameState);
		/*
		 * spriteSheet = playGameState.getAssetManager()
		 * .getTextureManager().getImage("bat"); setUpAnimation();
		 */
		spriteSheet = playGameState.getAssetManager().getTextureManager()
				.getImage("enemy1");
		setUpAnimation();
	}

	@Override
	protected void applyAction(final float delta) {
		super.applyAction(delta);

	}
}
