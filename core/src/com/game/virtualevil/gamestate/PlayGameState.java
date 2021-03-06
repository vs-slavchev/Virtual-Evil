package com.game.virtualevil.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.virtualevil.Game;
import com.game.virtualevil.entity.EntityManager;
import com.game.virtualevil.entity.PlayerCharacter;
import com.game.virtualevil.utility.*;
import com.game.virtualevil.utility.asset.MusicManager;

public final class PlayGameState extends GameState {

    private EntityManager entityManager;
    private Map map;
    private OrthographicCamera uiCamera;
    private InputController playerInputController;
    private GameInputProcessor gameInputProcessor;
    private UiInputProcessor uiInputProcessor;
    private UserInterface userInterface;
    private MusicManager musicManager;

    public PlayGameState(GameStateManager gsm, Game game) {
        super(gsm, game);
    }

    @Override
    public void initialize() {
        map = new Map(this, "map1");
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera.update();

        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ScreenViewport viewport = new ScreenViewport(uiCamera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.apply();
        uiCamera.position.set(uiCamera.viewportWidth / 2, uiCamera.viewportHeight / 2, 0);
        uiCamera.update();

        userInterface = new UserInterface(assetManager.getTextureManager());

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
        musicManager = new MusicManager();
        musicManager.initialize();
    }

    @Override
    public void update(final float delta) {
        entityManager.updateEntities(delta, this);
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

    public boolean isObjectInView(Vector2 position) {
        Rectangle cameraView = map.calculateRenderRectIndices(camera.position);
        Vector2 positionInMapIndices = map.positionToMapIndices(position);
        return positionInMapIndices.x >= cameraView.x
                && positionInMapIndices.x <= cameraView.width
                && positionInMapIndices.y >= cameraView.height
                && positionInMapIndices.y <= cameraView.y;
    }

    private void drawUI(PlayerCharacter player) {
        if (player.getCurrentHealth() <= 0) {
            return;
        }

        userInterface.draw(batch, this);
        map.drawMiniMap(batch, camera.position, 65, 65);
    }

    /**
     * Returns the cursor coordinates in the game world.
     */
    public Vector2 getMouseWorldCoords() {
        return screenToWorldCoords(playerInputController.getMousePosition());
    }

    /**
     * Converts screen coordinates to world ones.
     */
    public Vector2 screenToWorldCoords(Vector2 screenPosition) {
        Vector2 worldPosition = new Vector2();
        worldPosition.x = camera.position.x - Gdx.graphics.getWidth() / 2 + screenPosition.x;
        worldPosition.y = camera.position.y - Gdx.graphics.getHeight() / 2
                + (Gdx.graphics.getHeight() - screenPosition.y);
        return worldPosition;
    }

    @Override
    public void dispose() {
        super.dispose();
        musicManager.dispose();
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
