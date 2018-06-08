package com.hyprgloo.pizzajam2;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.painter.painter2d.HvlPainter2D;

public class LineSegment {
	
	public HvlCoord2D start;
	public HvlCoord2D end;

	public LineSegment(HvlCoord2D start, HvlCoord2D end){
		this.start = start;
		this.end = end;
	}
	
	public void draw(float delta){
		HvlPainter2D.hvlDrawLine(this.start.x, this.start.y, this.end.x, this.end.y, Color.white);
	}
	
}
