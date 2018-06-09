package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlResetRotation;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlRotate;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Flare {
	
	public static final float FLARE_LIFETIME = 4f;
	
	public HvlCoord2D loc;
	public float xs, ys, smokeEmitTimer, life;
	
	public Flare(float xArg, float yArg, float xsArg, float ysArg){
		loc = new HvlCoord2D(xArg, yArg);
		xs = xsArg;
		ys = ysArg;
		smokeEmitTimer = 0f;
		Game.flares.add(this);
		life = FLARE_LIFETIME;
	}
	
	public void update(float delta){
		smokeEmitTimer += delta;
		if(smokeEmitTimer > 0.1f && life > 2f){
			new Smoke(loc.x, loc.y, 32f, 0f);
			smokeEmitTimer = 0f;
		}
		life = HvlMath.stepTowards(life, delta, 0f);
		xs -= delta*10f;
		xs = HvlMath.limit(xs, -64, 0);
		ys = HvlMath.stepTowards(ys, delta*20f, 0f);
		loc.x += xs*delta;
		loc.y += ys*delta;
		hvlDrawQuadc(loc.x, loc.y, 128, 128, Main.getTexture(Main.INDEX_FLARE), new Color(1f, 1f, 1f, HvlMath.randomFloatBetween(0.2f, 0.9f) * (life/FLARE_LIFETIME)));
	}
	
	public static class Smoke{
		public static ArrayList<Smoke> smokeParticles = new ArrayList<>();
		
		public HvlCoord2D loc;
		public float size, life, rotation, xs;
		public Smoke(float xArg, float yArg, float sizeArg, float xsArg){
			loc = new HvlCoord2D(xArg, yArg);
			size = sizeArg;
			life = 1f;
			rotation = HvlMath.randomFloatBetween(0f, 360f);
			xs = xsArg;
			smokeParticles.add(this);
		}
		public void update(float delta){
			loc.x += xs*delta;
			life = HvlMath.stepTowards(life, delta, 0f);
			hvlRotate(loc, rotation);
			hvlDrawQuadc(loc.x, loc.y, size * HvlMath.map(life, 1f, 0, 0.5f, 2f), size * HvlMath.map(life, 1f, 0, 0.5f, 2f), Main.getTexture(Main.INDEX_SMOKE), new Color(1f, 1f, 1f, life/1.5f));
			hvlResetRotation();
		}
	}
	
}
