package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;

public abstract class EnemyCharacter extends NonPlayerCharacter {

    public EnemyCharacter(final int x, final int y) {
        super(x, y);
    }

    @Override
    protected void applyAction(final float delta, PlayGameState playState) {
        super.applyAction(delta, playState);

    }
}
