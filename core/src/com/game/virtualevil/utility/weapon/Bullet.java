package com.game.virtualevil.utility.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.entity.GameObject;
import com.game.virtualevil.utility.VirtualEvilError;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class Bullet extends GameObject {

    private Vector2 delta;
    private float speed;
    private TextureRegion img;

    private boolean isActive;

    public Bullet() {
        isActive = false;

    }

    public void move() {
        position.x += delta.x * speed * Gdx.graphics.getDeltaTime();
        position.y += delta.y * speed * Gdx.graphics.getDeltaTime();
    }

    public void draw(SpriteBatch sb) {
        sb.draw(img, position.x - img.getRegionWidth() / 2, position.y - img.getRegionHeight() / 2);
    }

    public void activate(Vector2 start, Vector2 target, WeaponType weaponType, TextureRegion image) {
        isActive = true;

        this.position = calculateStartPosition(start, target);
        this.img = image;

        float distance = (float) Math.sqrt(
                (target.x - start.x) * (target.x - start.x)
                        + (target.y - start.y) * (target.y - start.y));
        delta = new Vector2((target.x - start.x) / distance, (target.y - start.y) / distance);

        switch (weaponType) {
            case PISTOL:
                this.speed = 200;
                break;
            case AK47:
                this.speed = 350;
                break;
            case RPG:
                this.speed = 50;
                break;
            default:
                VirtualEvilError.show("Creating bullet from unknown weapon:\n" + weaponType);
                break;
        }
    }

    // calculates and offsets the spawn position of the bullets
    public Vector2 calculateStartPosition(Vector2 start, Vector2 target) {

        int offset = 20;
        float marginX = target.x - start.x;
        float marginY = target.y - start.y;

        Vector2 vec = new Vector2(marginX, marginY).nor();
        vec.x *= offset;
        vec.y *= offset;
        vec.x += start.x;
        vec.y += start.y;
        return vec;
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }

    public Vector2 getPosition() {
        return position;
    }

}
