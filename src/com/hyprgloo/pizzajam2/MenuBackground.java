package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuada;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class MenuBackground {

	private static ArrayList<MenuBackground> backgrounds = new ArrayList<>();
	
	private static MenuBackground current;
	private static int currentIndex = 0;
	public static float progress = 0f;
	
	public static void initialize(){
		new MenuBackground(Main.INDEX_MENU_PAN1, new HvlCoord2D(1280/2, 720/2 + 500), new HvlCoord2D(2048 - (1280/2), 2048 - (720/2) - 500));
		current = backgrounds.get(0);
	}
	
	public static void update(float delta){
		progress = HvlMath.stepTowards(progress, delta/10f, 1f);
		if(progress == 1f){
			progress = 0f;
			currentIndex = currentIndex + 1 >= backgrounds.size() ? 0 : currentIndex + 1;
			current = backgrounds.get(currentIndex);
		}
		current.drawBackground(progress);
		hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), 
				new Color(0f, 0f, 0f, 1f - HvlMath.mapl(progress, 0f, 0.1f, 0f, 1f) + HvlMath.mapl(progress, 0.9f, 1f, 0f, 1f)));
		hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), new Color(0f, 0f, 0f, 0.5f));
	}
	
	public static void refresh(){
		progress = 0;
		currentIndex = HvlMath.randomInt(backgrounds.size());
		current = backgrounds.get(currentIndex);
	}
	
	private int texture;
	private HvlCoord2D start, end;
	
	public MenuBackground(int textureArg, HvlCoord2D startArg, HvlCoord2D endArg){
		texture = textureArg;
		start = startArg;
		end = endArg;
		backgrounds.add(this);
	}
	
	public void drawBackground(float progress){
		hvlDrawQuada(-HvlMath.lerp(start, end, progress).x, -HvlMath.lerp(start, end, progress).y, 2048, 2048, Main.getTexture(texture));
	}
	
}
