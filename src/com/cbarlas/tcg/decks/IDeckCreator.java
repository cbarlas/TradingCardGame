package com.cbarlas.tcg.decks;

import com.cbarlas.tcg.cards.ICardCreator;

public interface IDeckCreator {
	
	public Deck createDeck(ICardCreator cardCreator);
}
