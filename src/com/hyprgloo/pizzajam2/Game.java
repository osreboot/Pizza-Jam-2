package com.hyprgloo.pizzajam2;

import java.util.ArrayList;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Game {
	
	public static Player player;
	public static ArrayList<LineSegment> tites;
	public static ArrayList<LineSegment> mites;
	public static ArrayList<PowerUp> powerUps;
	
	public static HvlCoord2D miteStart;
	public static HvlCoord2D miteEnd;
	public static HvlCoord2D titeStart;
	public static HvlCoord2D titeEnd;
	
	public static float miteTimer;
	public static float titeTimer;
	
	public static void initialize(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
		miteStart = new HvlCoord2D();
		miteEnd = new HvlCoord2D();
		titeStart = new HvlCoord2D();
		titeEnd = new HvlCoord2D();
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
		miteStart.x = mites.get(mites.size()-1).end.x;
		miteStart.y = mites.get(mites.size()-1).end.y;
		miteEnd.x = HvlMath.randomFloatBetween(1280, 1350);
		miteEnd.y = HvlMath.randomFloatBetween(640, 720);
		LineSegment mite = new LineSegment(miteStart, miteEnd);
		mites.add(mite);
		miteTimer = HvlMath.randomFloatBetween(80, 120);
	}
	public static void genTite(float delta) {
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
			miteWave.start.x -= delta*100;
			miteWave.end.x -= delta*100;
			miteWave.draw(delta);
		}
		for(LineSegment titeWave : tites) {
			titeWave.start.x -= delta*100;
			titeWave.end.x -= delta*100;
			titeWave.draw(delta);
		}
		titeTimer -= delta*50;
		miteTimer -= delta*50;
		if(miteTimer <= 0) {
			genMite(delta);
		}
		if(titeTimer <= 0) {
			genTite(delta);
		}
		player.update(delta);
		player.draw(delta);
		
		PowerUp.update(delta);
	}
	
	public static void restart(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
//		tites.clear();
//		mites.clear();
	}

}
