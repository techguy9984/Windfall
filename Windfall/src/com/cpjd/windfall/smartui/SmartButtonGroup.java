package com.cpjd.windfall.smartui;

import java.awt.Graphics2D;
import java.awt.Point;

import com.cpjd.tools.Layout;

public class SmartButtonGroup {
	
	// The button that controls the animation framework for all the buttosn
	SmartButton head;
	
	// All the buttons powered by the animations
	SmartButton[] buttons;
	
	/*
	 * Effects
	 */
	private boolean open;
	
	/*
	 * Technical vars
	 */
	
	// open vars
	private boolean opened;
	private Point[] homes;
	private Point[] endPos;
	private Point headEndPos;
	private Point headHome;
	
	public SmartButtonGroup(String headLoc, String clickLoc, SmartButton[] buttons, int panelWidth, int panelHeight) {
		this.buttons = buttons;
		
		head = new SmartButton(headLoc,clickLoc);
		head.setPos((int)Layout.centerw(head.getWidth(), panelWidth), (int)Layout.centerh(head.getHeight(), panelHeight));
		head.setBulge(true);
		
		// Calculate all the positions
		homes = new Point[buttons.length];
		endPos = new Point[buttons.length];
		
		
	}
	public void update() {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].update();
		}
		
		head.update();
	}	
	public void draw(Graphics2D g) {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].draw(g);
		}
		
		head.draw(g);
	}
}
