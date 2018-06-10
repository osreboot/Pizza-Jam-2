package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.menu.HvlMenu;

public class Game {

	public static float globalTimer = 0f;

	public static Player player;
	public static ArrayList<LineSegment> tites;
	public static ArrayList<LineSegment> mites;
	public static ArrayList<Flare> flares;

	public static PowerUp powerUp = new PowerUp(PowerUp.POWERUP_START_LOCATION_X, PowerUp.powerUpSpawnY);
	public static Mine mine = new Mine(Mine.MINE_START_LOC_X, Mine.mineSpawnY);
	public static Shockwave wave;
	public static final float SCROLLSPEED = 400;
	public static float 
	TERRAIN_CENTER_TIMER = 3f,
	TERRAIN_BUFFER = 50f,
	TERRAIN_HEIGHT = 256f, 
	TERRAIN_MIN_WIDTH = 50f,//1f,
	TERRAIN_MAX_WIDTH = 250f,//5f,
	TERRAIN_MIN_TIGHTNESS = 35f,
	TERRAIN_MAX_TIGHTNESS = 150f,
	TERRAIN_KNOCKBACK_X = 800,
	TERRAIN_KNOCKBACK_Y = 500;

	public static float terrainCenter = Display.getHeight()/2;
	public static float terrainTightness = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;
	public static float terrainCenterTimer = TERRAIN_CENTER_TIMER;
	public static float terrainCenterGoal = Display.getHeight()/2;
	public static float terrainTightnessGoal = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;

	private static boolean powerUpPickup = false;
	private static boolean healthPickup = false;
	private static boolean scorePickup = false;
	private static boolean flarePickup = false;
	private static boolean bigScorePickup = false;
	private static float powerUpTextCurrentHeight;
	private static float powerUpTextOpacity = 1;

	private static float powerUpTextX;
	private static float powerUpTextY;
	public static float playerErrorTimer = 0f;
	private static boolean powerUpHeightCheck = true;
	private static boolean powerUpHasGivenHealth = true;
	private static boolean powerUpHasGivenScore = true;
	private static boolean powerUpHasGivenFlare = true;
	private static boolean powerUpHasGivenBigScore = true;
	public static boolean spawnedWave = false;
	static float yCrossTites = 0;
	static float yCrossMites = 0;
	
	static int stage = 1;
	private static int textX = 100;
	
	private static boolean level1Changed = false;
	private static boolean level2Changed = false;
	private static boolean level3Changed = false;
	private static boolean level4Changed = false;
	private static boolean level5Changed = false;
	
	
	private static int
	timeStage1Start = 0,
	timeStage1End = 25,
	timeStage2Start = 28,
	timeStage2End = 63,
	timeStage3Start = 66,
	timeStage3End = 91,
	timeStage4Start = 94,
	timeStage4End = 119,
	timeStage5Start = 122,
	timeStage5End = 147,
	endlessBegin = 164;

