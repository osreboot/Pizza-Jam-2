package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction0r;
import com.osreboot.ridhvl.menu.HvlMenu;

public class TutorialText {

	public static ArrayList<TutorialText> tutorial = new ArrayList<>();
	public static int textIndex = 0;
	public static TutorialText current = null;

	public static void initialize(){
		if(!Main.settings.tut1Complete) 
			new TutorialText(
					new HvlCoord2D[]{new HvlCoord2D(Display.getWidth()/2, Display.getHeight()/2)}, 
					new String[]{"WASD to move"}, 
					new HvlAction0r<Boolean>(){
				@Override
				public Boolean run() {
					return Game.globalTimer > 1f;
				}
			}, new HvlAction0(){
				@Override
				public void run(){
					Main.settings.tut1Complete = true;
				}
			});
		if(!Main.settings.tut2Complete) 
			new TutorialText(new HvlCoord2D[]{
					new HvlCoord2D(Display.getWidth()*2/8, Display.getHeight()*13/16),
					new HvlCoord2D(Display.getWidth()*5/8, Display.getHeight()/2)},
			new String[]{"collect it to restore health", "health just appeared -->"}, 
			new HvlAction0r<Boolean>(){
				@Override
				public Boolean run() {
					return PowerUp.powerUpType == 1 && Game.powerUp.getxPos() < 1150;
				}
			}, new HvlAction0(){
				@Override
				public void run(){
					Main.settings.tut2Complete = true;
				}
			});
		if(!Main.settings.tut3Complete) 
			new TutorialText(new HvlCoord2D[]{
					new HvlCoord2D(Display.getWidth()/2, Display.getHeight()/2),
					new HvlCoord2D(Player.FLARE_INV_X + 32, Player.FLARE_INV_Y - 64),
					new HvlCoord2D(Display.getWidth()*3/8, Display.getHeight()*13/16),
					new HvlCoord2D(Display.getWidth()*5/8, Display.getHeight()/2)},
			new String[]{"press [space] to deploy a flare", "\\/", "the icon indicates you have a flare", "a flare just appeared -->"}, 
			new HvlAction0r<Boolean>(){
				@Override
				public Boolean run() {
					return PowerUp.powerUpType == 2 && Game.powerUp.getxPos() < 1150;
				}
			}, new HvlAction0(){
				@Override
				public void run(){
					Main.settings.tut3Complete = true;
				}
			});
		if(!Main.settings.tut4Complete) 
			new TutorialText(new HvlCoord2D[]{
					new HvlCoord2D(Display.getWidth()/2, Display.getHeight()/2),
					new HvlCoord2D(Display.getWidth()*3/4, Display.getHeight()/2)},
			new String[]{"watch the terrain, don't forget how to navigate it!", "it's getting dark..."}, 
			new HvlAction0r<Boolean>(){
				@Override
				public Boolean run() {
					return Game.globalTimer > 3f;
				}
			}, new HvlAction0(){
				@Override
				public void run(){
					Main.settings.tut4Complete = true;
				}
			});
		if(!Main.settings.tut5Complete) 
			new TutorialText(new HvlCoord2D[]{
					new HvlCoord2D(Display.getWidth()*4/8, Display.getHeight()*3/4),
					new HvlCoord2D(Display.getWidth()*4/8, Display.getHeight()/4),
					new HvlCoord2D(Display.getWidth()*5/8, Display.getHeight()/2),
					new HvlCoord2D(Display.getWidth()*5/8, Display.getHeight()/2)},
			new String[]{"...however they'll launch you into walls, be careful!", "mines don't do health damage", "it'll explode if you get close", "a mine just appeared -->"}, 
			new HvlAction0r<Boolean>(){
				@Override
				public Boolean run() {
					return Mine.mineOnScreen && Game.mine.getxPos() < 1150;
				}
			}, new HvlAction0(){
				@Override
				public void run(){
					Main.settings.tut5Complete = true;
				}
			});
	}

	public static void update(float delta){
		if(current != null){
			if(textIndex > 0){
				current.display(delta);
			}else{
				current.confirm.run();
				tutorial.remove(current);
				current = null;
				HvlMenu.setCurrent(MenuManager.game);
			}
		}else{
			if(Main.settings.tutorialsEnabled){
				for(TutorialText t : tutorial){
					if(t.spawnCheck.run()){
						textIndex = t.text.length;
						current = t;
					}
				}
			}
		}
	}

	public HvlCoord2D[] locs;
	public String[] text;
	public HvlAction0r<Boolean> spawnCheck;
	public HvlAction0 confirm;

	public TutorialText(HvlCoord2D[] locArgs, String[] textArg, HvlAction0r<Boolean> spawnCheckArg, HvlAction0 confirmArg){
		locs = locArgs;
		text = textArg;
		spawnCheck = spawnCheckArg;
		confirm = confirmArg;
		tutorial.add(this);
	}

	public void display(float delta){
		hvlDrawQuadc(locs[textIndex - 1].x, locs[textIndex - 1].y - 4f, Main.tutorialFont.getLineWidth(text[textIndex - 1]) + 36f, 68f, new Color(0f, 0f, 0.8f));
		hvlDrawQuadc(locs[textIndex - 1].x, locs[textIndex - 1].y - 4f, Main.tutorialFont.getLineWidth(text[textIndex - 1]) + 32f, 64f, Color.blue);
		Main.font.drawWordc(text[textIndex - 1], locs[textIndex - 1].x, locs[textIndex - 1].y, Color.white, 0.25f);
		Main.font.drawWordc("click to continue...", locs[textIndex - 1].x, locs[textIndex - 1].y + 48, Color.white, 0.125f);
	}

}
