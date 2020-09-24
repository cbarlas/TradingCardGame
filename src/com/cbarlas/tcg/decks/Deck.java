package com.cbarlas.tcg.decks;

import java.util.LinkedList;
import java.util.List;

import com.cbarlas.tcg.cards.Card;
import com.cbarlas.tcg.exceptions.DeckOutOfCardException;

public abstract class Deck {
	
	protected List<Card> cards;
	protected int capacity;
	
	public Deck(int capacity) {
		this.capacity = capacity;
		cards = new LinkedList<>();
	}
	
	public int size() {
		return cards.size();
	}
	
	public Card drawCard() throws DeckOutOfCardException {
		Card card = null;
		
		if (cards.isEmpty()) {
			throw new DeckOutOfCardException("There are no cards left in deck.");
		} else {
			card = pickCard();
		}
		
		return card;
	}
	
	public void addCard(Card card) {
		if (canAddCard(card)) {
			cards.add(card);
		}
	}
	
	private boolean canAddCard(Card card) {
		return cards.size() < capacity && cards.indexOf(card) == -1;
	}
	
	protected abstract Card pickCard();
}
