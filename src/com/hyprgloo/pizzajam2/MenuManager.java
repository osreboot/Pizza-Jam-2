package com.hyprgloo.pizzajam2;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;

public class MenuManager {

	public static HvlMenu intro, main, game;
	
	public static void initialize(){
		HvlComponentDefault.setDefault(new HvlArrangerBox(Display.getWidth(), Display.getHeight(), HvlArrangerBox.ArrangementStyle.HORIZONTAL));
		intro = new HvlMenu();
		
		main = new HvlMenu();
		main.add(new HvlArrangerBox.Builder().build());
		
		game = new HvlMenu();
		
		HvlMenu.setCurrent(intro);
	}
	
	private static float introProgress = 0f;
	
	public static void update(float delta){
		HvlMenu.updateMenus(delta);
		
		if(HvlMenu.getCurrent() == intro){
			introProgress += delta/4f;
			if(introProgress >= 1f || (introProgress > 0.25f && Mouse.isButtonDown(0))) HvlMenu.setCurrent(main);
			float alpha = 1f - (Math.abs(introProgress - 0.5f)*2f);
			Main.font.drawWordc("INTRO", Display.getWidth()/2, Display.getHeight()/2, new Color(1f, 1f, 1f, alpha));
		}else if(HvlMenu.getCurrent() == main){
			Main.font.drawWordc("MAIN MENU", Display.getWidth()/2, Display.getHeight()/2, Color.white);
			HvlMenu.setCurrent(game);
			Game.initialize();
		}else if(HvlMenu.getCurrent() == game){
			Game.update(delta);
		}
	}
	
}
