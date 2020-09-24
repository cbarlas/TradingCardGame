package com.cbarlas.tcg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cbarlas.tcg.GameController;
import com.cbarlas.tcg.Player;
import com.cbarlas.tcg.cards.DamageCard;
import com.cbarlas.tcg.decks.Deck;
import com.cbarlas.tcg.decks.RandomPickDeck;

class DamageCardTest {
	
	GameController controller;
	
	@BeforeEach
	void setUp() {
		controller = GameController.getInstance();
	}
	
	@Test
	void testCardMana() {
		DamageCard damageCard = new DamageCard(5);
		
		assertEquals(5, damageCard.getManaCost());
	}
	
	@Test
	void testCardDoesDamageToOpponentPlayer() {
		Deck firstPlayerDeck = new RandomPickDeck(1);
		
		DamageCard firstDamageCard = new DamageCard(2);
		firstPlayerDeck.addCard(firstDamageCard);
		
		Player firstPlayer = new Player(firstPlayerDeck);

		Deck secondPlayerDeck = new RandomPickDeck(1);
		
		DamageCard secondDamageCard = new DamageCard(5);
		firstPlayerDeck.addCard(secondDamageCard);
		
		Player secondPlayer = new Player(secondPlayerDeck);
		
		controller.setPlayers(firstPlayer, secondPlayer);
		
		assertEquals(30, secondPlayer.getHealth());
		
		firstDamageCard.play();
		
		assertEquals(28, secondPlayer.getHealth());
	}
}
