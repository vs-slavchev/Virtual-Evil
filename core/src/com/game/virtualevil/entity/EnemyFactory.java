package com.game.virtualevil.entity;

import com.game.virtualevil.gamestate.PlayGameState;

public class EnemyFactory {
	
	public EnemyCharacter createEnemy (PlayGameState playGameState, String type, final int x, final int y){
		if(type.equals("Soldier")){
			
		}
		return new SoldierRobot(playGameState, x, y);

	}
}
