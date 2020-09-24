package com.cbarlas.tcg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cbarlas.tcg.cards.Card;
import com.cbarlas.tcg.cards.DamageCard;
import com.cbarlas.tcg.decks.RandomPickDeck;
import com.cbarlas.tcg.exceptions.DeckOutOfCardException;

class RandomPickDeckTest {
	
	RandomPickDeck deck;
	
	int deckCapacity = 4; // for the sake of simplicity
	
	@BeforeEach
	void setUp() {
		deck = new RandomPickDeck(deckCapacity);
	}
	
	@Test
	void testDeckCapacity() {
		assertEquals(0, deck.size());
		
		deck.addCard(new DamageCard(0));
		deck.addCard(new DamageCard(0));
		deck.addCard(new DamageCard(0));
		deck.addCard(new DamageCard(0));

		assertEquals(deckCapacity, deck.size());

		deck.addCard(new DamageCard(0));

		assertEquals(deckCapacity, deck.size());
	}
	
	@Test
	void testDrawCard() {
		Card damageCard = new DamageCard(0);
		
		deck.addCard(damageCard);
		assertEquals(1, deck.size());
		
		Card drawnCard = null;
		
		try {
			drawnCard = deck.drawCard();
		} catch (DeckOutOfCardException e) {
		}
		
		assertEquals(damageCard, drawnCard);
		assertEquals(0, deck.size());
	}
	
	@Test
	void testDeckOutOfCardExceptionOnEmptyDeck() {
		assertEquals(0, deck.size());
		DeckOutOfCardException exception = assertThrows(DeckOutOfCardException.class,
				() -> deck.drawCard());
		
		assert(exception != null);
		assertEquals("There are no cards left in deck.", exception.getMessage());
	}
}
