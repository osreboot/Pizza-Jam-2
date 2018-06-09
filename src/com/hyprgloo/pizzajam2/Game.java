package com.hyprgloo.pizzajam2;

import java.util.ArrayList;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Game {

	public static Player player;
	public static ArrayList<LineSegment> tites;
	public static ArrayList<LineSegment> mites;

	public static PowerUp powerUp = new PowerUp(PowerUp.POWERUP_START_LOCATION_X, PowerUp.POWERUP_START_LOCATION_Y, 1);

	public static final float SCROLLSPEED = 100;

	public static float miteTimer;
	public static float titeTimer;

	public static void initialize(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
		HvlCoord2D miteStart = new HvlCoord2D();
		HvlCoord2D miteEnd = new HvlCoord2D();
		HvlCoord2D titeStart = new HvlCoord2D();
		HvlCoord2D titeEnd = new HvlCoord2D();
		tites = new ArrayList<LineSegment>();
		mites = new ArrayList<LineSegment>();
		miteTimer = 100;
		titeTimer = 100;
		miteStart.x = HvlMath.randomFloatBetween(1000, 1200);
		miteStart.y = HvlMath.randomFloatBetween(550, 800);
		miteEnd.x = HvlMath.randomFloatBetween(1280, 1350);
		miteEnd.y = HvlMath.randomFloatBetween(550, 800);
		titeStart.x = HvlMath.randomFloatBetween(1000,1200);
		titeStart.y = HvlMath.randomFloatBetween(-80, 120);
		titeEnd.x = HvlMath.randomFloatBetween(1280, 1350);
		titeEnd.y = HvlMath.randomFloatBetween(-80, 120);
		LineSegment mite = new LineSegment(miteStart, miteEnd);
		mites.add(mite);
		LineSegment tite = new LineSegment(titeStart, titeEnd);
		tites.add(tite);
	}


	public static void genMite(float delta) {

		HvlCoord2D miteStart = new HvlCoord2D();
		HvlCoord2D miteEnd = new HvlCoord2D();


		miteStart.x = mites.get(mites.size()-1).end.x;
		miteStart.y = mites.get(mites.size()-1).end.y;
		miteEnd.x = HvlMath.randomFloatBetween(1280, 1350);
		miteEnd.y = HvlMath.randomFloatBetween(640, 720);
		LineSegment mite = new LineSegment(miteStart, miteEnd);
		mites.add(mite);
		miteTimer = HvlMath.randomFloatBetween(80, 120);
	}
	public static void genTite(float delta) {
		HvlCoord2D titeStart = new HvlCoord2D();
		HvlCoord2D titeEnd = new HvlCoord2D();

		titeStart.x = tites.get(tites.size()-1).end.x;
		titeStart.y = tites.get(tites.size()-1).end.y;
		titeEnd.x = HvlMath.randomFloatBetween(1280, 1350);
		titeEnd.y = HvlMath.randomFloatBetween(0, 80);
		LineSegment tite = new LineSegment(titeStart, titeEnd);
		tites.add(tite);
		titeTimer = HvlMath.randomFloatBetween(80, 120);
	}
	public static void update(float delta){
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
		titeTimer -= delta*150;
		miteTimer -= delta*150;
		if(miteTimer <= 0) {
			genMite(delta);
		}
		if(titeTimer <= 0) {
			genTite(delta);
		}
		player.update(delta);
		player.draw(delta);


		PowerUp.update(delta);

		if(PowerUp.powerUpOnScreen) {
			powerUp.draw(delta);
			powerUp.setxPos(powerUp.getxPos() - PowerUp.POWERUP_SPEED);
			if(powerUp.getxPos() <= 854) {
				powerUp.setxPos(854);
			}
			if(player.getY() >= powerUp.getyPos() - (PowerUp.POWERUP_SIZE/2) - (player.PLAYER_SIZE/2) &&
					player.getY() <= powerUp.getyPos() + (PowerUp.POWERUP_SIZE/2) + (player.PLAYER_SIZE/2) &&
					player.getX() >= powerUp.getxPos() - (PowerUp.POWERUP_SIZE/2) - (player.PLAYER_SIZE/2) &&
					player.getX() <= powerUp.getxPos() + (PowerUp.POWERUP_SIZE/2) + (player.PLAYER_SIZE/2)) {
						PowerUp.powerUpOnScreen = false;
						powerUp.setxPos(PowerUp.POWERUP_START_LOCATION_X);
						powerUp.setyPos(PowerUp.POWERUP_START_LOCATION_Y);

					}

		}

	}

	public static void restart(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
		//		tites.clear();
		//		mites.clear();
	}

}
