package com.cpjd.windfall.smartui;

import java.awt.Graphics2D;
import java.awt.Point;

import com.cpjd.tools.Layout;
import com.cpjd.tools.Log;

/*
 * SmartButton will manage a animation for an array of buttons as well as updating, drawing, and click mananagement
 * 
 * TO USE:
 * Call new SmartButtonGroup() with the following params
 * 		-headLoc - a .png image location for the master button image
 * 		-headClickLoc - a .png image location for the master button image once clicked
 * 		-buttons[] - all the buttons that need to be controlled
 * 		-pwidth - the width of the panel
 * 		-pheight - the height of the panel
 * Call update()
 * Call draw(g)
 * Call isClicked(int index) to check if a button is clicked in the array (starts at index 0 - through the amount of buttons)
 * Call setClicked(boolean clicked, int index) to set the status of a button
 * All buttons passed in must be the same size
 * 
 */
public class SmartButtonGroup {
	
	// The master button that controls the animation
	SmartButton head;
	
	// All the buttons controlled by the SmartButtonGroup
	SmartButton[] buttons;
	
	// Technical vars
	private Point headHome;
	private Point headDest;
	private Point buttonsHome; // The location where the buttons start (when closed)
	private Point[] buttonsDest; // The location where the buttons will end up (when opend)
	private boolean[] buttonsAtDest; // Whether the buttons have arrived successfully at there location
	private int[] distToDest; // The distance between the button home and the destination
	private boolean open;
	private boolean moving;
	
	// Movement vars
	private double currentSpeed;
	private double currentFactor;
	private int maxSpeed = 1000;
	
