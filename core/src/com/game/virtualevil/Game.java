package com.game.virtualevil;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.entity.EntityManager;
import com.game.virtualevil.utility.FontManager;
import com.game.virtualevil.utility.Map;
import com.game.virtualevil.utility.TextureManager;

public class Game extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureManager textureManager;
	private FontManager fontManager;
	private EntityManager entityManager;
    private Map map;
	
	private final boolean testing = true;
	float delta;

	@Override
	public void create() {		
		batch = new SpriteBatch();
		textureManager = new TextureManager();
		fontManager = new FontManager();
		entityManager = new EntityManager(this);
		
		map = new Map(this);
		
		//scaled 2x
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
		
		//normal scale
		/*camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);*/ 
        camera.update();
		
	}

	@Override
	public void render() {
		delta = Gdx.graphics.getDeltaTime();
		
		entityManager.updateEntities(delta);
	
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		map.drawMap(batch);
		entityManager.drawEntities(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		textureManager.disposeAllTextures();
		fontManager.disposeFonts();
		map.getTileSet().getTexture().dispose();
	}
	
	public boolean isTesting() {
		return testing;
	}

	public FontManager getFontManager() {
		return fontManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public Map getMap() {
		return map;
	}
}
