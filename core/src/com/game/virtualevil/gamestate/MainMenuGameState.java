package com.game.virtualevil.gamestate;

import com.game.virtualevil.Game;

public class MainMenuGameState extends GameState{

	public MainMenuGameState(GameStateManager gsm, Game game) {
		super(gsm, game);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		super.dispose();
		
	}
	/*
	 * // number of points on the spiral var N = 40;
	 * 
	 * var maxRadius = 200;
	 * 
	 * // determines the inital spiral configuration var angleChange = 90;
	 * 
	 * // color of the spiral var redValue = 255; var greenValue = 0; var
	 * blueValue = 0;
	 * 
	 * // how much each color component changes every time var dRed = -1; var
	 * dGreen = 2; var dBlue = 3;
	 * 
	 * var draw = function() { background(0, 0, 0); stroke(redValue, greenValue,
	 * blueValue);
	 * 
	 * // coordinates of the current point var x = 200; var y = 200;
	 * 
	 * // coordinates of the previous point var lastX = 200; var lastY = 200;
	 * 
	 * for (var i = 0; i < N; i += 1) { // calculate position of current point
	 * var theta = angleChange * i*6; var radius = maxRadius * sqrt(i / N); x =
	 * 200 + radius * cos(theta); y = 200 + radius * sin(theta);
	 * 
	 * // draw a line from the last point to the current point line(lastX,
	 * lastY, x, y);
	 * 
	 * // update the previous point to be the current point lastX = x; lastY =
	 * y; }
	 * 
	 * // if colors go out of range, // flip the direction of change if
	 * (redValue < 0 || redValue > 255) { dRed = -dRed; } if (blueValue < 0 ||
	 * blueValue > 255) { dBlue = -dBlue; } if (greenValue < 0 || greenValue >
	 * 255) { dGreen = -dGreen; }
	 * 
	 * // increment colors redValue += dRed; blueValue += dBlue; greenValue +=
	 * dGreen;
	 * 
	 * // increment angleChange to rotate the spiral angleChange += 0.01; };
	 */

}
