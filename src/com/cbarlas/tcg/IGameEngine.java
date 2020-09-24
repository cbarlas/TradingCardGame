package com.cbarlas.tcg;

import com.cbarlas.tcg.cards.ICardCreator;
import com.cbarlas.tcg.decks.IDeckCreator;

public interface IGameEngine {

	public void runGame();
	
	public IDeckCreator getDeckCreator();
	
	public ICardCreator getCardCreator();
}
