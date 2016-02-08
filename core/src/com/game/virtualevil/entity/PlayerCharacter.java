package com.game.virtualevil.entity;

import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.ability.concrete.ReturnAbility;
import com.game.virtualevil.utility.ability.concrete.SoulRipAbility;
import com.game.virtualevil.utility.ability.concrete.SprintAbility;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public final class PlayerCharacter extends GameCharacter {

	private Weapon weapon;
	private final int maxEnergy;
	private int currentEnergy = 90;
	private float healthXCoordianteVisual;
	
	public PlayerCharacter(PlayGameState playGameState) {
		super(playGameState);
		this.healthXCoordianteVisual = currentHealth;
		this.maxEnergy = 100;
		
		inputController = playGameState.getInputContrller();
		setPosition(new Vector2(200, playGameState.getMap().getTotalHeight() - 250));
		spriteSheet = playGameState.getAssetManager().getTextureManager().getImage("hero");
		setUpAnimation();
		isActive = true;

		abilities.add(0, new SprintAbility(this));
		abilities.add(1, new ReturnAbility(this));
		abilities.add(2, new SoulRipAbility(this));

		weapon = new Weapon(WeaponType.MACHINE_GUN, playGameState);
	}

	/**
	 * Sets the player and camera positions as the player should always be in
	 * the center of the camera. */
	@Override
	public void setPosition(Vector2 position) {
		this.position.x = position.x;
		this.position.y = position.y;
		centerCameraOnPlayer();
	}

	private void centerCameraOnPlayer() {
		playGameState.getCamera().position.set(this.position.x, this.position.y, 0);
		playGameState.getCamera().update();
	}

	@Override
	public void applyAction(float delta) {
		super.applyAction(delta);

		// check the ability related input
		for (int i = 0; i < abilities.size(); i++) {
			if (inputController.getNumberKey(i + 1)) {
				abilities.get(i).attemptToUse();
			}
		}
		
		if(currentHealth != healthXCoordianteVisual){
			healthXCoordianteVisual += (currentHealth - healthXCoordianteVisual)/20.0;
		}
		
		weapon.updateTimer();
		if (inputController.isMouseLeft()) {
			weapon.fire();
		}
	}

	public float getHealthXCoordianteVisual() {
		return healthXCoordianteVisual;
	}

	/**
	 * Overridden because the camera needs to be on the player. */
	@Override
	protected void updateAnimation(float delta) {
		if (characterMoved) {
			centerCameraOnPlayer();
			frameTime += delta;
		}
	}
	
	public int getMaxEnergy() {
		return maxEnergy;
	}

	public int getCurrentEnergy() {
		return currentEnergy;
	}

}
