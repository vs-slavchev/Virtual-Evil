package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public abstract class  EnemyCharacter extends NonPlayerCharacter {

	public EnemyCharacter(PlayGameState playGameState, final int x, final int y) {
		super(playGameState, x, y);
	}

	@Override
	protected void applyAction(final float delta) {
		super.applyAction(delta);

	}
}
