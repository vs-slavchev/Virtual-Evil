package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class MechaFoxRobot extends EnemyCharacter {

	public MechaFoxRobot(PlayGameState playGameState, final int x, final int y) {
		super(playGameState, x, y);
		
		weapon = new Weapon(WeaponType.PISTOL, this, playGameState);
		spriteSheet = playGameState.getAssetManager().getTextureManager()
				.getImage("enemy4");
		setUpAnimation();
	}
	
	@Override
	protected void applyAction(final float delta) {
		super.applyAction(delta);

	}


}
