package com.cbarlas.tcg.decks;

import com.cbarlas.tcg.GameConstants;
import com.cbarlas.tcg.cards.ICardCreator;

public class RandomPickDeckCreator implements IDeckCreator {
	
	private ICardCreator cardCreator;
	
	@Override
	public Deck createDeck(ICardCreator cardCreator) {
		this.cardCreator = cardCreator;
		
		Deck deck = new RandomPickDeck(GameConstants.DESK_CAPACITY);
		insertCardsToDeck(deck);
		
		return deck;
	}
	
	private void insertCardsToDeck(Deck deck) {
		insertCardsWithManaCostToDeck(deck, 0, 2);
		insertCardsWithManaCostToDeck(deck, 1, 2);
		insertCardsWithManaCostToDeck(deck, 2, 3);
		insertCardsWithManaCostToDeck(deck, 3, 4);
		insertCardsWithManaCostToDeck(deck, 4, 3);
		insertCardsWithManaCostToDeck(deck, 5, 2);
		insertCardsWithManaCostToDeck(deck, 6, 2);
		insertCardsWithManaCostToDeck(deck, 7, 1);
		insertCardsWithManaCostToDeck(deck, 8, 1);
	}
	
	private void insertCardsWithManaCostToDeck(Deck deck, int manaCost, int cardAmount) {
		for (int i = 0; i < cardAmount; ++i) {
			deck.addCard(cardCreator.createCard(manaCost));
		}
	}
}
