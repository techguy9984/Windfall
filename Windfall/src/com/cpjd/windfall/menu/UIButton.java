package com.cpjd.windfall.menu;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.tools.Log;
import com.cpjd.tools.Mouse;
import com.cpjd.windfall.entity.Animation;

public class UIButton {
	
	// The magical most important variable - Indicates that all animations and everything isdone
	private boolean clicked;
	
	// Button information
	BufferedImage img;
	BufferedImage clickedImg;
	BufferedImage hoverImg;
	private int state; // 0 - normal 1 - hover 2 - clicked
	private int width;
	private int height;
	
	// Drawing location
	private int x;
	private int y;
	
	// Animation configurations
	private boolean rotate; private boolean lockRotate;
	private boolean bulge;
	private boolean customAnim; // The animation that plays when the mouse is hovering, rotate is not allowed for this mode
	
	// Rotate technical stuff
	private int degree;
	
	// Bulge technical stuff
	private int currentBulge;
	private int maxBulge = 10;
	
	// Custom animation
	Animation animation;
	
	private boolean lockPress; // Disables clicking
	
	public UIButton(String location) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(location));
			height = img.getHeight();
			width = img.getWidth();
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
		init();
	}
	
	public UIButton(String location, String clickedLocation) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(location));
			height = img.getHeight();
			width = img.getWidth();
			
			clickedImg = ImageIO.read(getClass().getResourceAsStream(clickedLocation));
			
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
		init();
	}
	
	public UIButton(String location, String hoverLocation, String clickedLocation) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(location));
			height = img.getHeight();
			width = img.getWidth();
			
			clickedImg = ImageIO.read(getClass().getResourceAsStream(clickedLocation));
			
			hoverImg = ImageIO.read(getClass().getResourceAsStream(hoverLocation));
			
		} catch(Exception e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
		init();
	}

	public UIButton(String location, String framesSheet, String clickedLocation, int delay) {

		//this.x = x; this.y = y;
		
		customAnim = true;
		
		try {
			img = ImageIO.read(getClass().getResourceAsStream(location));
			
			width = img.getWidth();
			height = img.getHeight();
			
			clickedImg = ImageIO.read(getClass().getResourceAsStream(clickedLocation));
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(framesSheet));
			
			BufferedImage[] frames = new BufferedImage[spritesheet.getWidth() / img.getWidth()];
			
			for(int i = 0; i < spritesheet.getWidth() / img.getWidth(); i++) {
				frames[i] = spritesheet.getSubimage(i * img.getWidth(), 0, img.getWidth(), img.getHeight());
			}
			
			animation = new Animation();
			animation.setFrames(frames);
			animation.setDelay(delay);
			animation.setLoop(false);
			
		} catch(Exception e) {	
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
		
		init();
		
	}
	
	public void setPos(int x, int y) {
		this.x = x; this.y = y;
	}
	public void init() {
		clicked = false;
		state = 0;
		lockRotate = false;
		currentBulge = 0;
		lockPress = false;
	}
	
	public void update() {
		
		// If clicked (final) skip all calculations
		if(clicked) return;
		
		// State detection
		if(img != null && !isHovered() && !isPressed()) {
			state = 0;
		}
		
		if(hoverImg != null && isHovered() && !isPressed()) {
			state = 1;
		}
		
		if(clickedImg != null && isPressed()) {
			state = 2;
			if(!lockPress) clicked = true;
		}
		
		/*
		 * ROTATION
		 */
		
		if(rotate) {
			if(isHovered() && !isPressed()) {
				if(degree <= 90) degree += 3;
			} else if(!isPressed()){
				if(degree > 0) degree -=3;
			}
			
			if(isPressed()) lockRotate = true; 
			
			if(lockRotate) {
				degree += 15;
				if(degree > 550) {
					if(!lockPress) clicked = true;
				}
			}
		}
		
		/*
		 * BULGE
		 */
		if(bulge) {
			if(isHovered()) {
				if(currentBulge < maxBulge) currentBulge++;
			} else {
				if(currentBulge > 0) currentBulge--;
			}
		}
		
		if(customAnim) {
			if(isHovered()) {
				animation.update();
			} else {
				animation.setFrame(animation.getFrame() - 1);
			}
		}
	}
	
	public void draw(Graphics2D g) {
		
		if(rotate || bulge && !customAnim) {
			AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(degree), (img.getWidth() / 2), (img.getHeight() / 2));
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
			if(state == 0) g.drawImage(op.filter(img,null), x - (currentBulge / 2), y - (currentBulge / 2), op.filter(img, null).getWidth() + currentBulge,op.filter(img, null).getHeight() + currentBulge, null);
			if(state == 1) g.drawImage(op.filter(hoverImg,null), x - (currentBulge / 2), y - (currentBulge / 2), op.filter(img, null).getWidth() + currentBulge,op.filter(img, null).getHeight() + currentBulge, null);
			if(state == 2) g.drawImage(op.filter(clickedImg,null), x - (currentBulge / 2), y - (currentBulge / 2), op.filter(img, null).getWidth() + currentBulge,op.filter(img, null).getHeight() + currentBulge, null);
		}
		
		if(customAnim) {
			if(state != 2) g.drawImage(animation.getImage(), x - (currentBulge / 2), y - (currentBulge / 2), width + currentBulge, height + currentBulge,null);
			else {
				g.drawImage(clickedImg, x - (currentBulge / 2), y - (currentBulge / 2), width + currentBulge, height + currentBulge,null);
			}
		}
	}
	
	// Checks if the mouse has been clicked in the button's region
	private boolean isPressed() {
		Rectangle rectangle = new Rectangle(x, y, width, height);

		if (Mouse.x >= rectangle.x && Mouse.x <= rectangle.x + rectangle.width && Mouse.y >= rectangle.y
				&& Mouse.y <= rectangle.y + rectangle.height && Mouse.leftPressed) {
			return true;
		} else {
			return false;
		}
	}

	// Checks if the mouse is within the button's region
	private boolean isHovered() {
		Rectangle rectangle = new Rectangle(x, y, width, height);
		
		if(Mouse.x >= rectangle.x && Mouse.x <= rectangle.x + rectangle.width &&
				Mouse.y >= rectangle.y && Mouse.y <= rectangle.y + rectangle.height) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void setLocked(boolean b) {
		lockRotate = b;
	}
	
	public void setRotate(boolean b) {
		rotate = b;
	}
	
	public void setBulge(boolean b) {
		bulge = b;
	}
	
	public int getWidth() {
		return img.getWidth();
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	public void setDegree(int i) {
		degree = i;
	}
	public void setClicked(boolean b) {
		clicked = b;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setLockPress(boolean b) {
		lockPress = b;
	}
	
}