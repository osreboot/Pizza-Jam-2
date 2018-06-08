package com.hyprgloo.pizzajam2;

import org.lwjgl.opengl.Display;

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
	
	public static void update(float delta){
		HvlMenu.updateMenus(delta);
	}
	
}
