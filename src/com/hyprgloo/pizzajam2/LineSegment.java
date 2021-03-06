package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawLine;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawPolygon;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMatrix2D;

public class LineSegment {
	
	public static HvlMatrix2D TERRAIN_MATRIX;
	
	public static final HvlCoord2D[] UVS_UNIT = new HvlCoord2D[]{
			new HvlCoord2D(0, 0),
			new HvlCoord2D(1, 0),
			new HvlCoord2D(1, 1),
			new HvlCoord2D(0, 1),
	};
	
	public HvlCoord2D start;
	public HvlCoord2D end;
	public boolean tite;

	public LineSegment(HvlCoord2D start, HvlCoord2D end, boolean tite){
		this.start = start;
		this.end = end;
		this.tite = tite;
	}

	public void draw(float delta){
		hvlDrawPolygon(0, 0, getSegmentPolygon(this, 1f, false), getSegmentPolygon(this, 1/512f, true), Main.getTexture(Main.INDEX_TERRAIN));
		hvlDrawPolygon(0, 0, getSegmentPolygon(this, 1f, false), UVS_UNIT, Main.getTexture(Main.INDEX_TERRAIN_GRADIENT), new Color(1f, 1f, 1f, 0.35f));
	}
	
	public void drawError(float delta){
		hvlDrawLine(this.start.x, this.start.y, this.end.x, this.end.y, new Color(1f, 0f, 0f, Game.playerErrorTimer), 2f);
	}
	
	public boolean isOffscreen(){
		return this.end.x < 0;
	}

	private static HvlCoord2D[] getSegmentPolygon(LineSegment sArg, float scale, boolean scroll){
		HvlCoord2D[] output;
		if(sArg.tite){
			output = new HvlCoord2D[]{
					new HvlCoord2D(sArg.start.x, 0),
					new HvlCoord2D(sArg.end.x, 0),
					new HvlCoord2D(sArg.end.x, sArg.end.y),
					new HvlCoord2D(sArg.start.x, sArg.start.y),
			};
		}else{
			output = new HvlCoord2D[]{
					new HvlCoord2D(sArg.start.x, Display.getHeight()),
					new HvlCoord2D(sArg.end.x, Display.getHeight()),
					new HvlCoord2D(sArg.end.x, sArg.end.y),
					new HvlCoord2D(sArg.start.x, sArg.start.y),
			};
		}
		if(scroll){
			for(int i = 0; i < output.length; i++)
				output[i].add(Game.SCROLLSPEED*Game.globalTimer, 0).mult(scale);
		}
		return output;
	}
	public float getStartX() {
		return this.start.x;
	}
	public float getStartY() {
		return this.start.y;
	}
	public float getEndX() {
		return this.end.x;
	}
	public float getEndY() {
		return this.end.y;
	}

}
