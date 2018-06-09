package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawPolygon;

import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl.HvlCoord2D;

public class LineSegment {

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
		hvlDrawPolygon(0, 0, getSegmentPolygon(this), UVS_UNIT, Main.getTexture(Main.INDEX_TERRAIN_GRADIENT));
		//HvlPainter2D.hvlDrawLine(this.start.x, this.start.y, this.end.x, this.end.y, Color.white);
	}

	private static HvlCoord2D[] getSegmentPolygon(LineSegment sArg){
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
