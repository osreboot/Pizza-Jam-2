package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class PowerUp {

	public static final float POWERUP_SIZE = 100;
	public static final float POWERUP_REST_LOCATION_X = 1440;
	public static final float POWERUP_REST_LOCATION_Y = 720/2;


	private float xPos;
	private float yPos;

	public PowerUp(float xArg, float yArg) {

		xPos = xArg;
		yPos = yArg;
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

	public void drawPowerUp() {
		
		hvlDrawQuadc(xPos, yPos, POWERUP_SIZE, POWERUP_SIZE, Color.red);
		
	}
	

}

