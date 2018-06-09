package com.hyprgloo.pizzajam2;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawPolygon;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;
import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.menu.HvlButtonMenuLink;
import com.osreboot.ridhvl.menu.HvlComponent;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlSpacer;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;
import com.osreboot.ridhvl.painter.HvlRenderFrame;

public class MenuManager {

	public static final float 
	BUTTON_WIDTH = 256f,
	BUTTON_HEIGHT = 96f;

	public static HvlMenu intro, main, game, pause, settings, credits;
	public static HvlRenderFrame pauseFrame;
	public static HvlInput inputPause;

	private static HashMap<HvlLabeledButton, LabeledButtonAlias> buttonAliases = new HashMap<>();

	public static void initialize(){
		inputPause = new HvlInput(new HvlInput.InputFilter(){
			@Override
			public float getCurrentOutput() {
				return Keyboard.isKeyDown(Keyboard.KEY_P) || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) ? 1f : 0f;
			}
		});
		inputPause.setPressedAction(new HvlAction1<HvlInput>(){
			@Override
			public void run(HvlInput aArg){
				if(HvlMenu.getCurrent() == game) HvlMenu.setCurrent(pause);
				else if(HvlMenu.getCurrent() == pause) HvlMenu.setCurrent(game);
			}
		});

		HvlComponentDefault.setDefault(new HvlArrangerBox(Display.getWidth(), Display.getHeight(), HvlArrangerBox.ArrangementStyle.HORIZONTAL));
		HvlLabeledButton defaultLabeledButton = new HvlLabeledButton(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
			}
		}, Main.font, "NOTEXT", Color.white);
		defaultLabeledButton.setTextScale(0.2f);
		defaultLabeledButton.setUpdateOverride(new HvlAction2<HvlComponent, Float>(){
			@Override
			public void run(HvlComponent aArg, Float delta) {
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				if(!buttonAliases.containsKey(b)){
					buttonAliases.put(b, new LabeledButtonAlias());
				}
				buttonAliases.get(b).update(delta, b.isHovering());;
				LabeledButtonAlias a = buttonAliases.get(b);
				hvlDrawPolygon(b.getX(), b.getY(), getTransformedButtonPolygon(1f, BUTTON_WIDTH + a.widthAdd, BUTTON_HEIGHT), new Color(0f, 0f, 0.4f));
				hvlDrawPolygon(b.getX(), b.getY(), getTransformedButtonPolygon(a.hoverAlpha2, BUTTON_WIDTH + a.widthAdd, BUTTON_HEIGHT), new Color(0f, 0f, 1f, a.hoverAlpha2/2f));
				hvlDrawPolygon(b.getX(), b.getY(), getTransformedButtonPolygon(HvlMath.limit(a.hoverAlpha, 0f, 0.9f), BUTTON_WIDTH + a.widthAdd, BUTTON_HEIGHT), new Color(0f, 0f, 1f, a.hoverAlpha));
				b.update(delta);
			}
		});
		HvlComponentDefault.setDefault(defaultLabeledButton);

		intro = new HvlMenu();
		main = new HvlMenu();
		settings = new HvlMenu();
		credits = new HvlMenu();
		game = new HvlMenu();
		pause = new HvlMenu();

		settings.add(new HvlArrangerBox.Builder().setStyle(HvlArrangerBox.ArrangementStyle.VERTICAL).setxAlign(0f).build());
		settings.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Sound: " + (Main.settings.soundEnabled ? "on" : "off")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg) {
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				Main.settings.soundEnabled = !Main.settings.soundEnabled;
				b.setText("Sound: " + (Main.settings.soundEnabled ? "on" : "off"));
			}
		}).build());
		settings.getFirstArrangerBox().add(new HvlSpacer(0f, 32f));
		settings.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Tutorials: " + (Main.settings.tutorialsEnabled ? "on" : "off")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg) {
				HvlLabeledButton b = (HvlLabeledButton)aArg;
				Main.settings.tutorialsEnabled = !Main.settings.tutorialsEnabled;
				b.setText("Tutorials: " + (Main.settings.tutorialsEnabled ? "on" : "off"));
			}
		}).build());
		settings.getFirstArrangerBox().add(new HvlSpacer(0f, 32f));
		settings.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Back").setClickedCommand(new HvlButtonMenuLink(main)).build());

		credits.add(new HvlArrangerBox.Builder().setStyle(HvlArrangerBox.ArrangementStyle.VERTICAL).setxAlign(0f).build());
		credits.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Back").setClickedCommand(new HvlButtonMenuLink(main)).build());
		
		main.add(new HvlArrangerBox.Builder().setStyle(HvlArrangerBox.ArrangementStyle.VERTICAL).setxAlign(0f).build());
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Start").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Game.initialize();
				HvlMenu.setCurrent(game);
			}
		}).build());
		main.getFirstArrangerBox().add(new HvlSpacer(0f, 32f));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Settings").setClickedCommand(new HvlButtonMenuLink(settings)).build());
		main.getFirstArrangerBox().add(new HvlSpacer(0f, 32f));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Credits").setClickedCommand(new HvlButtonMenuLink(credits)).build());
		main.getFirstArrangerBox().add(new HvlSpacer(0f, 32f));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Quit").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				Main.saveConfig();
				System.exit(0);
			}
		}).build());

		pause.add(new HvlArrangerBox.Builder().setStyle(HvlArrangerBox.ArrangementStyle.VERTICAL).setxAlign(0f).build());
		pause.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Resume").setClickedCommand(new HvlButtonMenuLink(game)).build());
		pause.getFirstArrangerBox().add(new HvlSpacer(0f, 32f));
		pause.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Quit").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				HvlMenu.setCurrent(main);
				Game.restart();
			}
		}).build());

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
			Main.font.drawWordc("MAIN MENU", Display.getWidth()/2, Display.getHeight()/8, Color.white, 0.5f);
		}else if(HvlMenu.getCurrent() == settings){
			//UPDATING THE SETTINGS MENU//
			Main.font.drawWordc("SETTINGS", Display.getWidth()/2, Display.getHeight()/8, Color.white, 0.5f);
		}else if(HvlMenu.getCurrent() == credits){
			//UPDATING THE CREDITS MENU//
			Main.font.drawWordc("CREDITS", Display.getWidth()/2, Display.getHeight()/8, Color.white, 0.5f);
			Main.font.drawWordc("os_reboot", Display.getWidth()/2, Display.getHeight()*5/16, Color.white, 0.325f);
			Main.font.drawWordc("@os_reboot", Display.getWidth()/2, Display.getHeight()*6/16, Color.gray, 0.25f);
			Main.font.drawWordc("Basset", Display.getWidth()/2, Display.getHeight()*9/16, Color.white, 0.325f);
			Main.font.drawWordc("@xbassetx", Display.getWidth()/2, Display.getHeight()*10/16, Color.gray, 0.25f);
			Main.font.drawWordc("HaveANiceDay", Display.getWidth()/2, Display.getHeight()*13/16, Color.white, 0.325f);
			Main.font.drawWordc("...", Display.getWidth()/2, Display.getHeight()*14/16, Color.gray, 0.25f);
		}else if(HvlMenu.getCurrent() == game){
			//UPDATING THE GAME//
			for(HvlLabeledButton b : buttonAliases.keySet()){
				buttonAliases.get(b).reset();
			}
			pauseFrame.doCapture(new HvlAction0(){
				@Override
				public void run() {
					Game.update(delta);
				}
			});
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), pauseFrame);
		}else if(HvlMenu.getCurrent() == pause){
			//UPDATING THE PAUSE MENU//
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), pauseFrame);
			hvlDrawQuad(0, 0, Display.getWidth(), Display.getHeight(), new Color(0f, 0f, 0f, 0.8f));
			Main.font.drawWordc("PAUSED", Display.getWidth()/2, Display.getHeight()/8, Color.white, 0.5f);
		}

		HvlMenu.updateMenus(delta);
	}

	private static HvlCoord2D[] getTransformedButtonPolygon(float alpha, float width, float height){
		HvlCoord2D[] output = new HvlCoord2D[]{
				new HvlCoord2D(0, 0),
				new HvlCoord2D((width - (height/2)) * alpha + (height/2), 0),
				new HvlCoord2D((width - (height/2))*alpha, height),
				new HvlCoord2D(0, height),
		};
		return output;
	}

	private static class LabeledButtonAlias{
		public float hoverAlpha = 0f, hoverAlpha2 = 0f, widthAdd = 0f;

		public LabeledButtonAlias(){}

		public void update(float delta, boolean hover){
			hoverAlpha = HvlMath.stepTowards(hoverAlpha, delta*4f, hover ? 1f : 0f);
			hoverAlpha2 = HvlMath.stepTowards(hoverAlpha2, delta*8f, hover ? 1f : 0f);
			widthAdd = HvlMath.stepTowards(widthAdd, delta*512f, hover ? 64f : 0f);
		}

		public void reset(){
			hoverAlpha = 0;
			hoverAlpha2 = 0;
			widthAdd = 0;
		}
	}

}
