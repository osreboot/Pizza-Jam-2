package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.Random;

import org.newdawn.slick.Color;

public class PowerUp {

	public static final float POWERUP_SIZE = 100;
	public static final float POWERUP_START_LOCATION_X = 1440;
	public static final float POWERUP_START_LOCATION_Y = 720/2;
	public static final int POWERUP_CHANCE = 5000;



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

	//Powerup moved inward from right side of screen and stops in center. Disappears when collected.
	
	static void update(float delta) {
		
		//System.out.println(random);
		
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
		
		hvlDrawQuadc(POWERUP_START_LOCATION_X, POWERUP_START_LOCATION_Y, POWERUP_SIZE, POWERUP_SIZE, Color.red);
		
	}
	

}

