package com.game.virtualevil.utility;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.Game;

public class MapManager {
	
	private Map map;
	private Game game;
	
	public MapManager(Game game) {
		this.game = game;
		map = new Map(this.game, "map1" );
	}
	
	public void loadMap(String mapName) {
		map = new Map(game, mapName);
	}
	
	public void drawMap(SpriteBatch batch, OrthographicCamera camera) {
		map.drawMap(batch, camera.position);
	}
	
	/* TODO:
	 * - method for requesting a reload of the entities in the entity manager;
	 * - refresh projectiles collection;
	 * - map: render the 2nd part of multi-part game objects
	 * (go only trough the tiles on the screen; do not save them in a collection);
	 * - going right sends the player to the map on the right: map names
	 * define their place in the world as 'C2R1' -  column 2 row 1 (load
	 * at game start if loading when switching maps is slow and noticeable);
	 */
	
	
	public Map getMap() {
		return map;
	}
}
