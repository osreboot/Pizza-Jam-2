package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawPolygon;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;

public class MenuManager {

	public static final float 
	BUTTON_WIDTH = 256f,
	BUTTON_HEIGHT = 96f;
	
	public static final HvlCoord2D[] POLYGON_BUTTON = new HvlCoord2D[]{
			new HvlCoord2D(0, 0),
			new HvlCoord2D(BUTTON_WIDTH, 0),
			new HvlCoord2D(BUTTON_WIDTH - (BUTTON_HEIGHT/2), BUTTON_HEIGHT),
			new HvlCoord2D(0, BUTTON_HEIGHT),
	};
	
	public static HvlMenu intro, main, game;
	
	public static void initialize(){
		HvlComponentDefault.setDefault(new HvlArrangerBox(Display.getWidth(), Display.getHeight(), HvlArrangerBox.ArrangementStyle.HORIZONTAL));
		HvlLabeledButton defaultLabeledButton = new HvlLabeledButton(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawPolygon(xArg, yArg, POLYGON_BUTTON, Color.blue);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawPolygon(xArg, yArg, POLYGON_BUTTON, Color.blue);
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				hvlDrawPolygon(xArg, yArg, POLYGON_BUTTON, Color.blue);
			}
		}, Main.font, "NOTEXT", Color.white);
		defaultLabeledButton.setTextScale(0.2f);
		HvlComponentDefault.setDefault(defaultLabeledButton);
		
		intro = new HvlMenu();
		
		main = new HvlMenu();
		main.add(new HvlArrangerBox.Builder().setxAlign(0f).build());
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.initialize();
				HvlMenu.setCurrent(game);
			}
		}).build());
		
		game = new HvlMenu();
		
		HvlMenu.setCurrent(intro);
	}
	
	private static float introProgress = 0f;
	
	public static void update(float delta){
		if(HvlMenu.getCurrent() == intro){
			//UPDATING THE INTRO MENU//
			introProgress += delta/4f;
			if(introProgress >= 1f || (introProgress > 0.25f && Mouse.isButtonDown(0))) HvlMenu.setCurrent(main);
			float alpha = 1f - (Math.abs(introProgress - 0.5f)*2f);
			hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/2, 512, 512, Main.getTexture(Main.INDEX_HYPRGLOO), new Color(1f, 1f, 1f, alpha));
			//Main.font.drawWordc("INTRO", Display.getWidth()/2, Display.getHeight()/2, new Color(1f, 1f, 1f, alpha));
		}else if(HvlMenu.getCurrent() == main){
			//UPDATING THE MAIN MENU//
			Main.font.drawWordc("MAIN MENU", Display.getWidth()/2, Display.getHeight()/2, Color.white);
		}else if(HvlMenu.getCurrent() == game){
			//UPDATING THE GAME//
			Game.update(delta);
		}
		
		HvlMenu.updateMenus(delta);
	}
	
	/*private static HvlCoord2D[] getTransformedButtonPolygon(){
		HvlCoord2D[] output = new HvlCoord2D[]{
				new HvlCoord2D(0, 0),
				new HvlCoord2D(BUTTON_WIDTH, 0),
				new HvlCoord2D(BUTTON_WIDTH - (BUTTON_HEIGHT/2), BUTTON_HEIGHT),
				new HvlCoord2D(0, BUTTON_HEIGHT),
		};
	}*/
	
}
