package com.cbarlas.tcg;

public class Main {

	public static void main(String[] args) {
		IGameEngine gameEngine = new TextBasedGameEngine();
		
		gameEngine.runGame();		
	}
}
