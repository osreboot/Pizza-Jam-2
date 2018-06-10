package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import java.util.Random;

public class PowerUp {

	public static final float POWERUP_SIZE = 20;
	public static final float POWERUP_START_LOCATION_X = 1440;
	public static final int POWERUP_CHANCE = 500;
	//base = 4000
	public static final int POWERUP_SPEED = 200;



	private float xPos;
	private float yPos;
	private float rotation = 0;
	
	public static int powerUpType;
	public static boolean powerUpOnScreen;
	static Random r = new Random();
	public static float powerUpSpawnY = r.nextInt(((720/2) + 200) - ((720/2) - 200) + 1) + ((720/2) - 200);
	public static float powerUpTypeGen = r.nextInt(100 + 1);
	public static float powerUpValHolder;

	private static int random = r.nextInt(POWERUP_CHANCE + 1); 
	

	public PowerUp(float xArg, float yArg) {

		xPos = xArg;
		yPos = yArg;
		
	}
	
	static void update(float delta) {
		
		//System.out.println(powerUpType);
		
		powerUpSpawnY = r.nextInt(((720/2) + 200) - ((720/2) - 200) + 1) + ((720/2) - 200);

		
		
		
		if(!powerUpOnScreen) {
			random = r.nextInt(POWERUP_CHANCE + 1);
			powerUpTypeGen = r.nextInt(100 + 1);
			if(Game.globalTimer > Game.endlessBegin + 10) powerUpTypeGen = 100;
			if(powerUpTypeGen > 0 && powerUpTypeGen < 66) {
				powerUpType = 1;
				//Health
			}else if(powerUpTypeGen >= 66 && Game.stage >= 2) {
				powerUpType = 2;
				//Flare
			}
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
		rotation += delta*25;
		hvlRotate(xPos, yPos, rotation);
		if(powerUpType == 1) {
		hvlDrawQuadc(xPos, yPos, POWERUP_SIZE, POWERUP_SIZE, Main.getTexture(Main.INDEX_POWERUP_HEALTH));
		}else if(powerUpType == 2) {
		hvlDrawQuadc(xPos, yPos, POWERUP_SIZE, POWERUP_SIZE, Main.getTexture(Main.INDEX_POWERUP_FLARE));
		}
		hvlResetRotation();
		
	}
	

}