	public static void initialize(){
		globalTimer = 0f;

		terrainCenter = Display.getHeight()/2;
		terrainTightness = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;
		terrainCenterTimer = TERRAIN_CENTER_TIMER;
		terrainCenterGoal = Display.getHeight()/2;
		terrainTightnessGoal = (TERRAIN_MIN_TIGHTNESS + TERRAIN_MAX_TIGHTNESS)/2f;

		powerUpPickup = false;
		healthPickup = false;
		scorePickup = false;
		flarePickup = false;
		bigScorePickup = false;
		powerUpTextOpacity = 1;

		playerErrorTimer = 0f;
		powerUpHeightCheck = true;
		powerUpHasGivenHealth = true;
		powerUpHasGivenScore = true;
		powerUpHasGivenFlare = true;
		powerUpHasGivenBigScore = true;
		spawnedWave = false;

		mine = new Mine(Mine.MINE_START_LOC_X, Mine.mineSpawnY);
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
		
		//System.out.println(player.getScore());

		textX -= SCROLLSPEED*delta;

		player.setScore(player.getScore() + (player.getX() * delta/2));
		
		


		playerErrorTimer = HvlMath.stepTowards(playerErrorTimer, delta, 0f);

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
			float origAngle;
			origAngle = HvlMath.fullRadians(miteWave.start, miteWave.end);
			
			if(player.getX() >= miteWave.start.x && player.getX() <= miteWave.end.x) {
				yCrossMites = HvlMath.map(player.getX(), miteWave.start.x, miteWave.end.x, miteWave.start.y, miteWave.end.y);
				if(player.getY() > yCrossMites){
					playerErrorTimer = 1f;
					player.damageTaken = true;
					
					
					player.impartedMomentum.y = TERRAIN_KNOCKBACK_Y * (float)Math.cos(origAngle);
					player.impartedMomentum.x = -TERRAIN_KNOCKBACK_X * (float)Math.sin(origAngle);
					
					player.setY(yCrossMites);
					player.setySpeed(Math.min(player.getySpeed(), 0));
				}
			}
		}
		//System.out.println(globalTimer);
		for(LineSegment titeWave : tites) {
			titeWave.start.x -= delta*SCROLLSPEED;
			titeWave.end.x -= delta*SCROLLSPEED;

			titeWave.draw(delta);
			float origAngle;
			origAngle = HvlMath.fullRadians(titeWave.start, titeWave.end);
			if(player.getX() >= titeWave.start.x && player.getX() <= titeWave.end.x) {
				yCrossTites = HvlMath.map(player.getX(), titeWave.start.x, titeWave.end.x, titeWave.start.y, titeWave.end.y);
				if(player.getY() < yCrossTites){
					playerErrorTimer = 1f;
					player.damageTaken = true;
					player.impartedMomentum.y = -TERRAIN_KNOCKBACK_Y * (float)Math.cos(origAngle);
					player.impartedMomentum.x = TERRAIN_KNOCKBACK_X * (float)Math.sin(origAngle);
					player.setY(yCrossTites);
					player.setySpeed(Math.max(player.getySpeed(), 0));
				}
			}
		}
		if(mites.get(mites.size()-1).end.x <= Display.getWidth() + 10) {
			generateTerrain(delta, false);
		}
		if(tites.get(tites.size()-1).end.x <= Display.getWidth() + 10) {
			generateTerrain(delta, true);
		}

		if(Mine.mineOnScreen) mine.draw(delta);

		float gradientAlpha = Math.min(globalTimer/3f, 1f) - HvlMath.mapl(player.flareTimer, 0f, Flare.FLARE_LIFETIME/4f, 0, 0.3f);
		hvlDrawQuadc(player.getX(), Display.getHeight()/2, 1200, 1200, Main.getTexture(Main.INDEX_GRADIENT), new Color(1f, 1f, 1f, gradientAlpha));
		//hvlDrawQuadc(player.getX(), yCrossMites, 20,20, Color.blue);
		//hvlDrawQuadc(player.getX(), yCrossTites, 20,20, Color.blue);
		if(Mine.mineOnScreen) mine.drawLight(delta);
System.out.println(globalTimer % 1);
		player.update(delta);
		if(!player.getInvincibleState()) {
		player.draw(delta);


		}else{
			if(globalTimer % 0.1 > 0.05) {
			player.draw(delta);
			}
		}
		
