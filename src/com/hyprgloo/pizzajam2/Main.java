package com.hyprgloo.pizzajam2;

import java.io.File;

import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl.config.HvlConfig;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.HvlRenderFrame;
import com.osreboot.ridhvl.painter.HvlRenderFrame.FBOUnsupportedException;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	//TODO Main menu background					L<
	//TODO functional settings page				S		[OS]
	//TODO music								M<		[OS,BASS]
	//TODO sound effects						M<
	//TODO score, calculations					S		[BASS]
	//TODO score, menus							S		[OS]
	//TODO tutorial text						M<<		[OS]
	//TODO player fail state					M<<
	//TODO player win state						M<<
	//TODO stage progression (to win state)		L<<
	//TODO stage progression visuals 			S<<
	//TODO particle fields						M		[OS]
	//TODO game name							S<<<
	//TODO game icon							S<		[OS]
	//TODO ship lighting effects				S
	//TODO main menu fade-in					S		[OS]
	//TODO fuel pickups/bar						L		[BASS]
	
	//TODO Sam Suggestion: change ground gradient to some texture that does not repeat... and maybe make a line outline when using flares: green or white

	public static void main(String[] args){
		new Main();
	}

	public static final int
	INDEX_FONT = 0,
	INDEX_HYPRGLOO = 1,
	INDEX_GRADIENT = 2,
	INDEX_TERRAIN_GRADIENT = 3,
	INDEX_POWERUP_HEALTH = 4,
	INDEX_POWERUP_FUEL = 5,
	INDEX_POWERUP_FLARE = 6,
	INDEX_FLARE = 7,
	INDEX_SMOKE = 8,
	INDEX_SHIP = 9,
	INDEX_FLARE_ICON1 = 10,
	INDEX_FLARE_ICON2 = 11,
	INDEX_MINE = 12,
	INDEX_SHOCKWAVE = 13,
	INDEX_MINE_LIGHT = 14,
	INDEX_MENU_PAN1 = 15,
	INDEX_FLARE_ICON0 = 16,
	INDEX_HEALTH0 = 17,
	INDEX_HEALTH1 = 18,
	INDEX_HEALTH2 = 19,
	INDEX_HEALTH3 = 20,
	INDEX_HEALTH4 = 21;

	public static final int
	INDEX_BEEP = 0,
	INDEX_BOOM = 1,
	INDEX_CRUNCH = 2,
	INDEX_DING = 3;

	public static final String PATH_SETTINGS = "res\\settings.cfg";

	public static HvlFontPainter2D font, tutorialFont;
	public static Settings settings;

	public Main(){
		super(144, 1280, 720, "Pizza Jam 2 : HYPRGLOO Submission", new HvlDisplayModeDefault());
	}

	@Override
	public void initialize(){
		getTextureLoader().loadResource("INOF");
		getTextureLoader().loadResource("HYPRGLOO");
		getTextureLoader().loadResource("Gradient");
		getTextureLoader().loadResource("Gradient2");
		getTextureLoader().loadResource("PowerupHealth");
		getTextureLoader().loadResource("PowerupFuel");
		getTextureLoader().loadResource("PowerupFlare");
		getTextureLoader().loadResource("Flare");
		getTextureLoader().loadResource("Smoke");
		getTextureLoader().loadResource("Ship");
		getTextureLoader().loadResource("FlareIcon1");
		getTextureLoader().loadResource("FlareIcon2");
		getTextureLoader().loadResource("MineOff");
		getTextureLoader().loadResource("Shockwave");
		getTextureLoader().loadResource("MineLight");
		getTextureLoader().loadResource("MenuPan1");
		getTextureLoader().loadResource("FlareIcon0");
		getTextureLoader().loadResource("Health0");
		getTextureLoader().loadResource("Health1");
		getTextureLoader().loadResource("Health2");
		getTextureLoader().loadResource("Health3");
		getTextureLoader().loadResource("Health4");

		getSoundLoader().loadResource("Beep");
		getSoundLoader().loadResource("Boom");
		getSoundLoader().loadResource("Crunch");
		getSoundLoader().loadResource("stageChange3");

		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		font.setCharSpacing(16f);
		
		tutorialFont = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		tutorialFont.setCharSpacing(16f);
		tutorialFont.setScale(0.25f);

		try{
			MenuManager.pauseFrame = new HvlRenderFrame(Display.getWidth(), Display.getHeight());
		}catch(FBOUnsupportedException e){
			e.printStackTrace();
		}
		File config = new File(PATH_SETTINGS);
		if(config.exists()){
			settings = HvlConfig.loadFromFile(PATH_SETTINGS);
		}else{
			HvlConfig.saveToFile(new Settings(), PATH_SETTINGS);
			settings = HvlConfig.loadFromFile(PATH_SETTINGS);
		}
		MenuManager.initialize();
		TutorialText.initialize();
		MenuBackground.initialize();
	}

	@Override
	public void update(float delta){
		MenuManager.update(delta);
	}

	public static void saveConfig(){
		HvlConfig.saveToFile(settings, PATH_SETTINGS);
	}

}
