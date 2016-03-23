package com.cpjd.windfall.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cpjd.tools.Log;
import com.cpjd.windfall.main.GamePanel;

// Manages world rendering
public class World {
	
	// Map controls
	private int xOffset;
	private int yOffset;
	private int zoom;
	
	int[][] layer1;
	int[][] layer2;
	int[][] layer3;
	int[][] layer4;
	int[][] layer5;
	
	Tileset tileset;
	
	// Drawing
	BufferedImage worldImg;
	Graphics g2;
	
	public World(int[][] layer1, Tileset tileset) {
		this.layer1 = layer1;
		this.tileset = tileset;
		
		init();
	}
	
	public World(int[][] layer1,int[][] layer2, Tileset tileset) {
		this.layer1 = layer1;
		this.layer2 = layer2;
		this.tileset = tileset;

		init();
	}
	
	public World(int[][] layer1,int[][] layer2,int[][] layer3, Tileset tileset) {
		this.layer1 = layer1;
		this.layer2 = layer2;
		this.layer3 = layer3;
		this.tileset = tileset;

		init();
	}
	
	public World(int[][] layer1,int[][] layer2,int[][] layer3,int[][] layer4, Tileset tileset) {
		this.layer1 = layer1;
		this.layer2 = layer2;
		this.layer3 = layer3;
		this.layer4 = layer4;
		this.tileset = tileset;

		init();
	}
	
	public World(int[][] layer1,int[][] layer2,int[][] layer3,int[][] layer4,int[][] layer5, Tileset tileset) {
		this.layer1 = layer1;
		this.layer2 = layer2;
		this.layer3 = layer3;
		this.layer4 = layer4;
		this.layer5 = layer5;
		this.tileset = tileset;
		
		init();
	}
	
	int layerCount;
	
	public void init() {
		layerCount = 0;
		if(layer1 != null) layerCount++;
		if(layer2 != null) layerCount++;
		if(layer3 != null) layerCount++;
		if(layer4 != null) layerCount++;
		if(layer5 != null) layerCount++;
		
		Log.log("Initializing world with "+layerCount+" layer(s)", 4);
		
		worldImg = new BufferedImage(GamePanel.GAME_WIDTH,GamePanel.GAME_HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g = worldImg.getGraphics();
		g2 = (Graphics2D) g;
	}
	
	public void update() {
		if(GamePanel.q)zoom-=20;
		if(GamePanel.e) zoom+= 20;
		if(GamePanel.w)yOffset-=10;
		if(GamePanel.a) xOffset-= 10;
		if(GamePanel.s)yOffset+=10;
		if(GamePanel.d) xOffset+= 10;
		
	}
	
	public void draw(Graphics2D g) {
		// Clear screen
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		
		for(int x = 0; x < layer1[0].length; x++) {
			for(int y = 0; y < layer1.length; y++) {
				if(layer1[y][x] == 0) continue;
				
				int tempY = 16 * (3 + x - y);
				g2.drawImage(tileset.getImage(layer1[y][x]), ((y + x) * 32) + xOffset, tempY + yOffset,null);
			}
		}
		
		if(layerCount == 1) { 
			g.drawImage(worldImg,0,0,worldImg.getWidth() +zoom, worldImg.getHeight() + zoom, null);
			return;
		
		}
		
		
		for(int x = 0; x < layer2[0].length; x++) {
			for(int y = 0; y < layer2.length; y++) {
				if(layer2[y][x] == 0) continue;
				
				
				int tempY = 16 * (3 + x - y);
				g2.drawImage(tileset.getImage(layer1[y][x]), ((y + x) * 32) + xOffset, tempY + yOffset,null);
			}
		}
		
		if(layerCount == 2) { 
			g.drawImage(worldImg,0,0,worldImg.getWidth() +zoom, worldImg.getHeight() + zoom, null);
			return;
		
		}

		for(int x = 0; x < layer3[0].length; x++) {
			for(int y = 0; y < layer3.length; y++) {
				if(layer3[y][x] == 0) continue;
				
				int tempY = 16 * (3 + x - y);
				g2.drawImage(tileset.getImage(layer1[y][x]), ((y + x) * 32) + xOffset, tempY + yOffset,null);
			}
		}

		if(layerCount == 3) { 
			g.drawImage(worldImg,0,0,worldImg.getWidth() +zoom, worldImg.getHeight() + zoom, null);
			return;
		
		}

		for(int x = 0; x < layer4[0].length; x++) {
			for(int y = 0; y < layer4.length; y++) {
				if(layer4[y][x] == 0) continue;
				
				
				int tempY = 16 * (3 + x - y);
				g2.drawImage(tileset.getImage(layer1[y][x]), ((y + x) * 32) + xOffset, tempY + yOffset,null);
			}
		}
		if(layerCount == 4) { 
			g.drawImage(worldImg,0,0,worldImg.getWidth() +zoom, worldImg.getHeight() + zoom, null);
			return;
		
		}
		for(int x = 0; x < layer5[0].length; x++) {
			for(int y = 0; y < layer5.length; y++) {
				if(layer5[y][x] == 0) continue;
				
				int tempY = 16 * (3 + x - y);
				g2.drawImage(tileset.getImage(layer1[y][x]), ((y + x) * 32) + xOffset, tempY + yOffset,null);
			}
		}
		
		if(layerCount == 5) { 
			g.drawImage(worldImg,0,0,worldImg.getWidth() +zoom, worldImg.getHeight() + zoom, null);
			return;
		
		}
	}
	
}
