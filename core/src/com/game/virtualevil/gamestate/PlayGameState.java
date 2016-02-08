package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.game.virtualevil.utility.UserInterface;

public final class PlayGameState extends GameState {

	private EntityManager entityManager;
	private Map map;
	private OrthographicCamera uiCamera;
	private InputController playerInputController;
	private GameInputProcessor gameInputProcessor;
	private UiInputProcessor uiInputProcessor;
	private UserInterface userInterface;

	public PlayGameState(GameStateManager gsm, Game game) {
		super(gsm, game);
	}

	@Override
	public void initialize() {
		map = new Map(this, "map1");
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		camera.update();

		// set up ui camera
		uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		ScreenViewport viewport = new ScreenViewport(uiCamera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.apply();
		uiCamera.position.set(uiCamera.viewportWidth / 2,
				uiCamera.viewportHeight / 2, 0);
		uiCamera.update();

		// user interface initialization
		userInterface = new UserInterface(assetManager.getTextureManager());

		// set up input handling
		playerInputController = new InputController();
		gameInputProcessor = new GameInputProcessor(playerInputController);
		uiInputProcessor = new UiInputProcessor(gsm);
		/*
		 * Input is sent to the first registered input processor in the
		 * multiplexer. If it doesn't handle it, then the input is forwarded to
		 * the second one: the one responsible for the UI input handling. Last
		 * line: the multiplexer is registered as an input handler.
		 */
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
		DebugInfo.start();
		batch.begin();
		map.drawMap(batch, camera.position);
		entityManager.drawEntities(batch);
		map.drawLayer2Map(batch, camera.position);
		batch.end();

		// draw using the UI camera
		batch.setProjectionMatrix(uiCamera.combined);
		batch.begin();
		drawUI(entityManager.getPlayer());
		batch.end();
	}

	public boolean isCharacterInView(NonPlayerCharacter npc) {
		Rectangle cameraView = map.calculateRenderRectIndices(camera.position);
		Vector2 npcMapIndices = map.positionToMapIndices(npc.getPosition());
		return npcMapIndices.x >= cameraView.x
				&& npcMapIndices.x <= cameraView.width
				&& npcMapIndices.y >= cameraView.height
				&& npcMapIndices.y <= cameraView.y;
	}

	private void drawUI(PlayerCharacter player) {
		// draw debugging info top left
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
		double missingHealthRatio = (double)(entityManager.getPlayer().getHealthXCoordianteVisual())/entityManager.getPlayer().getMaxHealth();
		//draw piston arm
		batch.draw(userInterface.getPistonArm(),  Gdx.graphics.getWidth() - 304, Gdx.graphics.getHeight() - 68,
				265, 7);
		//draw health
		batch.draw(userInterface.getHealthBar(), Gdx.graphics.getWidth() - 304, Gdx.graphics.getHeight() - 84,
				(int)(missingHealthRatio*265), 39);
		//draw piston		
		batch.draw(userInterface.getPiston(), Gdx.graphics.getWidth() - 304 + (int)(missingHealthRatio*265), Gdx.graphics.getHeight() - 84);
		//draw energy bar
		batch.draw(userInterface.getEnergyBar(entityManager.getPlayer().getCurrentEnergy(), entityManager.getPlayer().getMaxEnergy()),
				Gdx.graphics.getWidth() - 347, Gdx.graphics.getHeight() - 186);
		
		batch.draw(userInterface.getHealthAndEnergyInterface(), Gdx.graphics.getWidth() - userInterface.getHealthAndEnergyInterface().getRegionWidth()- 10,
				Gdx.graphics.getHeight() - userInterface.getHealthAndEnergyInterface().getRegionHeight() - 10);
		
		//draws the current hp as LCD digits on the HUD
		assetManager.getFontManager().getHUDHealthFont(36).draw(batch, Integer.toString( entityManager.getPlayer().getCurrentHealth()),
				Gdx.graphics.getWidth() - 365, Gdx.graphics.getHeight() - 45);
		
		//draws the weapons interface in the bottom right corner
		batch.draw(userInterface.getWeaponsInterface(), Gdx.graphics.getWidth() - userInterface.getWeaponsInterface().getRegionWidth()- 210,
				Gdx.graphics.getHeight() - userInterface.getWeaponsInterface().getRegionHeight() - 980, 414, 192);
		
		//draws the currently equipped weapons - firearm and melee
		batch.draw(userInterface.getAk47(), Gdx.graphics.getWidth() - userInterface.getAk47().getRegionWidth()- 310, 
				Gdx.graphics.getHeight() - userInterface.getAk47().getRegionHeight() - 970,128,128);
		
		batch.draw(userInterface.getKatana(), Gdx.graphics.getWidth() - userInterface.getKatana().getRegionWidth()- 115, 
				Gdx.graphics.getHeight() - userInterface.getKatana().getRegionHeight() - 950, 128, 128);
	}

	/**
	 * Returns the cursor coordinates in the game world.
	 */
	public Vector2 getMouseWorldCoords() {
		return screenToWorldCoords(playerInputController.getMousePosition());
	}

	/** Converts screen coordinates to world ones. */
	public Vector2 screenToWorldCoords(Vector2 screenPosition) {
		Vector2 worldPosition = new Vector2();
		worldPosition.x = screenPosition.x + camera.position.x
				- Gdx.graphics.getWidth() / 2;
		worldPosition.y = screenPosition.y + camera.position.y
				- Gdx.graphics.getHeight() / 2;
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
