package com.cbarlas.tcg.decks;

import java.util.Random;

import com.cbarlas.tcg.cards.Card;

public class RandomPickDeck extends Deck {
	
	private Random random;
	
	public RandomPickDeck(int capacity) {
		super(capacity);
		random = new Random();
	}
	
	@Override
	protected Card pickCard() {
		int randomCardIndex = random.nextInt(cards.size());
		
		Card randomCard = cards.remove(randomCardIndex);
		
		return randomCard;
	}
}
