package com.hyprgloo.pizzajam2;

import java.util.ArrayList;

public class Game {
	
	public static Player player;
	public static ArrayList<LineSegment> tites;
	public static ArrayList<LineSegment> mites;
	static ArrayList<PowerUp> powerUps;
	
	public static void initialize(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
	}
	
	public static void update(float delta){
		
	
		
		player.update(delta);
		player.draw(delta);
	}
	
	public static void restart(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
		tites.clear();
		mites.clear();
	}

}
