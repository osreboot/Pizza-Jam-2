package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.hyprgloo.pizzajam2.Flare.Smoke;
import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Player {

	private float xPos;
	private float yPos;
	private float xSpeed;
	private float ySpeed;
	private float health = 4;
	public boolean damageTaken;
	public boolean hitMine;
	private boolean invincibility = false;
	private float tempTimer;
	private float score = 0;
	public float flareTimer = 0f;
	public boolean hasFlare = true;
	
	public HvlCoord2D impartedMomentum = new HvlCoord2D();

	public static final float MAX_HEALTH = 4;
	public static final float PLAYER_SIZE = 50;
	public static final float PLAYER_START_X = 256;
	public static final float PLAYER_START_Y = 720/2;
	public static final float HEALTHBAR_X = 64;
	public static final float HEALTHBAR_Y = 720 - 36;
	public static final float MAX_SPEED = 300;
	public static final float ACCELERATION = 8500f;
	public static final float FLARE_INV_X = HEALTHBAR_X + 128 + 8;
	public static final float FLARE_INV_Y = HEALTHBAR_Y;
	public static final float UI_TRANSPARENCY = 0.8f;

	public Player(float xArg, float yArg){

		xPos = xArg;
		yPos = yArg;

	}

	private float rotateGoal = 0f, smokeEmitTimer = 0f;
	public void draw(float delta){
		smokeEmitTimer += delta;
		if(smokeEmitTimer > 0.4f){
			new Smoke(xPos - 38f, yPos + HvlMath.randomFloatBetween(-12f, 12f) - 4f, 32f, -96f);
			smokeEmitTimer = 0f;
		}
		rotateGoal = HvlMath.stepTowards(rotateGoal, delta*45f, xSpeed/30f);
		float value = HvlMath.mapl(flareTimer, 0f, Flare.FLARE_LIFETIME/4f, 0.4f, 1.0f);
		
		hvlRotate(xPos, yPos, rotateGoal);
		hvlDrawQuadc(xPos, yPos, PLAYER_SIZE*1.5f, PLAYER_SIZE*1.5f, Main.getTexture(Main.INDEX_SHIP), new Color(value, value, value));
		hvlResetRotation();
		if(health == 0){
			hvlDrawQuad(HEALTHBAR_X, HEALTHBAR_Y, 128, 32, Main.getTexture(Main.INDEX_HEALTH0), new Color(1f, 1f, 1f, UI_TRANSPARENCY));
		}else if(health == 1){
			hvlDrawQuad(HEALTHBAR_X, HEALTHBAR_Y, 128, 32, Main.getTexture(Main.INDEX_HEALTH1), new Color(1f, 0.15f, 0.15f, UI_TRANSPARENCY));
		}else if(health == 2){
			hvlDrawQuad(HEALTHBAR_X, HEALTHBAR_Y, 128, 32, Main.getTexture(Main.INDEX_HEALTH2), new Color(1f, 1f, 1f, UI_TRANSPARENCY));
		}else if(health == 3){
			hvlDrawQuad(HEALTHBAR_X, HEALTHBAR_Y, 128, 32, Main.getTexture(Main.INDEX_HEALTH3), new Color(1f, 1f, 1f, UI_TRANSPARENCY));
		}else if(health == 4){
			hvlDrawQuad(HEALTHBAR_X, HEALTHBAR_Y, 128, 32, Main.getTexture(Main.INDEX_HEALTH4), new Color(1f, 1f, 1f, UI_TRANSPARENCY));
		}
	}

	public void update(float delta) {
		
		tempTimer -= delta;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && hasFlare) {
			//Game.playerErrorTimer = 1f;
			flareTimer = Flare.FLARE_LIFETIME/2f;
			//damageTaken = true;
			new Flare(xPos, yPos, 30f, -100f);
			new Flare(xPos, yPos, 30f, 100f);
			new Flare(xPos, yPos, -50f, -100f);
			new Flare(xPos, yPos, -50f, 100f);
			hasFlare = false;
		}
		flareTimer = HvlMath.stepTowards(flareTimer, delta, 0);
		
		if(hasFlare){
			hvlDrawQuad(FLARE_INV_X, FLARE_INV_Y, 64, 32, Main.getTexture(Main.INDEX_FLARE_ICON1), Game.globalTimer % 1f > 0.5f ? new Color(1f, 0.5f, 0f, UI_TRANSPARENCY) : new Color(1f, 1f, 1f, UI_TRANSPARENCY));
			hvlDrawQuad(FLARE_INV_X, FLARE_INV_Y, 64, 32, Main.getTexture(Main.INDEX_FLARE_ICON2), Game.globalTimer % 1f > 0.5f ? new Color(1f, 1f, 1f, UI_TRANSPARENCY) : new Color(1f, 0.5f, 0f, UI_TRANSPARENCY));
		}else{
			hvlDrawQuad(FLARE_INV_X, FLARE_INV_Y, 64, 32, Main.getTexture(Main.INDEX_FLARE_ICON0), new Color(1f, 1f, 1f, UI_TRANSPARENCY));
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
		 
		}

		
		
		if(damageTaken && !invincibility) {
			if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_CRUNCH).playAsSoundEffect(1, 1f, false);
			health = health - 1;
			invincibility = true;
			tempTimer = 1;
		}
		if(hitMine) {
			//health -= 1;
			hitMine = false;
		}
		
		
		if(tempTimer <= 0) {
			invincibility = false;
			damageTaken = false;
		}
		
		impartedMomentum.x = HvlMath.stepTowards(impartedMomentum.x, delta*1000f, 0);
		impartedMomentum.y = HvlMath.stepTowards(impartedMomentum.y, delta*1000f, 0);

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

		xPos = xPos + (xSpeed * delta) + (impartedMomentum.x * delta);
		yPos = yPos + (ySpeed * delta) + (impartedMomentum.y * delta);
		
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
	
	public void setHealth(float healthArg) {
		health = healthArg;
	}
	
	public float getScore() {
		return score;
	}
	
	public boolean getInvincibleState() {
		return invincibility;
	}
	
	
	public void setScore(float scoreArg) {
		score = scoreArg;
	}

}