		PowerUp.update(delta);
		mine.update(delta);
		if(globalTimer > timeStage1Start) {
			stage = 1;
			if(!level1Changed) {
				level1Changed = true;
			}
		}
		if(globalTimer > timeStage2Start) {
			stage = 2;
			if(!level2Changed) {
				level2Changed = true;
			}

		}
		if(globalTimer > timeStage3Start) {
			stage = 3;
			if(!level3Changed) {
				level3Changed = true;
			}

		}
		if(globalTimer > timeStage4Start) {
			stage = 4;
			if(!level4Changed) {
				level4Changed = true;
			}

		}
		if(globalTimer > timeStage5Start) {
			stage = 5;
			if(!level5Changed) {
				level5Changed = true;
			}
		}
		if(globalTimer > timeStage1Start && globalTimer < 1+10 ||globalTimer > timeStage1End && globalTimer < timeStage2Start +10
				|| globalTimer > timeStage2End && globalTimer < timeStage3Start + 10
				|| globalTimer > timeStage3End && globalTimer < timeStage4Start + 10
				|| globalTimer > timeStage4End && globalTimer < timeStage5Start + 10) {
			Main.font.drawWord("Stage "+stage, textX, 100, Color.white);
		}
		else {
			textX = 1580;
		}
		if(globalTimer > timeStage5End && globalTimer < endlessBegin) {
			Main.font.drawWordc("Base stages complete.", Display.getWidth()/2, Display.getHeight()*3/8, Color.white, 0.3f);
			Main.font.drawWordc("Continue onward to begin Endless mode -->", Display.getWidth()/2, Display.getHeight()*4/8, Color.white, 0.3f);
		}
		if(PowerUp.powerUpOnScreen) {
			powerUp.draw(delta);

			//powerUp.setxPos(powerUp.getxPos() - (PowerUp.POWERUP_SPEED * delta);

			powerUp.setxPos(HvlMath.stepTowards(powerUp.getxPos(), PowerUp.POWERUP_SPEED * delta, 854));

			//if(powerUp.getxPos() <= 854) {
			//	powerUp.setxPos(854);
			//}
			if(player.getY() >= powerUp.getyPos() - (PowerUp.POWERUP_SIZE/2) - (Player.PLAYER_SIZE/2) &&
					player.getY() <= powerUp.getyPos() + (PowerUp.POWERUP_SIZE/2) + (Player.PLAYER_SIZE/2) &&
					player.getX() >= powerUp.getxPos() - (PowerUp.POWERUP_SIZE/2) - (Player.PLAYER_SIZE/2) &&
					player.getX() <= powerUp.getxPos() + (PowerUp.POWERUP_SIZE/2) + (Player.PLAYER_SIZE/2)) {
				powerUpTextX = powerUp.getxPos();
				powerUpTextY = powerUp.getyPos();
				powerUp.setxPos(PowerUp.powerUpSpawnX);
				powerUp.setyPos(PowerUp.powerUpSpawnY);
				powerUpPickup = true;
				PowerUp.powerUpOnScreen = false;

			}



			if(powerUpPickup) {
				if(player.getHealth() < Player.MAX_HEALTH && scorePickup == false && flarePickup == false && bigScorePickup == false  && PowerUp.powerUpType == 1) {
					healthPickup = true;
				}
				if((player.getHealth() == Player.MAX_HEALTH && healthPickup == false && flarePickup == false && bigScorePickup == false && PowerUp.powerUpType == 1)) {
					scorePickup = true;
				}
				if(player.hasFlare && healthPickup == false && scorePickup == false && flarePickup == false && PowerUp.powerUpType == 2) {
					bigScorePickup = true;
				}
				if(!player.hasFlare && healthPickup == false && scorePickup == false && bigScorePickup == false && PowerUp.powerUpType == 2) {
					flarePickup = true;
				}
			}
		}

		if(flarePickup) {

			Main.font.drawWordc("+1 Flare", powerUpTextX, powerUpTextY, new Color(255, 255, 255, powerUpTextOpacity), 0.12f);

			if(powerUpHeightCheck) {
				powerUpTextCurrentHeight = powerUpTextY;
				powerUpHeightCheck = false;
			}

			if(powerUpHasGivenFlare) {
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_PICKUP).playAsSoundEffect(1, 0.35f, false);
				player.hasFlare = true;
				powerUpHasGivenFlare = false;
			}

