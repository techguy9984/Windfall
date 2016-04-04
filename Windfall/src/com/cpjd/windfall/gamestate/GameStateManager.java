package com.cpjd.windfall.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.cpjd.windfall.menu.Menu;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;

	// Game States Codes
	public static final int NUMGAMESTATES = 3;
	// Menu stuff
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int CREDITS = 2;

	// Loaidng
	BufferedImage background;

	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];

		currentState = MENU;
		loadState(currentState);
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	private void loadState(int state) {
		if (state == MENU)
			gameStates[state] = new Menu(this);

	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public int getState() {
		return currentState;
	}

	public void update() {
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	public void draw(Graphics2D g) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}
	}
	public void keyPressed(int k) {
		if (gameStates[currentState] != null)
			gameStates[currentState].keyPressed(k);
	}
	public void keyReleased(int k) {

		if (gameStates[currentState] != null)
			gameStates[currentState].keyReleased(k);
	}

}