	public SmartButtonGroup(String headLoc, String headClickLoc, SmartButton[] buttons, int pwidth, int pheight) {
		if(buttons.length < 1) {
			Log.log("Null buttons array. Nothing to control.", 2);
			return;
		}
		// Check for null buttons
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i] == null) {
				Log.log("A null buttons was found in the buttons array.", 2);
				return;
			}
		}
		
		// Init vars
		this.buttons = buttons;
		buttonsAtDest = new boolean[buttons.length];
		
		// Create the head button
		head = new SmartButton(headLoc,headClickLoc);
		head.setBulge(true);
		
		// Calculate the buttonsHome location
		buttonsHome = new Point((int)Layout.centerw(buttons[0].getWidth(), pwidth), (int)Layout.centerh(buttons[0].getHeight(), pheight));
		
		// Calculate the margin between buttons (based off screen size)
		int margin = (int)(Layout.alignx(30, pwidth) - Layout.alignx(15, pwidth)) - buttons[0].getWidth();
		
		// Calculate the total width of all the buttons, margin included
		int totalButtonWidth = 0;
		for(int i = 0; i < buttons.length; i++) {
			totalButtonWidth += buttons[i].getWidth(); // Add the width of the buttons
			totalButtonWidth += margin; // Add the margin between buttons
			if(i == buttons.length - 1) {
				totalButtonWidth -= margin;
			}
		}
		
		// Calculate the x-position that is required to center the entire button array
		int center = (int)Layout.centerw(totalButtonWidth, pwidth);
		
		// Calculate the destination locations for all the buttons
		buttonsDest = new Point[buttons.length];
		for(int i = 0; i < buttons.length; i++) {
			buttonsDest[i] = new Point(center + (int)(Layout.alignx(15 * i, pwidth)), (int)Layout.centerh(buttons[i].getHeight(), pheight));
		}
		
		// Set the buttons' locations to the center
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setPos(buttonsHome.x, buttonsHome.y);
		}
		
		// Calculate the distance between home and destination & calculate factors
		distToDest = new int[buttons.length];
		for(int i = 0; i < buttons.length; i++) {
			distToDest[i] = buttons[i].getX() - buttonsDest[i].x;
			for(int j = 1; j < Math.abs(distToDest[i]) + 1; j++) {
				if(distToDest[i] % j == 0 || distToDest[i] % j == -0 || distToDest[i] % j == 1) {
					if(j > maxSpeed) {
						continue;
					}
					buttons[i].addFactor(j);
				}
			}
		}
		// Calculate head position
		headHome = new Point((int)Layout.centerw(head.getWidth(), pwidth), (int)Layout.centerh(head.getHeight(), pheight));
		headDest = new Point((int)Layout.centerw(head.getWidth(), pwidth),(int)Layout.aligny(10, pheight));
		head.setPos(headHome);
		
		// Disable clicking for all buttons
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].disable(true);
		}
		
		// Set all buttons to not being clicked
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setClicked(false);
		}
		
	}
	
	public void update() {
		if(buttons.length < 1) {
			return;
		}
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].update();
		}
		head.update();
				
		if(head.isClicked()) {
			moving = true;
			head.setClicked(false);
		}
		
		if(!open && moving) {
			if(head.getY() > headDest.y) {
				head.setPos(head.getX(),head.getY() - (int)currentSpeed);
			}
			currentFactor += 0.1;
			for(int i = 0; i < buttons.length; i++) {
				if(buttons[i].getArraySize() == 0) continue;
				if(currentFactor > buttons[i].getArraySize()) currentFactor = buttons[i].getArraySize() - 1;
				currentSpeed = buttons[i].getFactor((int)currentFactor);
				if(buttons[i].getX() < buttonsDest[i].x) {
					buttons[i].setPos(buttons[i].getX() + (int)currentSpeed, buttons[i].getY());
				}
				if(buttons[i].getX() > buttonsDest[i].x) {
					buttons[i].setPos(buttons[i].getX() - (int)currentSpeed, buttons[i].getY());
				}
			}
			
			// Check if all the buttons are at the final destination
			for(int i = 0; i < buttons.length; i++) {
				if(Math.abs(buttons[i].getX() - buttonsDest[i].x) < 9) {
					buttonsAtDest[i] = true;
				}
			}
			
			// If they are, stop the animation
			if(allTrue(buttonsAtDest)) {
				reset();
				moving = false;
				open = true;
				currentFactor = 0;
				for(int i = 0; i < buttons.length; i++) {
					buttons[i].disable(false);
				}
			}
		}
		if(open && moving) {
			if(head.getY() < headHome.y) {
				head.setPos(head.getX(),head.getY() + (int)currentSpeed);
			}
			
			// Move buttons
			currentFactor += 0.1;
			for(int i = 0; i < buttons.length; i++) {
				if(buttons[i].getArraySize() == 0) continue;
				if(currentFactor > buttons[i].getArraySize()) currentFactor = buttons[i].getArraySize() - 1;
				currentSpeed = buttons[i].getFactor((int)currentFactor);
				if(buttons[i].getX() < buttonsHome.x) {
					buttons[i].setPos(buttons[i].getX() + (int)currentSpeed, buttons[i].getY());
				}
				if(buttons[i].getX() > buttonsHome.x) {
					buttons[i].setPos(buttons[i].getX() - (int)currentSpeed, buttons[i].getY());
				}
			}
			
			// Check if all the buttons are at the final destination
			for(int i = 0; i < buttons.length; i++) {
				if(Math.abs(buttons[i].getX() - buttonsHome.x) < 9) {
					buttonsAtDest[i] = true;
				}
			}
			
			// If they are, stop the animation
			if(allTrue(buttonsAtDest)) {
				reset();
				moving = false;
				open = false;
				currentFactor = 0;
				for(int i = 0; i < buttons.length; i++) {
					buttons[i].disable(true);
				}
			}
		}
		
	}
	public void draw(Graphics2D g) {
		if(buttons.length < 1) {
			return;
		}
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].draw(g);
		}
		head.draw(g);
	}
	public boolean isClicked(int index) {
		if(buttons[index].isClicked()) return true;
		else return false;
	}
	public void setClicked(boolean clicked, int index) {
		buttons[index].setClicked(clicked);
	}
	private void reset() {
		for(int i = 0; i < buttonsAtDest.length; i++) {
			buttonsAtDest[i] = false;
		}
	}
	private boolean allTrue(boolean[] values) {
		for(boolean value : values) {
			if(!value) return false;
		}
		return true;
	}	
}
