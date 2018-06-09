package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.Random;

import org.newdawn.slick.Color;

public class PowerUp {

	public static final float POWERUP_SIZE = 15;
	public static final float POWERUP_START_LOCATION_X = 1440;
	public static final float POWERUP_START_LOCATION_Y = 720/2;
	public static final int POWERUP_CHANCE = 4000;
	public static final int POWERUP_SPEED = 1;



	private float xPos;
	private float yPos;
	
	public static int powerUpType;
	public static boolean powerUpOnScreen;
	static Random r = new Random();
	private static int random = r.nextInt(POWERUP_CHANCE + 1); 
	

	public PowerUp(float xArg, float yArg, int typeArg) {

		xPos = xArg;
		yPos = yArg;
		powerUpType = typeArg;
		
	}
	
	static void update(float delta) {
		
		if(!powerUpOnScreen) {
			random = r.nextInt(POWERUP_CHANCE + 1);
		}
		
		if(random == POWERUP_CHANCE) {
			powerUpOnScreen = true;
		}
		
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xArg) {
		xPos = xArg;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yArg) {
		yPos = yArg;
	}

	public void draw(float delta) {
		hvlDrawQuadc(xPos, yPos, POWERUP_SIZE, POWERUP_SIZE, Color.red);
		
	}
	

}

