package com.game.virtualevil.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.VirtualEvilError;
import com.game.virtualevil.utility.weapon.Bullet;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public class BulletPool {

    private Bullet[] arrayOfBullets;
    private PlayGameState playGameState;
    private int size;
    // inactivePointer points at the first inactive bullet in the array
    private int inactivePointer = 0;

    public BulletPool(int poolSize, PlayGameState playState) {
        this.size = poolSize;
        arrayOfBullets = new Bullet[size];
        this.playGameState = playState;

        for (int i = 0; i < size; i++) {
            arrayOfBullets[i] = new Bullet();
        }
    }

    public void activateBullet(Vector2 start, Vector2 target, WeaponType type,
                               TextureRegion region) {
        arrayOfBullets[inactivePointer++].activate(start, target, type, region);
        if (inactivePointer >= size) {
            inactivePointer = 0;
        }
    }

    public void deactivateBullet() {
        for (int i = 0; i < size; i++) {
            if (arrayOfBullets[i].isActive()
                    && !playGameState.isObjectInView(arrayOfBullets[i]
                    .getPosition())) {
                arrayOfBullets[i].deactivate();
            }
        }
    }

    public void moveBullets() {
        for (int i = 0; i < size; i++) {
            if (arrayOfBullets[i].isActive()) {
                arrayOfBullets[i].move();
            }
        }
    }

    public void drawBullets(SpriteBatch batch) {
        for (int i = 0; i < size; i++) {
            if (arrayOfBullets[i].isActive()) {
                arrayOfBullets[i].draw(batch);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Bullet getBullet(int x) {
        if (x >= arrayOfBullets.length || x < 0) {
            VirtualEvilError.show("invalid bullet index from bullet pool");
        }
        return arrayOfBullets[x];
    }

}
