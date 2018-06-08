package com.hyprgloo.pizzajam2;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String[] args){
		new Main();
	}
	
	public static final int
	INDEX_FONT = 0;
	
	public static HvlFontPainter2D font;
	
	public Main(){
		super(144, 1280, 720, "Pizza Jam 2 : HYPRGLOO Submission", new HvlDisplayModeDefault());
	}

	@Override
	public void initialize(){
		getTextureLoader().loadResource("INOF");
		
		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		font.setCharSpacing(16f);
		
		Game.initialize();
	}

	@Override
	public void update(float delta){
		font.drawWordc("GAME", Display.getWidth()/2, Display.getHeight()/2, Color.white);
		
		Game.update(delta);
	}

}
