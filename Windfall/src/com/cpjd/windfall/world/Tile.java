package com.cpjd.windfall.world;

import java.awt.image.BufferedImage;

public class Tile {
	
	// A tile's visual representation
	BufferedImage image;
	
	// It's ID
	private int id;
	
	// It's x & y coordinates
	private int x;
	private int y;
	
	// It's type
	private int type;
	
	public static final int NORMAL = 1;
	public static final int BLOCKED = 2;
	public static final int ANIMATED = 3;
	public static final int FATAL = 4;
	
	public Tile(BufferedImage image, int id) {
		this.id = id;
		
		this.image = image;
		
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public int getID() {
		return id;
	}
	public void setType(int t) {
		type = t;
	}
	public int getType() {
		return type;
	}
	
	
	
}
