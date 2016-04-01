package com.cpjd.windfall.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.cpjd.tools.Log;
import com.cpjd.tools.Mouse;
import com.cpjd.windfall.gamestate.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = -8276303685553158786L;
	
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;
	
	public static String GAME_TITLE;
	public static String GAME_VERSION;
	public static int GAME_VERSION_CODE;
	
	public static boolean GAME_QUALITY;
	public static boolean GAME_DEBUG;
	
	// Thread
	private Thread thread;
	private volatile boolean running;
	private final static int FPS = 150;
	private long targetTime = 1000 / FPS;
	
	// Image
	private BufferedImage image;
	private Graphics2D g;

	GameStateManager gsm;
	
	public GamePanel() {
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		setFocusable(true);
		requestFocus();
		
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			requestFocus();
			addMouseMotionListener(this);
			addMouseListener(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void init() {
		image = new BufferedImage(GAME_WIDTH,GAME_HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		if(GAME_QUALITY) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		running = true;
		
		gsm = new GameStateManager();
		
	}

	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running) {
			start = System.nanoTime();
			{
				update();
				draw();
				drawToScreen();
			}
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 0;
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				Log.logError(e, Log.THREAD_ERROR);
				Log.alertError(e, Log.THREAD_ERROR);
			}
		}
	}
	
	private void update() {
		gsm.update();

	}
	
	public int zoom = 0;
	
	private void draw() {
		g.setColor(Color.WHITE);
		g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
		
		gsm.draw(g);

		//Toolkit.getDefaultToolkit().sync(); // Refreshes the display on some systems
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
		g2.dispose();
	}

	public static boolean w;
	public static boolean s;
	public static boolean a;
	public static boolean d;
	public static boolean q;
	public static boolean e;
	
	
	/* Input Handlers */
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_W) {
			w = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_A) {
			a = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_S) {
			s = true;
		}if(key.getKeyCode() == KeyEvent.VK_D) {
			d = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_E) {
			e = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_Q) {
			q = true;
		}
	}
	public void keyReleased(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_W) {
			w = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_A) {
			a = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_S) {
			s = false;
		}if(key.getKeyCode() == KeyEvent.VK_D) {
			d = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_E) {
			e = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_Q) {
			q = false;
		}
	}
	public void keyTyped(KeyEvent key) {}
	public void mousePressed(MouseEvent mouse) {
		if(mouse.getButton() == 1) Mouse.leftPressed = true;
	}
	public void mouseReleased(MouseEvent mouse) {
		if(mouse.getButton() == 1) Mouse.leftPressed = false;
	}
	public void mouseMoved(MouseEvent mouse) {
		Mouse.y = mouse.getY();
		Mouse.x = mouse.getX();
	}
	public void mouseDragged(MouseEvent mouse) {}
	public void mouseClicked(MouseEvent mouse) {
	}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}	
}
