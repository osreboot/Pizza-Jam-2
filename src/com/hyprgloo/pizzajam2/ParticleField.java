package com.hyprgloo.pizzajam2;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;

public class ParticleField {

	public static ArrayList<Particle> particles;

	public static void initialize(){
		particles = new ArrayList<>();
		for(int i = 0; i < 100; i++){
			float size = HvlMath.randomFloatBetween(1f, 4f);
			float alpha = HvlMath.map(size, 1f, 5f, 0.15f, 0.4f);
			boolean blue = HvlMath.randomInt(100) > 85;
			particles.add(new Particle(HvlMath.randomFloatBetween(0, 1280), HvlMath.randomFloatBetween(Game.TERRAIN_BUFFER, 720 - Game.TERRAIN_BUFFER),
					size, size, -size * 40f, 0f, new Color(alpha, alpha, blue ? 0.8f : alpha)));
		}
	}

	public static void update(float delta){
		for(Particle p : particles)
			p.update(delta);
	}

}
