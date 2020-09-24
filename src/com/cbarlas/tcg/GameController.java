package com.cbarlas.tcg;

public class GameController {

	private static GameController instance = null;
	
	private Player player1;
	private Player player2;
	
	private Player activePlayer;
	private Player winnerPlayer;
	private boolean gameOver;
	
	private GameController() {
		gameOver = false;
	}
	
	public static GameController getInstance() {
		createInstance();
		return instance;
	}
	
	private static void createInstance() {
		if (instance == null) {
			instance = new GameController();
		}
	}
	
	public void setPlayers(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		activePlayer = player1;
	}
	
	public void startGame() {
		gameOver = false;
		activePlayer = player1;
		winnerPlayer = null;
		
		initializePlayerHand(player1, GameConstants.INITIAL_HAND_SIZE);
		initializePlayerHand(player2, GameConstants.INITIAL_HAND_SIZE);
		
		if (activePlayer != null) {
			activePlayer.startTurn();
		}
	}
	
	public void endTurnForPlayer(Player player) {
		if (player.equals(activePlayer)) {
			
			if (player.isDead()) {
				winnerPlayer = getOpponentPlayer();
				endGame();
			} else {
				switchActivePlayer();
				activePlayer.startTurn();
			}
		} else if (player.equals(getOpponentPlayer()) && player.isDead()) {
			winnerPlayer = activePlayer;
			endGame();
		}
	}

	public void endGame() {
		player1 = null;
		player2 = null;
		
		gameOver = true;
		activePlayer = null;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public Player getOpponentPlayer() {
		Player opponent = null;
		
		if (activePlayer != null) {
			opponent = (activePlayer.equals(player1) ? player2 : player1);
		}
		
		return opponent;
	}
	
	public Player getWinnerPlayer() {
		return winnerPlayer;
	}
	
	private void switchActivePlayer() {
		activePlayer = getOpponentPlayer();
	}
	
	private void initializePlayerHand(Player player, int initialCardAmount) {
		for (int i = 0; i < initialCardAmount; ++i) {
			player.drawCard();
		}
	}
		
}
