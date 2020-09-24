package com.cbarlas.tcg.cards;

import com.cbarlas.tcg.GameController;
import com.cbarlas.tcg.Player;

public class DamageCard extends Card {

	public DamageCard(int manaCost) {
		super(manaCost);
	}
	
	public void play() {
		GameController controller = GameController.getInstance();
		
		Player owner = controller.getActivePlayer();
		Player opponent = controller.getOpponentPlayer();
		
		owner.useMana(manaCost);
		
		int damage = manaCost;
		opponent.hit(damage);
		
		owner.discard(this);
	}
}
