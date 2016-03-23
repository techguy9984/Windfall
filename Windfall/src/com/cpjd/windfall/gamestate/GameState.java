package com.cpjd.windfall.gamestate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class GameState {

	protected GameStateManager gsm;

	protected BufferedImage loading;
	
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void update();
	public abstract void draw(Graphics2D g);
	
	
}
