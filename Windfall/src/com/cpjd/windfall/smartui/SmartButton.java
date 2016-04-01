package com.cpjd.windfall.smartui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.cpjd.tools.Log;
import com.cpjd.tools.Mouse;
import com.cpjd.windfall.entity.Animation;

public class SmartButton {
	
	private boolean clicked;
	
	// Clicking won't do anything
	private boolean disable;
	
	// 0 - normal & clicked images, 1 - normal, hover, & clicked images, 2 - custom animation
	private int mode;
	
	// 0 - normal, 1 - hovering, 2 - clicked
	private int state;
	
	private BufferedImage img;
	private BufferedImage hoverImg;
	private BufferedImage clickImg;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	// Animation effects
	private boolean rotate;
	private boolean bulge;
	private boolean slide; 
	/*
	 * Technical variables
	 */
	
	// slide vars
	private boolean sliding;
	private int currentSlide;
	private int maxSlide = 1000;
	
	// bulge vars
	private int currentBulge;
	private final int maxBulge = 10;
	
	// customAnim vars
	Animation animation;
	
	// rotate vars
	int degree;
	
	// factors variable required for Smartbutton
	ArrayList<Integer> factors;
	
	// Load the image with a normal image, and a clicked image
	public SmartButton(String imgLoc, String clickLoc) {
		factors = new ArrayList<Integer>();
		
		try {
			mode = 0;
			
			img = ImageIO.read(getClass().getResourceAsStream(imgLoc));
			clickImg = ImageIO.read(getClass().getResourceAsStream(clickLoc));
			
			width = img.getWidth();
			height = img.getHeight();
		} catch(IOException e) {
			Log.logError(e, Log.RES_LOAD_ERROR); 
		}
	}
	
	// The button will contain a normal image, hover image, and click image
	public SmartButton(String imgLoc, String hoverLoc, String clickLoc) {
		factors = new ArrayList<Integer>();
		
		try {
			mode = 1;
			
			img = ImageIO.read(getClass().getResourceAsStream(imgLoc));
			hoverImg = ImageIO.read(getClass().getResourceAsStream(hoverLoc));
			clickImg = ImageIO.read(getClass().getResourceAsStream(clickLoc));
			
			width = img.getWidth();
			height = img.getHeight();
		} catch(IOException e) {
			Log.logError(e, Log.RES_LOAD_ERROR); 
		}
	}
	
