package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class HeavyRobot extends EnemyCharacter {

    public HeavyRobot(PlayGameState playGameState, final int x, final int y) {
        super(x, y);

        weapon = new Weapon(WeaponType.AK47, this, playGameState);
        spriteSheet = playGameState.getAssetManager().getTextureManager().getImage("enemy2");
        setUpAnimation();
    }

    @Override
    protected void applyAction(final float delta, PlayGameState playState) {
        super.applyAction(delta, playState);
    }

}
