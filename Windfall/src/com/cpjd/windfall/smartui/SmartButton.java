package com.cpjd.windfall.smartui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.cpjd.tools.Log;

public class SmartButton {
	
	private boolean clicked;
	
	private BufferedImage img;
	private BufferedImage hoverImg;
	private BufferedImage clickImg;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	// Animation effects
	private boolean rotate;
	private boolean bulge;
	private boolean slide;
	private boolean customAnim;
	
	public SmartButton(String imgLoc, String clickLoc) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(imgLoc));
			clickImg = ImageIO.read(getClass().getResourceAsStream(clickLoc));
			
			width = img.getWidth();
			height = img.getHeight();
		} catch(IOException e) {
			Log.logError(e, Log.RES_LOAD_ERROR); 
		}
	}
	
	public SmartButton(String imgLoc, String hoverLoc, String clickLoc) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(imgLoc));
			hoverImg = ImageIO.read(getClass().getResourceAsStream(hoverLoc));
			clickImg = ImageIO.read(getClass().getResourceAsStream(clickLoc));
			
			width = img.getWidth();
			height = img.getHeight();
		} catch(IOException e) {
			Log.logError(e, Log.RES_LOAD_ERROR); 
		}
	}
	
	public SmartButton(String framesLoc, String clickLoc, int delay) {

		try {
			BufferedImage frames = ImageIO.read(getClass().getResourceAsStream(framesLoc));
			
		} catch(IOException e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {
		
	}
	
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
	
	public void setBulge(boolean bulge) {
		this.bulge = bulge;
	}
	
	public boolean isClicked() {
		return clicked;
	}
}
