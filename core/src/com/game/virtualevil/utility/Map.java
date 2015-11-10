package com.game.virtualevil.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;

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

	public Map() {
		readMap("map1");

		tilesetName = "forest_tiles.png";
		tileSet = new Sprite(new Texture(tilesetName));
	}

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

	/* the tile collision box depends on the tileID*/
	private boolean collidesWithTile(Rectangle characterCollisionRectangle, int mapXindex, int mapYindex) {
		/* 0-100 -> 0%
		 * 100-200 -> 50%
		 * 200-255 -> 100% */
		if (getTileID(mapXindex, mapYindex) < 40) {
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
		int mapWidth, mapHeight, multFactor;
		byte[][] map2 = null;

		try {
			bytes = file2.readBytes();
			width = bytes[0];
			height = bytes[1];
			multFactor = bytes[2];
			width *= multFactor;
			height *= multFactor;

			map2 = new byte[height][width];
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
		// printMap(mapWidth, mapHeight, map2);
	}

	public void drawMap(SpriteBatch batch) {
		// go trough the on-screen map and render the corresponding tile
		for (int u = 0; u < height; u++) {
			for (int v = 0; v < width; v++) {
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

	/* takes: x and y and returns the tileID as an int */
	public int getTileID(int x, int y) {
		if (x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
			System.out.println("invalid map indexing: x: "
					+ x + "; y: " + y);
		}
		return map[y][x] + 127;
	}
	
	/* modifies the map */
	public void setTileID(int xIndex, int yIndex, int tileID) {
		if (xIndex < 0 || xIndex >= map[0].length
				|| yIndex < 0 || yIndex >= map.length) {
			map[yIndex][xIndex] = toSignedByte(tileID);
		}
	}
	
	/* takes: 0-255; returns -128 to 127 */
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

}
