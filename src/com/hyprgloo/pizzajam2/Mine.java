package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import com.osreboot.ridhvl.HvlMath;

public class Mine {
	public static final float MINE_SIZE = 20;
	public static final float MINE_RANGE = 80;
	public static final float MINE_START_LOC_X = 1440;
	public static final int MINE_CHANCE = 100;

	public static final int MINE_SPEED = 150;
	public static float mineSpawnY = HvlMath.randomFloatBetween(200, 520);
	public static boolean mineOnScreen;

	private float xPos;
	private float yPos;
	private float rotation = 0;

	private static int random = HvlMath.randomInt(MINE_CHANCE+1);

	public Mine(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public float beepTimer = 0f;
	public float beepVolume = 0f;
	private boolean beeped = false;
	public void update(float delta) {
		mineSpawnY = HvlMath.randomFloatBetween(200, 520);

		if(!mineOnScreen) {
			beepVolume = 0f;
			random = HvlMath.randomInt(MINE_CHANCE+1);
		}
		if(random == MINE_CHANCE) {
			mineOnScreen = true;
		}
	}
	public float getxPos() {
		return this.xPos;
	}

	public void setxPos(float xArg) {
		this.xPos = xArg;
	}

	public float getyPos() {
		return this.yPos;
	}

	public void setyPos(float yArg) {
		this.yPos = yArg;
	}
	public void draw(float delta) {
		rotation += delta*10;
		hvlRotate(xPos, yPos, rotation);
		hvlDrawQuadc(xPos, yPos, MINE_SIZE, MINE_SIZE, Main.getTexture(Main.INDEX_MINE));
		hvlResetRotation();
	}

	public void drawLight(float delta){
		if(mineOnScreen && beepTimer % 1f > 0.5f){
			if(!beeped){
				if(Main.settings.soundEnabled) Main.getSound(Main.INDEX_BEEP).playAsSoundEffect(1, beepVolume, false);
				beeped = true;
			}
			hvlRotate(xPos, yPos, rotation);
			hvlDrawQuadc(xPos, yPos, MINE_SIZE, MINE_SIZE, Main.getTexture(Main.INDEX_MINE_LIGHT));
			hvlResetRotation();
		}else beeped = false;
	}


}
