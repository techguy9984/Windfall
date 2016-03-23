package com.cpjd.windfall.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.tools.Layout;
import com.cpjd.tools.Log;
import com.cpjd.windfall.gamestate.GameState;
import com.cpjd.windfall.gamestate.GameStateManager;
import com.cpjd.windfall.main.GamePanel;

public class Menu extends GameState {
	
	BufferedImage background;
	
	// Buttons
	UIButton play, create, load, leaderboard, options, exit;
	
	UIButtonGroup buttons;
	
	UIButton[] configButtons;
	
	public Menu(GameStateManager gsm) {
		super(gsm);
		
		create = new UIButton("/UI/create.png","/UI/createClicked.png");
		create.setBulge(true);
	
		load = new UIButton("/UI/load.png","/UI/loadFrames.png","/UI/loadClicked.png", 0);
		load.setBulge(true);
		
		leaderboard = new UIButton("/UI/leaderboard.png","/UI/leaderboardClicked.png");
		leaderboard.setBulge(true);
		
		options = new UIButton("/UI/options.png","/UI/optionsClicked.png");
		options.setBulge(true);
		options.setRotate(true);
		
		exit = new UIButton("/UI/exit.png","/UI/exitClicked.png");
		exit.setBulge(true);
		
		configButtons = new UIButton[5];
		configButtons[0] = create;
		configButtons[1] = load;
		configButtons[2] = leaderboard;
		configButtons[3] = options;
		configButtons[4] = exit;
		
		buttons = new UIButtonGroup(configButtons);
		
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/landscape.png"));
			
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		

		
		
	}
	
	public void update() {
		buttons.update();
		
		/*play.update();
		create.update();
		load.update();
		leaderboard.update();
		options.update();
		exit.update();*/
		

	}

	public void draw(Graphics2D g) {

		g.drawImage(background, 0, 0, null);
		
		buttons.draw(g);
		
		/*play.draw(g);
		create.draw(g);
		load.draw(g);
		leaderboard.draw(g);
		options.draw(g);
		exit.draw(g);*/
		

	}

}
