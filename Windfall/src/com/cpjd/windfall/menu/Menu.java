package com.cpjd.windfall.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.tools.Log;
import com.cpjd.windfall.gamestate.GameState;
import com.cpjd.windfall.gamestate.GameStateManager;
import com.cpjd.windfall.main.GamePanel;
import com.cpjd.windfall.smartui.SmartButton;
import com.cpjd.windfall.smartui.SmartButtonGroup;

public class Menu extends GameState {
	
	BufferedImage background;
	
	// Buttons
	SmartButton play, create, leaderboard, options, exit;
	
	SmartButton[] configButtons;
	
	SmartButton play2, load;
	
	SmartButtonGroup buttons;
	
	public Menu(GameStateManager gsm) {
		super(gsm);
		
		create = new SmartButton("/UI/create.png","/UI/createClicked.png");
		create.setBulge(true);
	
		load = new SmartButton("/UI/create.png","/UI/exit.png","/UI/load.png");
		load.setBulge(true);
		//load.setRotate(true);
		
		leaderboard = new SmartButton("/UI/leaderboard.png","/UI/leaderboardClicked.png");
		leaderboard.setBulge(true);
		
		options = new SmartButton("/UI/options.png","/UI/optionsClicked.png");
		options.setBulge(true);
		//options.setRotate(true);
		
		exit = new SmartButton("/UI/exit.png","/UI/exitClicked.png");
		exit.setBulge(true);
		
		configButtons = new SmartButton[5];
		configButtons[0] = create;
		configButtons[1] = load;
		configButtons[2] = leaderboard;
		configButtons[3] = options;
		configButtons[4] = exit;
		
		buttons = new SmartButtonGroup("/UI/play.png","/UI/playClicked.png",configButtons,GamePanel.GAME_WIDTH,GamePanel.GAME_HEIGHT);
		
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/landscape.png"));
			
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
	}
	
	public void update() {
		buttons.update();
	}

	public void draw(Graphics2D g) {

		g.drawImage(background, 0, 0, null);
		
		buttons.draw(g);
		

	}

}
