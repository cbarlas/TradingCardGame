package com.cbarlas.tcg;

import java.util.Scanner;

import com.cbarlas.tcg.cards.Card;
import com.cbarlas.tcg.cards.DamageCardCreator;
import com.cbarlas.tcg.cards.ICardCreator;
import com.cbarlas.tcg.decks.IDeckCreator;
import com.cbarlas.tcg.decks.RandomPickDeckCreator;

public class TextBasedGameEngine implements IGameEngine {

	private GameController controller;
	
	private Player firstPlayer;
	private Player secondPlayer;
	
	private Scanner inputStream;
	
	public TextBasedGameEngine() {
		controller = GameController.getInstance();
		
		firstPlayer = new Player(getDeckCreator().createDeck(getCardCreator()));
		secondPlayer = new Player(getDeckCreator().createDeck(getCardCreator()));
		controller.setPlayers(firstPlayer, secondPlayer);
		
		inputStream = new Scanner(System.in);
	}
	
	@Override
	public void runGame() {
		greet();
		requestUserNames();
		controller.startGame();
		
		while (controller.isGameOver() == false) {
			Player activePlayer = controller.getActivePlayer();
			System.out.println("");
			System.out.println("Turn for " + activePlayer.getName() + " to play.");
			
			boolean userEndedTurn = false;
			
			while (userEndedTurn == false) {
				
				displayTurnInfo();
				
				Card card = getCardFromUserInput();
				
				if (card == null) {
					userEndedTurn = true;
					activePlayer.endTurn();
				} else if (activePlayer.canPlayCard(card)) {
					card.play();
					if (controller.isGameOver()) {
						userEndedTurn = true;
					}
				} else {
					System.out.println("Cannot play this card, insufficient Mana.");
				}
			}
		}
		
		Player winner = controller.getWinnerPlayer();
		
		System.out.println("Winner is: " + winner.getName());
		inputStream.close();
	}

	@Override
	public IDeckCreator getDeckCreator() {
		return new RandomPickDeckCreator();
	}

	@Override
	public ICardCreator getCardCreator() {
		return new DamageCardCreator();
	}
	
	private void greet() {
		System.out.println("Trading Card Game");
	}
	
	private void requestUserNames() {
		System.out.println("Please enter Player 1 name:");
		String firstPlayerName = inputStream.nextLine();
		firstPlayer.setName(firstPlayerName);
		
		System.out.println("Please enter Player 2 name:");
		String secondPlayerName = inputStream.nextLine();
		secondPlayer.setName(secondPlayerName);
	}
	
	private Card getCardFromUserInput() {
		Card card = null;
		Player activePlayer = controller.getActivePlayer();
		
		System.out.print("Please select the card number to play, select 0 to end turn: ");
		
		boolean validInputTaken = false;
		
		while (validInputTaken == false) {
			int cardIndex = inputStream.nextInt() - 1;
			
			if (cardIndex >= 0 && cardIndex < activePlayer.getHandSize()) {
				card = activePlayer.getHand().get(cardIndex);
				validInputTaken = true;
			} else if (cardIndex == -1) {
				System.out.println(activePlayer.getName() + " ends turn.");
				validInputTaken = true;
			} else {
				displayTurnInfo();
				System.out.print("Invalid card number. Please select again, select 0 to end turn: ");
			}
		}
		
		return card;		
	}
	
	private void displayTurnInfo() {
		displayUserHand();
		displayUserHealth();
		displayUserMana();
		displayDeckSize();
	}
	
	private void displayUserHand() {
		System.out.println("-----------");
		
		Player activePlayer = controller.getActivePlayer();
		
		for (int i = 0; i < activePlayer.getHandSize(); ++i) {
			Card card = activePlayer.getHand().get(i);
			System.out.print("Card " + (i + 1) + " Mana cost is " + card.getManaCost());
			
			if (i + 1 < activePlayer.getHandSize()) {
				System.out.print(" ---- ");
			}
		}
		
		System.out.println("");
	}
	
	private void displayUserHealth() {
		Player activePlayer = controller.getActivePlayer();
		
		System.out.println("Health: " + activePlayer.getHealth());
	}
	
	private void displayUserMana() {
		Player activePlayer = controller.getActivePlayer();
		
		System.out.println("Usable mana: " + activePlayer.getMana());
	}
	
	private void displayDeckSize() {
		Player activePlayer = controller.getActivePlayer();

		System.out.println("Cards left in the deck: " + activePlayer.getDeckSize());
	}
}
