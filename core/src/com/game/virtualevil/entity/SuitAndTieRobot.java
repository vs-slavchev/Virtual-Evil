package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class SuitAndTieRobot extends EnemyCharacter {

    public SuitAndTieRobot(PlayGameState playGameState, final int x, final int y) {
        super(x, y);

        weapon = new Weapon(WeaponType.PISTOL, this, playGameState);
        spriteSheet = playGameState.getAssetManager().getTextureManager().getImage("enemy3");
        setUpAnimation();
    }

    @Override
    protected void applyAction(final float delta, PlayGameState playState) {
        super.applyAction(delta, playState);
    }
}
