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
import com.game.virtualevil.utility.DebugInfo;
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
		uiInputProcessor = new UiInputProcessor(gsm);
		/* Input is sent to the first registered input processor
		 * in the multiplexer. If it doesn't handle it, then 
		 * the input is forwarded to the second one: the one 
		 * responsible for the UI input handling. Last line:
		 * the multiplexer is registered as an input handler. */
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(gameInputProcessor);
		multiplexer.addProcessor(uiInputProcessor);
		Gdx.input.setInputProcessor(multiplexer);
		
		DebugInfo.setUp(this);
		
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
		DebugInfo.start();
		DebugInfo.draw("x: " + (int) player.getPosition().x + "; y: " + (int) player.getPosition().y);
		DebugInfo.draw("FPS: " + Gdx.graphics.getFramesPerSecond());
		DebugInfo.draw("dir: " + player.getSpriteDirection());
		DebugInfo.draw("Screen: mouseX: " + (int) playerInputController.getMousePosition().x
				+ "; mouseY: " + (int) playerInputController.getMousePosition().y);
		DebugInfo.draw("World: mouseX: " + (int) screenToWorldCoords(playerInputController.getMousePosition()).x
				+ "; mouseY: "
				+ (int) screenToWorldCoords(playerInputController.getMousePosition()).y);
		DebugInfo.draw("mouseLeft pressed?: " + playerInputController.isMouseLeft());

		// draw the actual UI
	}
	
	public Vector2 screenToWorldCoords(Vector2 screenPosition) {
		Vector2 worldPosition = new Vector2();
		worldPosition.x = screenPosition.x + + camera.position.x - Gdx.graphics.getWidth()/2;
		worldPosition.y = screenPosition.y + camera.position.y - Gdx.graphics.getHeight()/2;
		return worldPosition;
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
