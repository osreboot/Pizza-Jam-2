package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Flare {
	
	public static final float FLARE_LIFETIME = 5f;
	
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
		if(smokeEmitTimer > 0.1f){
			new Smoke(loc.x, loc.y, 32f);
		}
		life = HvlMath.stepTowards(life, delta, 0);
		xs -= delta*10f;
		xs = HvlMath.limit(xs, -64, 0);
		ys = HvlMath.stepTowards(ys, delta*10f, 0f);
		loc.x += xs*delta;
		loc.y += ys*delta;
		hvlDrawQuadc(loc.x, loc.y, 128, 128, Main.getTexture(Main.INDEX_FLARE), new Color(1f, 1f, 1f, HvlMath.randomFloatBetween(0.2f, 0.8f) * (life/FLARE_LIFETIME)));
	}
	
	public static class Smoke{
		public static ArrayList<Smoke> smokeParticles = new ArrayList<>();
		
		public HvlCoord2D loc;
		public float size;
		public Smoke(float xArg, float yArg, float sizeArg){
			loc = new HvlCoord2D(xArg, yArg);
			size = sizeArg;
			smokeParticles.add(this);
		}
		public void update(float delta){
			
		}
	}
	
}
