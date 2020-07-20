package me.neoblade298.neomonopoly.RNGCards;

import me.neoblade298.neomonopoly.Objects.Game;
import me.neoblade298.neomonopoly.Objects.GamePlayer;

public class GainMoneyCard extends RNGCard {
	private Game game;
	private String name;
	private int amount;
	
	public GainMoneyCard(Game game, String name, int amount) {
		this.game = game;
		this.name = name;
		this.amount = amount;
	}
	
	public void onDraw(GamePlayer gp, String src) {
		super.onDraw(gp, src);
		game.giveMoney(amount, gp, "&e" + gp + " &7got &a+$" + amount + "&7!");
		game.isBusy = false;
	}
}
