package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.virtualevil.Game;
import com.game.virtualevil.entity.EntityManager;
import com.game.virtualevil.entity.NonPlayerCharacter;
import com.game.virtualevil.utility.GameInputProcessor;
import com.game.virtualevil.utility.InputController;
import com.game.virtualevil.utility.Map;
import com.game.virtualevil.utility.UiInputProcessor;

public final class PlayGameState extends GameState{

	private EntityManager entityManager;
	private Map map;
	private InputController playerInputController;
	private GameInputProcessor gameInputProcessor;
	private UiInputProcessor uiInputProcessor;
	
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
		
		// set up input handling
		playerInputController = new InputController();
		gameInputProcessor = new GameInputProcessor(playerInputController);
		uiInputProcessor = new UiInputProcessor();
		/* Input is sent to the first registered input processor
		 * in the multiplexer. If it doesn't handle it, then 
		 * the input is forwarded to the second one: the one 
		 * responsible for the UI input handling. Then the
		 * multiplexer is registered as an input handler. */
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(gameInputProcessor);
		multiplexer.addProcessor(uiInputProcessor);
		Gdx.input.setInputProcessor(multiplexer);
		
		entityManager = new EntityManager(this);
	}

	@Override
	public void update(float delta) {
		entityManager.updateEntities(delta);
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
	
	public boolean isCharacterInView(NonPlayerCharacter npc) {
		Rectangle cameraView = map.calculateRenderRectIndices(camera.position);
		Vector2 mapIndicesNpc = map.positionToMapIndices(npc.getPosition());
		return mapIndicesNpc.x >= cameraView.x
				&& mapIndicesNpc.x <= cameraView.width
				&& mapIndicesNpc.y >= cameraView.height
				&& mapIndicesNpc.y <= cameraView.y;
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

	public InputController getInputContrller() {
		return playerInputController;
	}
}
