package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.virtualevil.Game;
import com.game.virtualevil.entity.EntityManager;
import com.game.virtualevil.entity.NonPlayerCharacter;
import com.game.virtualevil.entity.PlayerCharacter;
import com.game.virtualevil.utility.GameInputProcessor;
import com.game.virtualevil.utility.InputController;
import com.game.virtualevil.utility.Map;
import com.game.virtualevil.utility.UiInputProcessor;

public final class PlayGameState extends GameState{

	private EntityManager entityManager;
	private Map map;
	private OrthographicCamera uiCamera;
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
		// set up ui camera
		uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ScreenViewport viewport = new ScreenViewport(uiCamera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.apply();
		uiCamera.position.set(uiCamera.viewportWidth / 2, uiCamera.viewportHeight / 2, 0);
		uiCamera.update();
		
		// set up input handling
		playerInputController = new InputController();
		gameInputProcessor = new GameInputProcessor(playerInputController);
		uiInputProcessor = new UiInputProcessor();
		/* Input is sent to the first registered input processor
		 * in the multiplexer. If it doesn't handle it, then 
		 * the input is forwarded to the second one: the one 
		 * responsible for the UI input handling. Last line:
		 * the multiplexer is registered as an input handler. */
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
		batch.end();
		
		// draw using the UI camera
		batch.setProjectionMatrix(uiCamera.combined);
		batch.begin();
		drawUI(batch, entityManager.getPlayer());
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
	
	private void drawUI(SpriteBatch batch, PlayerCharacter player) {
		// draw debugging info top left
		if (Game.TESTING) {
			BitmapFont debugFont = assetManager.getFontManager().getDebugFont();

			debugFont.draw(batch, "x: " + (int) player.getPosition().x + "; y: " + (int) player.getPosition().y,
					5f, Gdx.graphics.getHeight() - 5f);
			debugFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 5f, Gdx.graphics.getHeight() - 20f);
			debugFont.draw(batch, "dir: " + player.getSpriteDirection(), 5f, Gdx.graphics.getHeight() - 35f);
		}
		
		//draw the actual UI
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