	// The button will play an animation when hovered
	public SmartButton(String framesLoc, String clickLoc, int frameWidth, int frameHeight, int delay) {
		factors = new ArrayList<Integer>();

		try {
			mode = 2;
			
			width = frameWidth;
			height = frameHeight;
			
			clickImg = ImageIO.read(getClass().getResourceAsStream(clickLoc));
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(framesLoc));
			
			BufferedImage[] frames = new BufferedImage[spritesheet.getWidth() / frameWidth];
			
			for(int i = 0; i < frames.length; i++) {
				frames[i] = spritesheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
			}
			
			animation = new Animation();
			animation.setFrames(frames);
			animation.setDelay(delay);
			animation.setLoop(false);
			
		} catch(IOException e) {
			Log.logError(e, Log.RES_LOAD_ERROR);
		}
	}
	
	public void update() {
		
		if(clicked && !sliding) return;
		
		// Check which state
		if(isMouseHovering()) {
			state = 1;
			if(isMousePressed() && !disable) {
				state = 2;
			}
		} else {
			state = 0;
		}
		
		/*
		 * Manage effects
		 */
		
		if(bulge) {
			if(isMouseHovering()) {
				currentBulge++;
				if(currentBulge > maxBulge) currentBulge = maxBulge;
			} else {
				currentBulge--;
				if(currentBulge < 0) currentBulge = 0;
			}
		}
		
		if(rotate) {
			if(isMouseHovering()) {
				degree+=3;
				if(degree >= 90) degree = 90;
			} else {
				degree-=3;
				if(degree < 0) degree = 0;
			}
		}
		
		if(slide && isMousePressed()) {
			sliding = true;
		}
		
		if(sliding) {
			currentSlide += 75;
			if(currentSlide >= maxSlide) {
				sliding = false;
			}
		}
		
		if(mode == 2) {
			if(isMouseHovering()) {
				animation.update();
			} else {
				animation.setFrame(animation.getFrame() - 1);
			}
		}
		
		/*
		 * Manage the global clicked variable
		 */
		if(isMousePressed() && !disable) {
			clicked = true;
		}
		
	}

	public void draw(Graphics2D g) {
		
		// Rotation
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(degree), width / 2, height / 2);
		AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		
		// Calculate new drawing information
		int drawx = x, drawy = y;
		int drawWidth = width, drawHeight = height;
		
		if(bulge) {
			drawx = x - (currentBulge / 2);
			drawy = y - (currentBulge / 2);
			drawWidth += currentBulge;
			drawHeight += currentBulge;
		}
		
		if(slide) {
			drawx += currentSlide;
		}
		
		// Decide what to draw based off the current operating mode
		if (mode == 0) {
			if(rotate) {
				if(state == 0 || state == 1) g.drawImage(op.filter(img,null), drawx, drawy, op.filter(img, null).getWidth() + currentBulge,op.filter(img, null).getHeight() + currentBulge , null);
				if(state == 2) g.drawImage(op.filter(clickImg,null), drawx, drawy, op.filter(clickImg, null).getWidth() + currentBulge,op.filter(clickImg, null).getHeight() + currentBulge , null);
			} else {
				if(state == 0 || state == 1) g.drawImage(img, drawx, drawy, drawWidth, drawHeight, null);
				if(state == 2) g.drawImage(clickImg, drawx, drawy, drawWidth, drawHeight, null);
			}
		}
		if (mode == 1) {
			if(rotate) {
				if (state == 0) g.drawImage(op.filter(img,null), drawx, drawy, op.filter(clickImg, null).getWidth() + currentBulge,op.filter(clickImg, null).getHeight() + currentBulge , null);
				if (state == 1) g.drawImage(op.filter(hoverImg,null), drawx, drawy, op.filter(clickImg, null).getWidth() + currentBulge,op.filter(clickImg, null).getHeight() + currentBulge , null);
				if (state == 2) g.drawImage(op.filter(clickImg,null), drawx, drawy, op.filter(clickImg, null).getWidth() + currentBulge,op.filter(clickImg, null).getHeight() + currentBulge , null);	
			} else {
				if (state == 0) g.drawImage(img, drawx, drawy, drawWidth, drawHeight, null);
				if (state == 1) g.drawImage(hoverImg, drawx, drawy, drawWidth, drawHeight, null);
				if (state == 2) g.drawImage(clickImg, drawx, drawy, drawWidth, drawHeight, null);	
			}
		}
		if (mode == 2) {
			if(state != 2) {
				if(rotate) {
					g.drawImage(op.filter(animation.getImage(), null),drawx,drawy,op.filter(clickImg, null).getWidth() + currentBulge,op.filter(clickImg, null).getHeight() + currentBulge , null);
				} else {
					g.drawImage(animation.getImage(), drawx, drawy, drawWidth, drawHeight,null);
				}
			} else {
				if(rotate) {
					g.drawImage(op.filter(clickImg,null), drawx, drawy, op.filter(clickImg, null).getWidth() + currentBulge,op.filter(clickImg, null).getHeight() + currentBulge ,null);
				} else {
					g.drawImage(clickImg, drawx, drawy, drawWidth, drawHeight,null);
				}
			}
		}
	}
	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}
	public void setBulge(boolean bulge) {
		this.bulge = bulge;
	}
	public boolean isClicked() {
		return clicked;
	}
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	public void setPos(int x, int y) {
		this.x = x; this.y = y;
	}
	public void setPos(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setSlide(boolean slide) {
		this.slide = slide;
	}
	public void disable(boolean disable) {
		this.disable = disable;
	}
	public void reset() {
		currentSlide = 0;
		clicked = false;
		sliding = false;
		degree = 0;
	}
	public boolean isMouseHovering() {
		Rectangle rect = new Rectangle(x,y,width,height);
		
		if(rect.contains(new Point(Mouse.x,Mouse.y))) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isMousePressed() {
		Rectangle rect = new Rectangle(x,y,width,height);
		
		if(rect.contains(new Point(Mouse.x,Mouse.y)) && Mouse.leftPressed) {
			return true;
		} else {
			return false;
		}
	}
	public void addFactor(int factor) {
		factors.add(factor);
	}
	public int getArraySize() {
		return factors.size();
	}
	public ArrayList<Integer> getFactorsArray() {
		return factors;
	}
	public int getFactor(int index) {
		return factors.get(index);
	}
	public void clearArray() {
		factors.clear();
	}
}
