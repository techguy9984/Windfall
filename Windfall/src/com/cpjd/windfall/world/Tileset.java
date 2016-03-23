package com.cpjd.windfall.world;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.tools.Log;

public class Tileset {
	
	public int width;
	public int height;
	
	ArrayList<Tile> tiles;
	
	public Tileset(String location, int tileSize) {
		Log.log("Loading tileset at: "+location, 4);
		
		// Load the tiles
		BufferedImage tileset = null;
		
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(location));
			
			width = tileset.getWidth();
			height = tileset.getHeight();
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
		// Detect how many tiles there are
		int numTiles = 0;
		
		for(int i = 0; i < width / tileSize; i++) {
			for(int j = 0; j < height / tileSize; j++) {
				numTiles++;
			}
		}
		
		tiles = new ArrayList<Tile>();
		
		Log.log("Loaded "+numTiles+" tile images", 4);
		
		// Load the tiles in
		for(int i = 0; i < width / tileSize; i++) {
			for(int j = 0; j < height / tileSize; j++) {
				BufferedImage subTile = tileset.getSubimage(i * tileSize, j * tileSize, tileSize, tileSize);
				int genID = tiles.size() + 1;
				tiles.add(new Tile(subTile,genID));
			}
		}	

	}
	
	public BufferedImage getImage(int id) {
		if(tiles.get(id).getImage() == null) {
			return null;
		}
		return tiles.get(id).getImage();
	}
	
}
