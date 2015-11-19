package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.game.virtualevil.Game;

public class Map {

	// map
	private String tilesetName;
	private int width, height;
	private byte[][] map;

	// drawing on the screen
	private int tileSize = 32;
	private final int numTilesPerRow = 10;
	private Sprite tileSet;
	private TextureRegion tileTexture;
	// rendering distance from the camera center; measured in map indices
	private int renderDistanceInIndices = 12;

	/**
	 * The mapName shouldn't contain '.bin' */
	public Map(Game game, String mapName) {
		readMap(mapName);
		tilesetName = "cyber_tileset";
		tileSet = new Sprite(game.getTextureManager().getImage(tilesetName));
	}

	/**
	 * Checks whether a rectangle collides with any tiles near it.
	 * The tile indices in which the rectangle is located are calculated
	 * and the rectangle is checked for collision against the nearest tiles.
	 * @param characterCollisionRectangle the rectangle of the entity to be checked
	 * @return true - collision detected, false - no collision */
	public boolean collidesWithTerrain(Rectangle characterCollisionRectangle) {
		/* map[0][0] is top left, but player 0,0 is bottom left, so we flip the
		 * player y to match the map layout */
		characterCollisionRectangle.y = height * tileSize - characterCollisionRectangle.y;
		int mapXindex = (int) characterCollisionRectangle.x / tileSize;
		int mapYindex = (int) characterCollisionRectangle.y / tileSize;
		
		if (characterCollisionRectangle.x <= 0 || mapXindex >= map[0].length -1
				|| mapYindex <= 0 || mapYindex >= map.length) {
			return true;
		}
		
		/* check collision with:
		 * - the tile the player is on;
		 * - the upper tile;
		 * - the right tile;
		 * - the diagonal top right tile. */
		return collidesWithTile(characterCollisionRectangle, mapXindex, mapYindex)
				|| collidesWithTile(characterCollisionRectangle, mapXindex +1, mapYindex)
				|| collidesWithTile(characterCollisionRectangle, mapXindex, mapYindex -1)
				|| collidesWithTile(characterCollisionRectangle, mapXindex +1, mapYindex -1);
	}

	/**
	 * Check whether a rectangle collides with the specified tile.
	 * The collision rectangle of the tile depends on its ID, that
	 * is its position in tile set.
	 * @param characterCollisionRectangle the rectangle to be checked
	 * @param mapXindex the x index of the map 2D array
	 * @param mapYindex the y index of the map 2D array
	 * @return true - collision detected, false - no collision */
	private boolean collidesWithTile(Rectangle characterCollisionRectangle, int mapXindex, int mapYindex) {
		/* 0-100 -> 0%
		 * 100-200 -> 50%
		 * 200-255 -> 100% */
		if (getTileID(mapXindex, mapYindex) < 60) {
			return false;
		} else if (getTileID(mapXindex, mapYindex) < 100) {
			Rectangle tileColRect = new Rectangle(mapXindex * tileSize + tileSize/4,
					(mapYindex +1) * tileSize + tileSize/8,
					tileSize/2, tileSize/2);
			if (characterCollisionRectangle.overlaps(tileColRect)) {
				return true;
			}
		} else if (getTileID(mapXindex, mapYindex) < 255) {
			Rectangle tileColRect = new Rectangle(mapXindex * tileSize,
					(mapYindex +1) * tileSize,
					tileSize, tileSize);
			if (characterCollisionRectangle.overlaps(tileColRect)) {
				return true;
			}
		}

		return false;
	}

	public void readMap(String mapName) {
		FileHandle file2 = Gdx.files.internal(mapName + ".bin");

		byte[] bytes;
		int multFactor;
		byte[][] map2 = null;

		try {
			bytes = file2.readBytes();
			width = bytes[0];
			height = bytes[1];
			multFactor = bytes[2];
			width *= multFactor;
			height *= multFactor;

			// create the map with the now known width and height 
			map2 = new byte[height][width];
			/* fill the 2D map with information from the read 1D
			 * array of bytes */
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					map2[i][j] = bytes[3 + i * width + j];
				}
			}
		} catch (GdxRuntimeException ex) {
			System.out.println("Error reading from map file!");
			return;
		}
		map = map2;
	}

	public void drawMap(SpriteBatch batch, Vector3 cameraPosition) {
		// calculate on which indices of the map the camera is
		int cameraXindex = (int) (cameraPosition.x / tileSize);
		int cameraYindex = (int) ((height*tileSize - cameraPosition.y) / tileSize);
		
		/* determine which are the min and max values for the vertical/horizontal 
		 * drawing of the map to prevent invalid indexing */
		int leftDrawBoundaryIndex = (cameraXindex - renderDistanceInIndices < 0)
				? 0 : cameraXindex - renderDistanceInIndices;
		int rightDrawBoundaryIndex = (cameraXindex + renderDistanceInIndices >= width)
				? width : cameraXindex + renderDistanceInIndices;
		int upDrawBoundaryIndex = (cameraYindex - renderDistanceInIndices < 0)
				? 0 : cameraYindex - renderDistanceInIndices;
		int downDrawBoundaryIndex = (cameraYindex + renderDistanceInIndices >= height)
				? height : cameraYindex + renderDistanceInIndices;
		
		// go trough the on-screen map and render the corresponding tile
		for (int u = upDrawBoundaryIndex; u < downDrawBoundaryIndex; u++) {
			for (int v = leftDrawBoundaryIndex; v < rightDrawBoundaryIndex; v++) {
				int tileID = getTileID(v, u);
				tileTexture = new TextureRegion(tileSet, (tileID % numTilesPerRow) * tileSize,
						(tileID / numTilesPerRow) * tileSize, tileSize, tileSize);
				batch.draw(tileTexture, v * tileSize, height * tileSize - ((u + 1) * tileSize),
						tileSize, tileSize);
			}
		}
	}

	@SuppressWarnings("unused")
	private void printMap() {
		System.out.println(width + " " + height);
		for (int k = 0; k < height; k++) {
			for (int l = 0; l < width; l++) {
				int tileID = map[k][l];
				System.out.print((tileID + 127) + " ");
			}
			System.out.println();
		}
	}

	/**
	 * @param x the x index of the tile
	 * @param y the y index of the tile
	 * @return the ID of the tile
	 */
	public int getTileID(int x, int y) {
		if (x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
			System.out.println("invalid map indexing: x: "
					+ x + "; y: " + y);
		}
		return map[y][x] + 127;
	}
	
	/**
	 * @param xIndex the x index of the tile
	 * @param yIndex the y index of the tile
	 * @param tileID the ID to set the tile ID to*/
	public void setTileID(int xIndex, int yIndex, int tileID) {
		if (xIndex < 0 || xIndex >= map[0].length
				|| yIndex < 0 || yIndex >= map.length) {
			return;
		}
		map[yIndex][xIndex] = toSignedByte(tileID);
	}
	
	/**
	 * Takes an int between 0 and 255 and returns a byte
	 * with a value ranging from -128 to 127.
	 * @param value the unsigned int value to convert
	 * @return the converted signed byte
	 */
	@SuppressWarnings("static-method")
	private byte toSignedByte(int value) {
		if (value < 0 || value > 255) {
			System.out.println("Cannot convert " + value + " to signed byte!");
		}
		return (byte) (value - 127);
	}

	public Sprite getTileSet() {
		return tileSet;
	}

	public int getHeight() {
		return height;
	}
	
	public int getTotalHeight() {
		return height * tileSize;
	}

}
