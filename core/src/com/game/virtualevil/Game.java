package com.game.virtualevil;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
		
		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width,
				Gdx.graphics.getDesktopDisplayMode().height, true);
		Gdx.graphics.setVSync(true);
		
		batch = new SpriteBatch();
		textureManager = new TextureManager();
		fontManager = new FontManager();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/2,
				Gdx.graphics.getHeight()/2);
		
		map = new Map(this, "map1");
		entityManager = new EntityManager(this);
		camera.update();
	}

	@Override
	public void render() {
		delta = Gdx.graphics.getDeltaTime();
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		entityManager.updateEntities(delta);
	
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		map.drawMap(batch, camera.position);
		entityManager.drawEntities(batch);
		map.drawLayer2Map(batch, camera.position);
		entityManager.getPlayer().drawUI(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		textureManager.disposeAllTextures();
		fontManager.disposeFonts();
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
