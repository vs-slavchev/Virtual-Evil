package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.virtualevil.Game;
import com.game.virtualevil.entity.EntityManager;
import com.game.virtualevil.utility.Map;

public class PlayGameState extends GameState{

	private EntityManager entityManager;
	private Map map;
	
	public PlayGameState(GameStateManager gsm, Game game) {
		super(gsm, game);
	}

	@Override
	public void initialize() {
		map = new Map(this, "map1");
		camera = new OrthographicCamera(
				Gdx.graphics.getWidth()/2,
				Gdx.graphics.getHeight()/2);
		camera.update();
		
		entityManager = new EntityManager(this);
	}

	@Override
	public void update(float delta) {
		handleInput();
		
		entityManager.updateEntities(delta);
	}

	/** keys not related to player character
	 * control (for instance menus/tabs/options) */
	@SuppressWarnings("static-method")
	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		// TODO handle more keys
	}

	@Override
	public void draw() {
		super.draw();
		batch.begin();
		map.drawMap(batch, camera.position);
		entityManager.drawEntities(batch);
		map.drawLayer2Map(batch, camera.position);
		entityManager.getPlayer().drawUI(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	public Map getMap() {
		return map;
	}
}
