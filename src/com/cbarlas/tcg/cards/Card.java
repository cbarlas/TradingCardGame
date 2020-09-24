package com.cbarlas.tcg.cards;

public abstract class Card {

	protected int manaCost;

	public Card(int manaCost) {
		this.manaCost = manaCost;
	}
	
	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
	
	public int getManaCost() {
		return manaCost;
	}
	
	public abstract void play();

}
