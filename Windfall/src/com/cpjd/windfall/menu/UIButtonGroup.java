package com.cpjd.windfall.menu;

import java.awt.Graphics2D;

import com.cpjd.tools.Layout;
import com.cpjd.windfall.main.GamePanel;

// Cleanly manages buttons for an overall animation
public class UIButtonGroup {

	// Head button for controlling animation
	UIButton master;
	
	UIButton[] buttons;
	
	// Animations vars
	private boolean home;
	private int home_x; // The start location for all buttons
	private int home_y;
	
	// Movement
	private boolean moving;
	private double currentSpeed;
	private double maxSpeed = 3;
	private double speed_inc = 0.5;
	
	private double xOffset;
	
	private int[] finalX;
	
	private int state; // 0 closed 1 open
	
	public UIButtonGroup(UIButton[] buttons) {
		this.buttons = buttons;
		
		state = 0;
		
		master = new UIButton("/UI/play.png","/UI/playClicked.png");
		master.setBulge(true);
		master.setPos((int)Layout.centerw(master.getWidth(), GamePanel.GAME_WIDTH), (int)Layout.centerh(master.getHeight(), GamePanel.GAME_HEIGHT));
		
		moving = false;
		
		finalX = new int[buttons.length];
		
		for(int i = 0, j = 20; i < buttons.length; i++, j+=15) {
			finalX[i] = (int)Layout.alignx(j, GamePanel.GAME_WIDTH) - buttons[i].getWidth() / 2;
		}
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setPos((int)Layout.centerw(buttons[i].getWidth(), GamePanel.GAME_WIDTH), (int)Layout.centerh(buttons[i].getHeight(), GamePanel.GAME_HEIGHT));
		}
	}
	
	public void update() {
		if(!moving && !master.isClicked()) {
			// Exit
			if(buttons[4].isClicked()) {
				System.exit(0);
			}
		}
		
		if(state == 0) {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].setLockPress(true);
			}
		} else {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].setLockPress(false);
			}
		}
		
		if(master.isClicked()) {
			moving = true;
		}

		// Moving method
		if(moving && state == 0) {
			currentSpeed += speed_inc;
			if(currentSpeed > maxSpeed) currentSpeed = maxSpeed;
			
			xOffset += currentSpeed;
			
			for(int i = 0; i < buttons.length; i++) {
				if(buttons[i].getX() - xOffset > finalX[i]) {
					buttons[i].setPos(buttons[i].getX() - (int)xOffset, buttons[i].getY());
				}
				if(buttons[i].getX() + xOffset < finalX[i]) {
					buttons[i].setPos(buttons[i].getX() + (int)xOffset, buttons[i].getY());
				}
			}
			if(master.getY() > Layout.aligny(15, GamePanel.GAME_HEIGHT)) master.setPos(master.getX(), master.getY() - (int)xOffset);
			
			// Check if the action is completed
			int finishedCount = 0;
			for(int i = 0; i < buttons.length; i++) {
				if(Math.abs(buttons[i].getX() - finalX[i]) <= 15) {
					finishedCount++;
				}
			}
			if(finishedCount == buttons.length) {
				moving = false;
				master.setClicked(false);
				state = 1;
				currentSpeed = 0;
				xOffset = 0;
				finishedCount = 0;
			}
		}
		
		if(moving && state == 1) {
			currentSpeed += speed_inc;
			if(currentSpeed > maxSpeed) currentSpeed = maxSpeed;
			
			xOffset += currentSpeed;
			
			for(int i = 0; i < buttons.length; i++) {
				if(buttons[i].getX() < (int)Layout.centerw(buttons[i].getWidth(), GamePanel.GAME_WIDTH)) {
					buttons[i].setPos(buttons[i].getX() + (int)xOffset, buttons[i].getY());
				}
				if(buttons[i].getX() > (int)Layout.centerw(buttons[i].getWidth(), GamePanel.GAME_WIDTH)) {
					buttons[i].setPos(buttons[i].getX() - (int)xOffset, buttons[i].getY());
				}
			}
			if(master.getY() <= (int)Layout.centerh(master.getHeight(), GamePanel.GAME_HEIGHT) - 20) master.setPos(master.getX(), master.getY() + (int)xOffset);
			
			// Check if the action is completed
			int finishedCount = 0;
			for(int i = 0; i < buttons.length; i++) {
				if(Math.abs(buttons[i].getX() - (GamePanel.GAME_WIDTH / 2)) <= 52) {
					finishedCount++;
				}
			}

			if(finishedCount == buttons.length) {
				moving = false;
				master.setClicked(false);
				state = 0;
				currentSpeed = 0;
				xOffset = 0;
				finishedCount = 0;
			}
		}
		
		
		if(moving) {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].setClicked(false);
				buttons[i].setLocked(false);
			}
		}
		
		
		master.update();
		
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i] != null) buttons[i].update();
		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i] != null) buttons[i].draw(g);
		}
		
		master.draw(g);
	}
	
}
