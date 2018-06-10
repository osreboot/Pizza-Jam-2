package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Particle {

	public HvlCoord2D loc;
	public float xl, yl, xs, ys, direction;
	public Color c;
	
	public Particle(float xArg, float yArg, float xlArg, float ylArg, float xsArg, float ysArg, Color cArg){
		loc = new HvlCoord2D(xArg, yArg);
		xl = xlArg;
		yl = ylArg;
		xs = xsArg;
		ys = ysArg;
		c = cArg;
	}
	
	public void update(float delta){
		loc.x += xs * delta;
		loc.y += ys * delta;
		hvlRotate(loc, direction);
		hvlDrawQuadc(loc.x, loc.y, xl, yl, c);
		hvlResetRotation();
		if(loc.x < -xl - yl){
			loc.x = xl + yl + 1280;
			loc.y = HvlMath.randomFloatBetween(Game.TERRAIN_BUFFER, 720 - Game.TERRAIN_BUFFER);
		}
	}
	
}
