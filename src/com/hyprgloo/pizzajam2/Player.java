package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import org.newdawn.slick.Color;

public class Player {

	private static float xPos;
	private static float yPos;
	
	public static final float PLAYER_SIZE = 50;
	public static final float PLAYER_START_X = 50;
	public static final float PLAYER_START_Y = 720/2;
	
	public Player(float xArg, float yArg){

		xPos = xArg;
		yPos = yArg;
		
	}

	public void draw(float delta){

		hvlDrawQuad(xPos, yPos, PLAYER_SIZE, PLAYER_SIZE, Color.white);
		
	}
	
	public void update(float delta) {
		
		
		
	}

	public float getX(){
		return xPos;
	}

	public float getY(){
		return yPos;
	}

}
