package com.cbarlas.tcg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cbarlas.tcg.GameController;
import com.cbarlas.tcg.Player;
import com.cbarlas.tcg.cards.DamageCardCreator;
import com.cbarlas.tcg.cards.ICardCreator;
import com.cbarlas.tcg.decks.IDeckCreator;
import com.cbarlas.tcg.decks.RandomPickDeckCreator;

class GameControllerTest {

	GameController controller;
	Player player1;
	Player player2;
	
	@BeforeEach
	void setUp() {
		controller = GameController.getInstance();
		IDeckCreator deckCreator = new RandomPickDeckCreator();
		ICardCreator cardCreator = new DamageCardCreator();
		
		player1 = new Player(deckCreator.createDeck(cardCreator));
		player2 = new Player(deckCreator.createDeck(cardCreator));
	}
	
	@AfterEach
	void destroy() {
		controller.endGame();
	}
	
	@Test
	void testSetPlayers() {
		controller.setPlayers(player1, player2);
		
		assertEquals(player1, controller.getActivePlayer());
		assertEquals(player2, controller.getOpponentPlayer());
	}
	
	@Test
	void testStartGameDistributesInitialCards() {
		controller.setPlayers(player1, player2);
		
		controller.startGame();
		
		assertEquals(4, player1.getHandSize());
		assertEquals(3, player2.getHandSize());
		
		assertEquals(16, player1.getDeckSize());
		assertEquals(17, player2.getDeckSize());
	}
	
	@Test
	void testEndTurnForPlayerStartsTurnForOtherPlayer() {
		controller.setPlayers(player1, player2);
		
		controller.startGame();
		
		assertEquals(3, player2.getHandSize());
		
		controller.endTurnForPlayer(controller.getActivePlayer());

		assertEquals(player2, controller.getActivePlayer());
		assertEquals(player1, controller.getOpponentPlayer());
		
		assertEquals(4, player2.getHandSize());
	}
	
	@Test
	void testEndGameNullifiesActivePlayer() {
		controller.setPlayers(player1, player2);
		
		controller.startGame();
		controller.endGame();
		
		assert(controller.getActivePlayer() == null);
	}
	
	@Test
	void testEndTurnForPlayerEndsTheGameIfPlayerIsDead() {
		controller.setPlayers(player1, player2);
		
		controller.startGame();
		
		player1.hit(30);
		
		assert(player1.isDead() == true);
		controller.endTurnForPlayer(player1);
		
		assert(controller.getActivePlayer() == null);
	}
}
