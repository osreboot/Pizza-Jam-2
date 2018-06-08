package com.hyprgloo.pizzajam2;

public class Game {
	
	public static Player player;
	
	public static void initialize(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
	}
	
	public static void update(float delta){
		player.draw(delta);
	}
	
	public static void restart(){
		player = new Player(Player.PLAYER_START_X, Player.PLAYER_START_Y);
	}

}
