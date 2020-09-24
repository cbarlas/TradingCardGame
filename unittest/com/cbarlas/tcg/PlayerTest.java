package com.cbarlas.tcg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.cbarlas.tcg.GameController;
import com.cbarlas.tcg.Player;
import com.cbarlas.tcg.cards.Card;
import com.cbarlas.tcg.cards.DamageCard;
import com.cbarlas.tcg.cards.DamageCardCreator;
import com.cbarlas.tcg.cards.ICardCreator;
import com.cbarlas.tcg.decks.Deck;
import com.cbarlas.tcg.decks.IDeckCreator;
import com.cbarlas.tcg.decks.RandomPickDeck;
import com.cbarlas.tcg.decks.RandomPickDeckCreator;

@TestInstance(Lifecycle.PER_CLASS)
class PlayerTest {

	IDeckCreator deckCreator;
	ICardCreator cardCreator;
	GameController controller;
	
	Player player;
	
	@BeforeAll
	void initializeGame() {
		deckCreator = new RandomPickDeckCreator();
		cardCreator = new DamageCardCreator();
	}
	
	@BeforeEach
	void setUp() {
		controller = GameController.getInstance();
		player = new Player(deckCreator.createDeck(cardCreator));
	}
	
	@AfterEach
	void destroy() {
		controller.endGame();
	}
	
	@Test
	void testPlayerInitialHealth() {
		assertEquals(30, player.getHealth());
	}
	
	@Test
	void testPlayerInitialMana() {
		assertEquals(0, player.getMana());
	}
	
	@Test
	void testPlayerManaCapacityIncreasesAtEachTurn() {
		player.startTurn();
		assertEquals(1, player.getMana());
	}
	
	@Test
	void testPlayerMaximumManaCapacityIsTen() {
		assertEquals(0, player.getMana());
		
		for (int i = 0; i < 10; ++i) {
			player.startTurn();
		}
		
		assertEquals(10, player.getMana());

		player.startTurn();
		assertEquals(10, player.getMana());
	}
	
	@Test
	void testPlayerCanUseMana() {
		player.startTurn();
		assertEquals(1, player.getMana());
		
		player.useMana(1);
		assertEquals(0, player.getMana());
	}
	
	@Test
	void testPlayerDrawsACardAtTurn() {
		assertEquals(0, player.getHandSize());
		
		player.startTurn();

		assertEquals(1, player.getHandSize());
		assertEquals(19, player.getDeckSize());
	}
	
	@Test
	void testDrawnCardIsDiscardedOnFullHand() {
		assertEquals(0, player.getHandSize());
		assertEquals(20, player.getDeckSize());
		
		player.drawCard();
		player.drawCard();
		player.drawCard();
		player.drawCard();
		player.drawCard();

		assertEquals(5, player.getHandSize());
		assertEquals(15, player.getDeckSize());
		
		player.drawCard();

		assertEquals(5, player.getHandSize());
		assertEquals(14, player.getDeckSize());
	}
	
	@Test
	void testPlayerLosesHealthDrawingCardFromEmptyDeck() {
		assertEquals(30, player.getHealth());
		
		int deckSize = player.getDeckSize();
		
		for (int i = 0; i < deckSize; ++i) {
			player.drawCard();
		}
		
		assertEquals(player.getDeckSize(), 0);
		assertEquals(30, player.getHealth());
		
		player.drawCard();
		assertEquals(29, player.getHealth());
	}
	
	@Test
	void testPlayerIsDeadOnTakingFatalDamage() {
		assertEquals(30, player.getHealth());
		
		player.hit(9);
		
		assertEquals(21, player.getHealth());
		assert(player.isDead() == false);
		
		player.hit(25);
		assert(player.isDead() == true);
	}
	
	@Test
	void testPlayerCanPlayCardAgainstOpponent() {
		Deck deckForFirstPlayer = new RandomPickDeck(5);
		
		Card card1 = new DamageCard(1);
		deckForFirstPlayer.addCard(card1);
		Card card2 = new DamageCard(1);
		deckForFirstPlayer.addCard(card2);
		Card card3 = new DamageCard(1);
		deckForFirstPlayer.addCard(card3);
		Card card4 = new DamageCard(1);
		deckForFirstPlayer.addCard(card4);
		
		Player firstPlayer = new Player(deckForFirstPlayer);
		controller.setPlayers(firstPlayer, player);
		controller.startGame();
		
		assertEquals(30, player.getHealth());
		assertEquals(1, firstPlayer.getMana());
		assertEquals(4, firstPlayer.getHandSize());
		
		firstPlayer.playCard(card2);

		assertEquals(29, player.getHealth());
		assertEquals(0, firstPlayer.getMana());
		assertEquals(3, firstPlayer.getHandSize());
	}
	
	@Test
	void testPlayerCanDiscardACardFromHand() {
		player.drawCard();
		List<Card> playerHand = player.getHand();
		
		assertEquals(1, player.getHandSize());
		
		player.discard(playerHand.get(0));
		assertEquals(0, player.getHandSize());
	}
	
	@Test
	void testActivePlayerChangesWhenUserEndsTurn() {
		Deck deckForOpponentPlayer = new RandomPickDeck(5);
		
		deckForOpponentPlayer.addCard(new DamageCard(1));
		deckForOpponentPlayer.addCard(new DamageCard(1));
		deckForOpponentPlayer.addCard(new DamageCard(1));
		deckForOpponentPlayer.addCard(new DamageCard(1));
		deckForOpponentPlayer.addCard(new DamageCard(1));
		
		Player opponent = new Player(deckForOpponentPlayer);
		
		controller.setPlayers(player, opponent);
		controller.startGame();
		
		assertEquals(player, controller.getActivePlayer());
		assertEquals(opponent, controller.getOpponentPlayer());
		
		player.endTurn();

		assertEquals(opponent, controller.getActivePlayer());
		assertEquals(player, controller.getOpponentPlayer());
	}
	
	@Test
	void testPlayerCannotPlayHigherManaCard() {
		Deck deck = new RandomPickDeck(1);
		
		Card highCostCard = new DamageCard(7);
		
		deck.addCard(highCostCard);
		
		Player opponent = new Player(deck);
		
		assert(opponent.getMana() < highCostCard.getManaCost());
		assert(opponent.canPlayCard(highCostCard) == false);
	}
}
