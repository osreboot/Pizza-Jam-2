package com.hyprgloo.pizzajam2;

import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.HvlRenderFrame;
import com.osreboot.ridhvl.painter.HvlRenderFrame.FBOUnsupportedException;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String[] args){
		new Main();
	}

	public static final int
	INDEX_FONT = 0,
	INDEX_HYPRGLOO = 1,
	INDEX_GRADIENT = 2,
	INDEX_TERRAIN_GRADIENT = 3,
	INDEX_POWERUP_HEALTH = 4;

	public static HvlFontPainter2D font;

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

		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		font.setCharSpacing(16f);

		MenuManager.initialize();
		try{
			MenuManager.pauseFrame = new HvlRenderFrame(Display.getWidth(), Display.getHeight());
		}catch(FBOUnsupportedException e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(float delta){
		MenuManager.update(delta);
	}

}
