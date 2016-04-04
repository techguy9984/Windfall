package com.cpjd.windfall.smartui;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SmartCheck {
	
	BufferedImage checked;
	BufferedImage unchecked;
	
	/*
	 * Effects
	 */
	private boolean bulge;
	private boolean slideup;
	
	/*
	 * Technical vars
	 */
	
	// bulge vars
	private int currentBulge;
	private int maxBulge;
	
	public SmartCheck(String uncheckLoc, String checkLoc) {
		
		try {
			unchecked = ImageIO.read(getClass().getResourceAsStream(uncheckLoc));
			checked = ImageIO.read(getClass().getResourceAsStream(checkLoc));
		} catch(Exception e) {
			
		}
	}
}