			if(powerUpTextY > powerUpTextCurrentHeight - 20) {
				powerUpTextY = HvlMath.stepTowards(powerUpTextY, delta*20, powerUpTextCurrentHeight - 20);
				powerUpTextOpacity = HvlMath.stepTowards(powerUpTextOpacity, delta, 0);
			}else {
				powerUpTextY = 1000;
				flarePickup = false;
				powerUpHeightCheck = true;
				powerUpPickup = false;
				powerUpHasGivenFlare = true;
				powerUpTextOpacity = 1;
			}
		}


		if(bigScorePickup) {

			Main.font.drawWordc("+3000 Pts", powerUpTextX, powerUpTextY, new Color(255, 255, 255, powerUpTextOpacity), 0.12f);

			if(powerUpHeightCheck) {
				powerUpTextCurrentHeight = powerUpTextY;
				powerUpHeightCheck = false;
			}

			if(powerUpHasGivenBigScore) {
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_PICKUP).playAsSoundEffect(1, 0.35f, false);
				player.setScore(player.getScore() + 3000);
				powerUpHasGivenBigScore = false;
			}

			if(powerUpTextY > powerUpTextCurrentHeight - 20) {
				powerUpTextY = HvlMath.stepTowards(powerUpTextY, delta*20, powerUpTextCurrentHeight - 20);
				powerUpTextOpacity = HvlMath.stepTowards(powerUpTextOpacity, delta, 0);
			}else {
				powerUpTextY = 1000;
				bigScorePickup = false;
				powerUpHeightCheck = true;
				powerUpPickup = false;
				powerUpHasGivenBigScore = true;
				powerUpTextOpacity = 1;
			}
		}


		if(healthPickup) {

			Main.font.drawWordc("+1 HP", powerUpTextX, powerUpTextY, new Color(255, 255, 255, powerUpTextOpacity), 0.12f);

			if(powerUpHeightCheck) {
				powerUpTextCurrentHeight = powerUpTextY;
				powerUpHeightCheck = false;
			}

			if(player.getHealth() < Player.MAX_HEALTH && powerUpHasGivenHealth) {
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_PICKUP).playAsSoundEffect(1, 0.35f, false);
				player.setHealth(player.getHealth() + 1);
				powerUpHasGivenHealth = false;
			}

			if(powerUpTextY > powerUpTextCurrentHeight - 20) {
				powerUpTextY = HvlMath.stepTowards(powerUpTextY, delta*20, powerUpTextCurrentHeight - 20);
				powerUpTextOpacity = HvlMath.stepTowards(powerUpTextOpacity, delta, 0);
			}else {
				powerUpTextY = 1000;
				healthPickup = false;
				powerUpHeightCheck = true;
				powerUpPickup = false;
				powerUpHasGivenHealth = true;
				powerUpTextOpacity = 1;
			}
		}


		if(scorePickup) {
			Main.font.drawWordc("+1000 Pts", powerUpTextX, powerUpTextY, new Color(255, 255, 255, powerUpTextOpacity), 0.12f);


			if(powerUpHeightCheck) {
				powerUpTextCurrentHeight = powerUpTextY;
				powerUpHeightCheck = false;
			}

			if(powerUpHasGivenScore) {
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_PICKUP).playAsSoundEffect(1, 0.35f, false);
				player.setScore(player.getScore() + 1000);
				powerUpHasGivenScore = false;
			}

			if(powerUpTextY > powerUpTextCurrentHeight - 20) {
				powerUpTextY = HvlMath.stepTowards(powerUpTextY, delta*20, powerUpTextCurrentHeight - 20);
				powerUpTextOpacity = HvlMath.stepTowards(powerUpTextOpacity, delta, 0);
			}else {
				powerUpTextY = 1000;
				scorePickup = false;
				powerUpHeightCheck = true;
				powerUpPickup = false;
				powerUpHasGivenScore = true;
				powerUpTextOpacity = 1;
			}
		}

		if(Mine.mineOnScreen && stage >= 3) {
			mine.beepVolume = HvlMath.map(HvlMath.distance(player.getX(), player.getY(), mine.getxPos(), mine.getyPos()), 0, 400, 0.25f, 0f);
			mine.beepVolume = HvlMath.limit(mine.beepVolume, 0f, 0.25f);
			mine.beepTimer += delta * (Display.getWidth() - HvlMath.distance(player.getX(), player.getY(), mine.getxPos(), mine.getyPos())) * 0.005f;
			mine.setxPos(HvlMath.stepTowards(mine.getxPos(), Mine.MINE_SPEED * delta, -50));
			if(HvlMath.distance(player.getX(), player.getY(), mine.getxPos(), mine.getyPos()) < Mine.MINE_RANGE) {
				player.hitMine = true;

				HvlCoord2D knockback = new HvlCoord2D(player.getX() - mine.getxPos(), player.getY() - mine.getyPos());
				knockback.normalize();
				knockback.mult(1000f);
				player.impartedMomentum = new HvlCoord2D(knockback);

				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_BOOM).playAsSoundEffect(1, 0.8f, false);
				wave = new Shockwave(mine.getxPos(), mine.getyPos());
				spawnedWave = true;
				mine.setxPos(Mine.MINE_START_LOC_X);
				mine.setyPos(Mine.mineSpawnY);
				Mine.mineOnScreen = false;
			}

			if(mine.getxPos() < -25) {
				mine.setxPos(Mine.MINE_START_LOC_X);
				mine.setyPos(Mine.mineSpawnY);
				Mine.mineOnScreen = false;
			}
		}


		if(spawnedWave) {
			wave.update(delta);
		}


		ArrayList<Flare.Smoke> smokeCleanup = new ArrayList<>();
		for(Flare.Smoke s : Flare.Smoke.smokeParticles){
			s.update(delta);
			if(s.life == 0) smokeCleanup.add(s);
		}
		for(Flare.Smoke s : smokeCleanup) Flare.Smoke.smokeParticles.remove(s);

		for(Flare f : flares){
			f.update(delta);
		}

		ArrayList<LineSegment> lsCleanup = new ArrayList<>();
		for(LineSegment miteWave : mites) {
			miteWave.start.y += Shockwave.displacementY;
			miteWave.end.y += Shockwave.displacementY;
			miteWave.drawError(delta);
			if(miteWave.isOffscreen()) lsCleanup.add(miteWave);
		}
		for(LineSegment titeWave : tites) {
			titeWave.start.y += Shockwave.displacementY;
			titeWave.end.y += Shockwave.displacementY;
			titeWave.drawError(delta);
			if(titeWave.isOffscreen()) lsCleanup.add(titeWave);
		}
		for(LineSegment s : lsCleanup){
			if(mites.contains(s)) mites.remove(s);
			if(tites.contains(s)) tites.remove(s);
		}
		

		if(stage == 4) {
			TERRAIN_MAX_TIGHTNESS = 60f;
		}
		if(stage == 5) {
			TERRAIN_MAX_TIGHTNESS = 45f;
		}

		
		//Draw the score
		if(player.getHealth() > 0) {
		Main.font.drawWord("SCORE: " + (int)(Math.round(player.getScore())), Player.HEALTHBAR_X+2, 15+2, Color.black, 0.17f);

		Main.font.drawWord("SCORE: " + (int)(Math.round(player.getScore())), Player.HEALTHBAR_X, 15, Color.white, 0.17f);
		}
		

	}

	public static void restart(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
		powerUp = new PowerUp(PowerUp.powerUpSpawnX, PowerUp.powerUpSpawnY);
		tites.clear();
		mites.clear();
		flares.clear();
		Shockwave.displacementY = 0;
		textX = 100;
		level1Changed = false;
		level2Changed = false;
		level3Changed = false;
		level4Changed = false;
		level5Changed = false;
		TERRAIN_MAX_TIGHTNESS = 150f;
		Flare.Smoke.smokeParticles.clear();
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

		lineEnd.y = TERRAIN_BUFFER;
		lineEnd.x = HvlMath.randomFloatBetween(Display.getWidth() + TERRAIN_MIN_WIDTH, Display.getWidth() + TERRAIN_MAX_WIDTH);
		if(tite){
			lineEnd.y = HvlMath.randomFloatBetween(TERRAIN_BUFFER, terrainCenter - terrainTightness);
		}else{
			lineEnd.y = HvlMath.randomFloatBetween(Display.getHeight() - TERRAIN_BUFFER, terrainCenter + terrainTightness);
		}
		if(globalTimer > timeStage1Start && globalTimer < 1 ||globalTimer > timeStage1End && globalTimer < timeStage2Start 
				|| globalTimer > timeStage2End && globalTimer < timeStage3Start
				|| globalTimer > timeStage3End && globalTimer < timeStage4Start
				|| globalTimer > timeStage4End && globalTimer < timeStage5Start) {
			
			if(!tite) {
				lineEnd.y = Display.getHeight()-TERRAIN_BUFFER;
			}else {
				lineEnd.y = TERRAIN_BUFFER;
			}
		}
		if(globalTimer > timeStage5End && globalTimer < endlessBegin) {
			if(!tite) {
				lineEnd.y = Display.getHeight()-TERRAIN_BUFFER;
			}else {
				lineEnd.y = TERRAIN_BUFFER;
			}
		}
		LineSegment line = new LineSegment(lineStart, lineEnd, tite);
		if(tite){
			tites.add(line); 
		}else{
			mites.add(line);
		}
		
	}

	public static boolean getLivingState() {

		if(player.getHealth() == 0) {
			return false;
		}else {
			return true;
		}


	}




}
