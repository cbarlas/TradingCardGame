package com.cbarlas.tcg;

import java.util.List;

import com.cbarlas.tcg.cards.Card;
import com.cbarlas.tcg.decks.Deck;
import com.cbarlas.tcg.exceptions.DeckOutOfCardException;

import java.util.LinkedList;

public class Player {
	
	private int health;
	private int manaSlots;
	private int mana;
	
	private Deck deck;
	private List<Card> hand;
	
	private String name;
	
	public Player(Deck deck) {
		health = GameConstants.INITIAL_HEALTH;
		manaSlots = GameConstants.INITIAL_MANA_SLOTS;
		mana = manaSlots;
		this.deck = deck;
		hand = new LinkedList<>();
		name = "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void drawCard() {
		try {
			Card card = deck.drawCard();
			if (hand.size() < GameConstants.HAND_CAPACITY) {
				hand.add(card);
			}
		} catch (DeckOutOfCardException ex) {
			hit(1);
		}
	}
	
	public void discard(Card card) {
		hand.remove(card);
	}
	
	public void playCard(Card card) {
		if (canPlayCard(card)) {
			card.play();
		}
	}
	
	public void hit(int damage) {
		health -= damage;
		
		if (isDead()) {
			endTurn();
		}
	}
	
	public void useMana(int manaCost) {
		mana -= manaCost;
	}
	
	public void startTurn() {
		updateManaSlotCapacity();
		refreshManaSlots();
		drawCard();
	}
	
	public void endTurn() {
		GameController controller = GameController.getInstance();
		
		controller.endTurnForPlayer(this);
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMana() {
		return mana;
	}
	
	public boolean canPlayCard(Card card) {
		return hand.indexOf(card) != -1 && mana >= card.getManaCost();
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	public int getDeckSize() {
		return deck.size();
	}
	
	public List<Card> getHand() {
		return hand;
	}

	private void updateManaSlotCapacity() {
		if (manaSlots < GameConstants.MAXIMUM_MANA_SLOTS) {
			++manaSlots;
		}
	}
	
	private void refreshManaSlots() {
		mana = manaSlots;
	}
}
