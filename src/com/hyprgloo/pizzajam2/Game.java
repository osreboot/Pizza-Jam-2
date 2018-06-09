package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Game {

	public static float globalTimer = 0f;
	
	public static Player player;
	public static ArrayList<LineSegment> tites;
	public static ArrayList<LineSegment> mites;
	public static ArrayList<Flare> flares;

	public static PowerUp powerUp = new PowerUp(PowerUp.POWERUP_START_LOCATION_X, PowerUp.POWERUP_START_LOCATION_Y, 1);

	public static final float SCROLLSPEED = 400;
	public static final float 
	TERRAIN_CENTER_TIMER = 3f,
	TERRAIN_BUFFER = 50f,
	TERRAIN_HEIGHT = 256f, 
	TERRAIN_MIN_WIDTH = 50f,//1f,
	TERRAIN_MAX_WIDTH = 250f,//5f,
	TERRAIN_MIN_TIGHTNESS = 35f,
	TERRAIN_MAX_TIGHTNESS = 150f;
	
	public static float terrainCenter = Display.getHeight()/2;
	public static float terrainTightness = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;
	public static float terrainCenterTimer = TERRAIN_CENTER_TIMER;
	public static float terrainCenterGoal = Display.getHeight()/2;
	public static float terrainTightnessGoal = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;
	
	private static boolean powerUpPickup = false;

	public static void initialize(){
		globalTimer = 0f;
		
		terrainCenter = Display.getHeight()/2;
		terrainTightness = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;
		terrainCenterTimer = TERRAIN_CENTER_TIMER;
		terrainCenterGoal = Display.getHeight()/2;
		terrainTightnessGoal = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;
		
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
		tites = new ArrayList<LineSegment>();
		mites = new ArrayList<LineSegment>();
		flares = new ArrayList<>();
		LineSegment mite = new LineSegment(
				new HvlCoord2D(0, Display.getHeight() - TERRAIN_BUFFER), 
				new HvlCoord2D(Display.getWidth(), Display.getHeight() - TERRAIN_BUFFER), false);
		mites.add(mite);
		LineSegment tite = new LineSegment(
				new HvlCoord2D(0, TERRAIN_BUFFER), 
				new HvlCoord2D(Display.getWidth(), TERRAIN_BUFFER), true);
		tites.add(tite);
	}

	public static void update(float delta){
		globalTimer += delta;
		
		terrainCenterTimer -= delta;
		if(terrainCenterTimer <= 0){
			terrainCenterTimer = TERRAIN_CENTER_TIMER;
			terrainTightnessGoal = HvlMath.randomFloatBetween(TERRAIN_MIN_TIGHTNESS, TERRAIN_MAX_TIGHTNESS);
			terrainCenterGoal = HvlMath.randomFloatBetween(TERRAIN_BUFFER + terrainTightnessGoal, Display.getHeight() - TERRAIN_BUFFER - terrainTightnessGoal);
		}
		terrainTightness = HvlMath.stepTowards(terrainTightness, delta*60f, terrainTightnessGoal);
		terrainCenter = HvlMath.stepTowards(terrainCenter, delta*100f, terrainCenterGoal);
		//hvlDrawQuadc(Display.getWidth(), terrainCenter, 25, 25, Color.pink);
		//hvlDrawQuadc(Display.getWidth(), terrainCenter - terrainTightness, 15, 15, Color.pink);
		//hvlDrawQuadc(Display.getWidth(), terrainCenter + terrainTightness, 15, 15, Color.pink);
		
		for(LineSegment miteWave : mites) {
			miteWave.start.x -= delta*SCROLLSPEED;
			miteWave.end.x -= delta*SCROLLSPEED;
			miteWave.draw(delta);
			
		}
		for(LineSegment titeWave : tites) {
			titeWave.start.x -= delta*SCROLLSPEED;
			titeWave.end.x -= delta*SCROLLSPEED;
			titeWave.draw(delta);
		}
		if(mites.get(mites.size()-1).end.x <= Display.getWidth() + 10) {
			generateTerrain(delta, false);
		}
		if(tites.get(tites.size()-1).end.x <= Display.getWidth() + 10) {
			generateTerrain(delta, true);
		}
		
		float gradientAlpha = Math.min(globalTimer/3f, 1f);
		hvlDrawQuadc(player.getX(), Display.getHeight()/2, 1200, 1200, Main.getTexture(Main.INDEX_GRADIENT), new Color(1f, 1f, 1f, gradientAlpha));
		
		player.update(delta);
		player.draw(delta);

		PowerUp.update(delta);
		if(PowerUp.powerUpOnScreen) {
			powerUp.draw(delta);
			
			powerUp.setxPos(powerUp.getxPos() - PowerUp.POWERUP_SPEED);
			
			
			if(powerUp.getxPos() <= 854) {
				powerUp.setxPos(854);
			}
			if(player.getY() >= powerUp.getyPos() - (PowerUp.POWERUP_SIZE/2) - (Player.PLAYER_SIZE/2) &&
					player.getY() <= powerUp.getyPos() + (PowerUp.POWERUP_SIZE/2) + (Player.PLAYER_SIZE/2) &&
					player.getX() >= powerUp.getxPos() - (PowerUp.POWERUP_SIZE/2) - (Player.PLAYER_SIZE/2) &&
					player.getX() <= powerUp.getxPos() + (PowerUp.POWERUP_SIZE/2) + (Player.PLAYER_SIZE/2)) {
				PowerUp.powerUpOnScreen = false;
				powerUp.setxPos(PowerUp.POWERUP_START_LOCATION_X);
				powerUp.setyPos(PowerUp.POWERUP_START_LOCATION_Y);
				powerUpPickup = true;
			}
			
			if(powerUpPickup) {
				
				if(player.getHealth() == Player.MAX_HEALTH) {
					
					Main.font.drawWordc("+1 HP", powerUp.getxPos(), powerUp.getyPos(), Color.white);
					
				}
				
			}
			
		}
		
		for(Flare.Smoke s : Flare.Smoke.smokeParticles){
			s.update(delta);
		}
		
		for(Flare f : flares){
			f.update(delta);
		}
	}

	public static void restart(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
		powerUp = new PowerUp(PowerUp.POWERUP_START_LOCATION_X, PowerUp.POWERUP_START_LOCATION_Y, 1);
		tites.clear();
		mites.clear();
	}
	
	public static void generateTerrain(float delta, boolean tite) {
		HvlCoord2D lineStart = new HvlCoord2D();
		HvlCoord2D lineEnd = new HvlCoord2D();
		if(tite){
			lineStart.x = tites.get(tites.size()-1).end.x;
			lineStart.y = tites.get(tites.size()-1).end.y;
		}else{
			lineStart.x = mites.get(mites.size()-1).end.x;
			lineStart.y = mites.get(mites.size()-1).end.y;
		}
		lineEnd.x = HvlMath.randomFloatBetween(Display.getWidth() + TERRAIN_MIN_WIDTH, Display.getWidth() + TERRAIN_MAX_WIDTH);
		if(tite){
			lineEnd.y = HvlMath.randomFloatBetween(TERRAIN_BUFFER, terrainCenter - terrainTightness);
		}else{
			lineEnd.y = HvlMath.randomFloatBetween(Display.getHeight() - TERRAIN_BUFFER, terrainCenter + terrainTightness);
		}
		LineSegment line = new LineSegment(lineStart, lineEnd, tite);
		if(tite){
			tites.add(line); 
		}else{
			mites.add(line);
		}
	}

}
