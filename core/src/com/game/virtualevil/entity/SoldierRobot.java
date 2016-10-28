package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class SoldierRobot extends EnemyCharacter {

    public SoldierRobot(PlayGameState playGameState, final int x, final int y) {
        super(x, y);

        weapon = new Weapon(WeaponType.PISTOL, this, playGameState);
        spriteSheet = playGameState.getAssetManager().getTextureManager().getImage("enemy1");
        setUpAnimation();
    }

    @Override
    protected void applyAction(final float delta, PlayGameState playState) {
        super.applyAction(delta, playState);
    }
}
