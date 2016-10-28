package com.game.virtualevil.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {

    protected Vector2 position;
    protected int radius;

    public Vector2 getPosition() {
        return position;
    }

    public int getRadius() {
        return radius;
    }
}
