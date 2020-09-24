package com.cbarlas.tcg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cbarlas.tcg.cards.Card;
import com.cbarlas.tcg.cards.DamageCard;
import com.cbarlas.tcg.cards.DamageCardCreator;

class DamageCardCreatorTest {

	DamageCardCreator cardCreator;
	
	@BeforeEach
	void setUp() {
		cardCreator = new DamageCardCreator();
	}
	
	@Test
	void testDamageCardCreatorProducesDamageCard() {
		Card card = cardCreator.createCard(5);
		
		assert(card != null);
		assert(card instanceof DamageCard);
		
		assertEquals(5, card.getManaCost());
	}
}
