package com.cbarlas.tcg.cards;

public class DamageCardCreator implements ICardCreator {

	
	@Override
	public Card createCard(int manaCost) {
		return new DamageCard(manaCost);
	}
}
