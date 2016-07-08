package com.game.virtualevil.entity;

import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.gamestate.PlayGameState;
import com.game.virtualevil.utility.ability.Ability;
import com.game.virtualevil.utility.weapon.Weapon;
import com.game.virtualevil.utility.weapon.Weapon.WeaponType;

public final class PlayerCharacter extends GameCharacter {

	private final int maxEnergy;
	private int currentEnergy = 100;
	/* the visual representation of the current health;
	 * used in drawing the UI */
	private float healthXCoordianteVisual;
	private PlayGameState playState;

	public PlayerCharacter(PlayGameState playState) {
		super();
		this.healthXCoordianteVisual = currentHealth;
		this.maxEnergy = 100;
		this.playState = playState;
		this.inputController = playState.getInputContrller();

		setPosition(new Vector2(72, 3738));
		spriteSheet = playState.getAssetManager().getTextureManager()
				.getImage("hero");
		setUpAnimation();
		isActive = true;

		abilities.add(0, Ability.create("Sprint", this));
		abilities.add(1, Ability.create("Return", this));
		abilities.add(2, Ability.create("Invulnerability", this));
		abilities.add(3, Ability.create("Robot", this));

		weapon = new Weapon(WeaponType.MACHINE_GUN, this, playState);
	}

	/** Sets the player and camera positions as the player should always be in
	 * the center of the camera. */
	@Override
	public void setPosition(final Vector2 position) {
		super.setPosition(position);
		centerCameraOnPlayer();
	}

	private void centerCameraOnPlayer() {
		playState.getCamera().position.set(this.position.x,
				this.position.y, 0);
		playState.getCamera().update();
	}

	public void update(float delta, PlayGameState playGameState) {
		if (currentHealth != healthXCoordianteVisual) {
			healthXCoordianteVisual += (currentHealth - healthXCoordianteVisual) / 20.0;
		}
		super.update(delta, playGameState);
	}
	
	@Override
	public void applyAction(final float delta, PlayGameState playGameState) {
		super.prepareMovement(delta, playGameState);
		updateAnimation(delta);

		// check the ability related input
		for (int i = 0; i < abilities.size(); i++) {
			if (inputController.getNumberKey(i + 1)) {
				abilities.get(i).attemptToUse();
			}
		}
		if (inputController.isMouseLeftPressed()) {
			weapon.fire(new Vector2(playGameState.getMouseWorldCoords()));
		}
	}

	/** Overridden because the camera needs to be on the player. */
	@Override
	protected void updateAnimation(final float delta) {
		if (characterMoved) {
			centerCameraOnPlayer();
			frameTime += delta;
		}
	}
	
	public double calculateMissingHealthRatio() {
		return healthXCoordianteVisual/maxHealth;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public int getCurrentEnergy() {
		return currentEnergy;
	}
}
