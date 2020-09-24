package com.cbarlas.tcg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cbarlas.tcg.GameConstants;
import com.cbarlas.tcg.cards.DamageCardCreator;
import com.cbarlas.tcg.cards.ICardCreator;
import com.cbarlas.tcg.decks.Deck;
import com.cbarlas.tcg.decks.RandomPickDeck;
import com.cbarlas.tcg.decks.RandomPickDeckCreator;

class RandomPickDeckCreatorTest {
	
	RandomPickDeckCreator deckCreator;
	
	@BeforeEach
	void setUp() {
		deckCreator = new RandomPickDeckCreator();
	}
	
	@Test
	void testRandomPickDeckCreatorProducesRandomPickDeck() {
		ICardCreator cardCreator = new DamageCardCreator();
		
		Deck deck = deckCreator.createDeck(cardCreator);
		
		assert(deck != null);
		assert(deck instanceof RandomPickDeck);
		
		assertEquals(GameConstants.DESK_CAPACITY, deck.size());
	}
}
