package com.game.virtualevil;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.virtualevil.entity.EntityManager;
import com.game.virtualevil.utility.FontManager;
import com.game.virtualevil.utility.TextureManager;

public class Game extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureManager textureManager;
	private FontManager fontManager;
	private EntityManager entityManager;
    
    //private Sprite mapSprite;
	//private byte[] map;
	
	private final boolean testing = true;
	float delta;

	@Override
	public void create() {		
		batch = new SpriteBatch();
		textureManager = new TextureManager();
		fontManager = new FontManager();
		entityManager = new EntityManager(this);
		
		/*mapSprite = new Sprite(textureManager.getImage("sc_map"));
		mapSprite.setPosition(0, 0);
		mapSprite.setSize(Gdx.graphics.getWidth()*2, Gdx.graphics.getHeight()*2);*/
		
		/*FileHandle file = Gdx.files.internal("myblob.bin");
		map = file.readBytes();*/
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
		
	}

	@Override
	public void render() {
		delta = Gdx.graphics.getDeltaTime();
		
		entityManager.updateEntities(delta);
	
		//camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		//mapSprite.draw(batch);
		entityManager.drawEntities(batch);
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
}
