package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;

public class Player {

	private static float xPos;
	private static float yPos;
	private static float xSpeed;
	private static float ySpeed;
	private static float maxSpeed = 300;
	private static float health = 4;
	private static float fuelLevel;
	private static boolean boostState;
	private static boolean damageTaken;
	private static boolean invincibility = false;
	private static float tempTimer;
	private static float flareTimer = 1;
	

	public static float MAX_HEALTH = 4;
	public static final float PLAYER_SIZE = 50;
	public static final float PLAYER_START_X = 25;
	public static final float PLAYER_START_Y = 720/2;
	public static final float HEALTHBAR_X = 100;
	public static final float HEALTHBAR_Y = 694;
	public static final float MAX_SPEED = 300;
	public static final float ACCELERATION = 8500f;

	public Player(float xArg, float yArg){

		xPos = xArg;
		yPos = yArg;

	}

	public void draw(float delta){

		hvlDrawQuadc(xPos, yPos, PLAYER_SIZE, PLAYER_SIZE, Color.white);
		hvlDrawQuad(HEALTHBAR_X, HEALTHBAR_Y, health*40, 20, Color.white);

	}

	public void update(float delta) {
		
		tempTimer -= delta;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
			damageTaken = true;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			flareTimer = Flare.FLARE_LIFETIME;
		}
		flareTimer = HvlMath.stepTowards(flareTimer, delta, 0);
		
		if(damageTaken && !invincibility) {
			new Flare(xPos, yPos, 30f, -100f);
			new Flare(xPos, yPos, 30f, 100f);
			new Flare(xPos, yPos, -50f, -100f);
			new Flare(xPos, yPos, -50f, 100f);
			
			health = health - 1;
			invincibility = true;
			tempTimer = 2;
		}
		
		if(tempTimer <= 0) {
			invincibility = false;
			damageTaken = false;
		}
		

		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			ySpeed = ySpeed - (delta * ACCELERATION);
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			ySpeed = ySpeed + (delta * ACCELERATION);
		}else {
			ySpeed = HvlMath.stepTowards(ySpeed, ACCELERATION*delta, 0);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xSpeed = xSpeed - (delta * ACCELERATION);
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xSpeed = xSpeed + (delta * ACCELERATION);
		}else {
			xSpeed = HvlMath.stepTowards(xSpeed, ACCELERATION*delta, 0);
		}

		xPos = xPos + (xSpeed * delta);
		yPos = yPos + (ySpeed * delta);
		
		if(xSpeed >= MAX_SPEED) {
			xSpeed = MAX_SPEED;
		}
		
		if(xSpeed <= -MAX_SPEED) {
			xSpeed = -MAX_SPEED;
		}
		
		if(ySpeed >= MAX_SPEED) {
			ySpeed = MAX_SPEED;
		}
		
		if(ySpeed <= -MAX_SPEED) {
			ySpeed = -MAX_SPEED;
		}
		
		yPos = HvlMath.limit(yPos, PLAYER_SIZE/2, 720 - (PLAYER_SIZE/2));
		xPos = HvlMath.limit(xPos, PLAYER_SIZE/2, 1280 - (PLAYER_SIZE/2));


	}

	public float getX(){
		return xPos;
	}

	public float getY(){
		return yPos;
	}

	public void setX(float xArg) {
		xPos = xArg;
	}

	public void setY(float yArg) {
		yPos = yArg;
	}

	public float getxSpeed(){
		return xSpeed;
	}

	public float getySpeed(){
		return ySpeed;
	}

	public void setxSpeed(float xArg) {
		xSpeed = xArg;
	}

	public void setySpeed(float yArg) {
		ySpeed = yArg;
	}
	
	public float getHealth() {
		return health;
	}

}
