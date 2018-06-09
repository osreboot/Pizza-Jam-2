package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Shockwave{
	public HvlCoord2D loc;
	public float size;
	public float opacity;
	public float occilation;
	public static float displacementY = 0;
	public float direction = 1;
	public Shockwave(float xPos, float yPos) {
		loc = new HvlCoord2D(xPos, yPos);
		size = 0;
		opacity = 1;
		displacementY = 0;
		occilation = 5;
	}
	public void update(float delta) {
		size = HvlMath.stepTowards(size, delta*250, 500);
		opacity = HvlMath.stepTowards(opacity, delta/2, 0);
		Game.playerErrorTimer = HvlMath.stepTowards(opacity, delta/2, 0) - 0.6f;
		hvlDrawQuadc(loc.x, loc.y, size,size, Main.getTexture(Main.INDEX_SHOCKWAVE), new Color(255f, 255f, 255f, opacity));
		if(size > 490 && opacity < 0.01) {
			Game.spawnedWave = false;
			displacementY = 0;
		}else {
			occilation = HvlMath.stepTowards(occilation, delta*2.5f, 0);
			displacementY += direction *occilation;
			if(displacementY >= occilation) {
				direction = -1;
			}
			if(displacementY <= -occilation) {
				direction = 1;
			}
		}
	}
}
