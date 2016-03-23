package com.cpjd.windfall.entity;
//Fuck the police - DAMN STRAIGHT MOFO
import java.awt.image.BufferedImage;

/* Animation for map objects, handles sprite animation */
public class Animation {

	private BufferedImage[] frames; // All the individual images of a sprite
	private int currentFrame; 
	
	private long startTime; // Between frames
	private long delay; // How long to wait between each frame
	
	private boolean playedOnce; // Wheteher or not the animation has already played, if it has looped
	
	// Whether to loop the animation when it finishes
	private boolean loop = false;
	
	public Animation() {
		playedOnce = false;
		loop = true;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames; // Pass in the array
		currentFrame = 0;
		startTime = System.nanoTime(); // The time the animation started
		playedOnce = false;
	}
	
	public void setDelay(long d) {
		delay = d;
	}
	public void setLoop(boolean b) {
		loop = b;
	}
	public void setFrame(int i) {
		if(i < 0) i = 0;
		currentFrame = i;
	}
	public void setPlayed(boolean played) {
		playedOnce = played;
	}
	public void update() { // Handles logic for determing whether or not to move to the next frame
		if(delay == -1) return;

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}

		if(currentFrame == frames.length) { // Loop the animation back to 0
			if(loop) currentFrame = 0;
			else currentFrame = frames.length - 1;
			playedOnce = true;
		}
		
	}

	public int getFrame() {
		return currentFrame;
	}
	public BufferedImage getImage() {
		return frames[currentFrame];
	}
	public boolean hasPlayedOnce() {
		return playedOnce;
	}

}
