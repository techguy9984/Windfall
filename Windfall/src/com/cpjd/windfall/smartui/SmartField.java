package com.cpjd.windfall.smartui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class SmartField {
	
	// Bounds
	private int x;
	private int y;
	private int width;
	private int height;
	
	// Text contained
	private String text;
	private String textDraw;
	private int pos; // Cursor position (char)
	
	public SmartField() {
		
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {
		// Draw border
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(10));
		g.drawRect(x, y, width, height);
		
		// Draw background
		g.setColor(Color.WHITE);
		g.drawRect(x + 1, y + 1, width - 1, height - 1);
			
		// Draw text
		g.setColor(Color.BLACK);
		g.drawString(text, x, y);
	}

}
