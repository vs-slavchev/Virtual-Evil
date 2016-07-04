package com.game.virtualevil.utility;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.virtualevil.entity.PlayerCharacter;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.asset.TextureManager;

public class UserInterface {
	
	private TextureRegion healthAndEnergyInterface, piston, pistonArm, healthBar, energyBar,
							weaponsInterface, minimapInterface, abilitiesInterface,
							pistol, shotgun, ak47, minigun, katana, lightSabre, axe;
	private HashMap<String, TextureRegion> abilitiesMap = new HashMap<>();
	private int width;
	private int height;
	
	public UserInterface (TextureManager tm){
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		cropInterfaceRegions(tm);
		
		String[] abilityNames = {"Sprint", "Return", "Invulnerability", "Robot" };
		for (int i = 0; i < abilityNames.length; i++){
			TextureRegion image = new TextureRegion(tm.getImage("abilitiesTileSet"),0 ,i * 32 ,32, 32);
			abilitiesMap.put(abilityNames[i], image);
		}
	}
	
	public void draw(SpriteBatch batch, PlayGameState playState){
		
		PlayerCharacter player = playState.getEntityManager().getPlayer();
		
		DebugInfo.drawBasicDebugInfo(player, playState.getInputContrller());
		drawAbilitiesInterface(batch, player);
		drawHealthEnergyInterface(batch, player);
		drawDigitsHealth(batch, playState, player);
		drawWeaponsInterface(batch);
		batch.draw(minimapInterface, 5, 7, 240, 240);
	}
	
	private void drawAbilitiesInterface(SpriteBatch batch, PlayerCharacter player) {
		//draw abilities frame
		batch.draw(abilitiesInterface, width/2 - abilitiesInterface.getRegionWidth(),
				7, 596, 224);
		
		//draw the actual abilities
		for(int i = 0; i < player.getAbilities().size();i++ ){
			TextureRegion icon = getAbilitiesMap().get(player.getAbilities().get(i).getAbilityName());
			batch.draw(icon, width/2 - 109*2 + i * 62*2, 87, 64, 64);
		}
	}

	private void drawHealthEnergyInterface(SpriteBatch batch, PlayerCharacter player) {
		double missingHealthRatio = player.calculateMissingHealthRatio();
		//draw piston arm
		batch.draw(pistonArm,  width - 304, height - 68,
				265, 7);
		//draw health
		batch.draw(healthBar, width - 304, height - 84,
				(int)(missingHealthRatio*265), 39);
		//draw piston		
		batch.draw(piston, width - 304 + (int)(missingHealthRatio*265), height - 84);
		//draw energy bar
		batch.draw(getEnergyBar(player.getCurrentEnergy(), player.getMaxEnergy()),
				width - 347, height - 186);
		
		// draw the actual UI
		batch.draw(healthAndEnergyInterface, width - healthAndEnergyInterface.getRegionWidth()- 10,
				height - healthAndEnergyInterface.getRegionHeight() - 10);
	}
	
	private void drawDigitsHealth(SpriteBatch batch, PlayGameState playState, PlayerCharacter player) {
		// draws the current health as LCD digits on the HUD
		BitmapFont lcdFont = playState.getAssetManager().getFontManager().getHUDHealthFont(36);
		lcdFont.draw(batch, Integer.toString(player.getCurrentHealth()),
				width - 365, height - 45);
	}

	private void drawWeaponsInterface(SpriteBatch batch) {
		//draws the weapons interface in the bottom right corner
		batch.draw(weaponsInterface, width - weaponsInterface.getRegionWidth()- 210,
				height - weaponsInterface.getRegionHeight() - 980, 414, 192);
		
		//draws the currently equipped weapons - firearm and melee weapon
		batch.draw(ak47, width - ak47.getRegionWidth()- 310, 
				height - ak47.getRegionHeight() - 970, 128, 128);
		
		batch.draw(katana, width - katana.getRegionWidth()- 115, 
				height - katana.getRegionHeight() - 950, 128, 128);
	}

	private void cropInterfaceRegions(TextureManager tm) {
		healthAndEnergyInterface = new TextureRegion(tm.getImage("HPETileSet"),
				0, 0, 384, 196);
		healthBar = new TextureRegion(tm.getImage("HPETileSet"), 0, 281, 3, 39);
		piston = new TextureRegion(tm.getImage("HPETileSet"), 0, 217, 29, 39);
		pistonArm = new TextureRegion(tm.getImage("HPETileSet"), 0, 265, 3, 7);
		energyBar = new TextureRegion(tm.getImage("HPETileSet"), 0, 329, 294,
				78);

		weaponsInterface = new TextureRegion(tm.getImage("HPETileSet"), 0, 411,
				207, 96);
		ak47 = new TextureRegion(tm.getImage("weaponsTileSet"), 0, 128, 64, 64);
		katana = new TextureRegion(tm.getImage("weaponsTileSet"), 0, 256, 64, 64);

		minimapInterface = new TextureRegion(tm.getImage("HPETileSet"), 0, 518,
				120, 120);
		
		abilitiesInterface = new TextureRegion(tm.getImage("HPETileSet"), 0, 657,
				298, 112);
	}
	
	public TextureRegion getEnergyBar(int current, int max) {
		int currentEnergy = current < 0 ? 0 : current;
		currentEnergy = current > max ? max : currentEnergy;
		
		return new TextureRegion(energyBar,
				0, 0, (int) (294*((double)currentEnergy/max)), 78);
	}
	
	public HashMap<String, TextureRegion> getAbilitiesMap() {
		return abilitiesMap;
	}
}
