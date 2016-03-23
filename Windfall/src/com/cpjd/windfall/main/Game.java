package com.cpjd.windfall.main;

import javax.swing.JFrame;

import com.cpjd.tools.Log;

public class Game extends JFrame{

	private static final long serialVersionUID = -1139087955463380188L;

	public Game(int width, int height, boolean fullscreen, boolean quality, String title, String version, int versionCode) {
		super(title);
		
		// Set attributes
		GamePanel.GAME_WIDTH = width;
		GamePanel.GAME_HEIGHT = height;
		GamePanel.GAME_TITLE = title;
		GamePanel.GAME_QUALITY = quality;
		GamePanel.GAME_VERSION = version;
		GamePanel.GAME_VERSION_CODE = versionCode;
		if(fullscreen) setUndecorated(true);
		
		// Set up logging
		Log.setLogDir(GamePanel.GAME_TITLE);
		Log.log("width: "+width+" height: "+height+" fullscreen: "+fullscreen+" quality: "+quality+" title: "+title+
				" version: "+version+" versionCode: "+versionCode, 1);
		
		// Icon image
		//ImageIcon icon = new ImageIcon(getClass().getResource("/CPJD/icon.jpg"));
		//setIconImage(icon.getImage());
		
		setLayout(null);
		setContentPane(new GamePanel());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Game(1000,600,false,true,"Windfall Alpha 0.0.5","Alpha 0.0.5",1);
	}
	
}
