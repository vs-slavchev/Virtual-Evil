package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.entity.PlayerCharacter;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.asset.TextureManager;

public class UserInterface {

    private int width, height;
    private TextureManager tm;

    public UserInterface(TextureManager tm) {
        this.tm = tm;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    public void draw(SpriteBatch batch, PlayGameState playState) {
        PlayerCharacter player = playState.getEntityManager().getPlayer();

        DebugInfo.drawBasicDebugInfo(player, playState.getInputContrller());
        drawAbilitiesInterface(batch, player);
        drawHealthEnergyInterface(batch, player);
        drawDigitsHealth(batch, playState);
        drawWeaponsInterface(batch, playState);
        batch.draw(tm.getImage("minimap"), 5, 7, 240, 240);
    }

    private void drawAbilitiesInterface(SpriteBatch batch, PlayerCharacter player) {
        TextureRegion abilitiesInterface = tm.getImage("abilitiesPanel");
        batch.draw(abilitiesInterface, width / 2 - abilitiesInterface.getRegionWidth(),
                7, 596, 224);

        for (int i = 0; i < player.getAbilities().size(); i++) {
            TextureRegion icon = tm.getImage(player.getAbilities().get(i).getAbilityName());
            batch.draw(icon, width / 2 - 109 * 2 + i * 62 * 2, 87, 64, 64);
        }
    }

    private void drawHealthEnergyInterface(SpriteBatch batch, PlayerCharacter player) {
        double missingHealthRatio = player.calculateMissingHealthRatio();
        batch.draw(tm.getImage("pistonArm"), width - 304, height - 68, 265, 7);
        batch.draw(tm.getImage("healthBar"), width - 304, height - 84,
                (int) (missingHealthRatio * 265), 39);
        batch.draw(tm.getImage("piston"), width - 304 + (int) (missingHealthRatio * 265),
                height - 84);
        batch.draw(getEnergyBar(player.getCurrentEnergy(), player.getMaxEnergy()),
                width - 347, height - 186);

        TextureRegion healthAndEnergyInterface = tm.getImage("healthAndEnergy");
        batch.draw(healthAndEnergyInterface,
                width - healthAndEnergyInterface.getRegionWidth() - 10,
                height - healthAndEnergyInterface.getRegionHeight() - 10);
    }

    private void drawDigitsHealth(SpriteBatch batch, PlayGameState playState) {
        BitmapFont lcdFont = playState.getAssetManager().getFontManager().getHUDHealthFont(36);
        lcdFont.draw(batch,
                Integer.toString(playState.getEntityManager().getPlayer().getCurrentHealth()),
                width - 365, height - 45);
    }

    private void drawWeaponsInterface(SpriteBatch batch, PlayGameState playState) {
        PlayerCharacter player = playState.getEntityManager().getPlayer();
        TextureRegion weaponsPanel, mainWeapon, offWeapon;
        weaponsPanel = tm.getImage("weaponsPanel");
        mainWeapon = tm.getImage(player.getWeapon().getWeaponType().toString());
        offWeapon = tm.getImage(player.getOffWeapon().getWeaponType().toString());

        batch.draw(weaponsPanel, width - weaponsPanel.getRegionWidth() - 210,
                height - weaponsPanel.getRegionHeight() - 980, 414, 192);

        batch.draw(mainWeapon, width - mainWeapon.getRegionWidth() - 310,
                height - mainWeapon.getRegionHeight() - 970, 128, 128);

        batch.draw(offWeapon, width - offWeapon.getRegionWidth() - 115,
                height - offWeapon.getRegionHeight() - 950, 128, 128);

        BitmapFont lcdFont = playState.getAssetManager().getFontManager().getHUDHealthFont(36);
        lcdFont.draw(batch, playState.getEntityManager().getPlayer().getWeapon().getAmmoInfo(),
                width - 370, 180);
    }

    public TextureRegion getEnergyBar(int current, int max) {
        int currentEnergy = Math.max(current, 0);
        currentEnergy = Math.min(currentEnergy, max);

        return new TextureRegion(tm.getImage("energyBar"),
                0, 0, (int) (294 * ((double) currentEnergy / max)), 78);
    }
}
