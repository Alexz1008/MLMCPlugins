package me.neoblade298.neomonopoly.RNGCards;

import org.bukkit.scheduler.BukkitRunnable;

import me.neoblade298.neomonopoly.Objects.Game;
import me.neoblade298.neomonopoly.Objects.GamePlayer;
import me.neoblade298.neomonopoly.SpaceCards.Railroad;
import me.neoblade298.neomonopoly.SpaceCards.Utility;

public class NearestUtilityCard extends RNGCard{
	private Game game;
	private String name;
	
	public NearestUtilityCard(Game game, String name) {
		this.game = game;
		this.name = name;
	}
	
	@Override
	public void onDraw(GamePlayer gp, String src) {
		super.onDraw(gp, src);
		new BukkitRunnable() { public void run() {
			int pos = gp.getPosition();
			if (pos < 12) {
				game.movePlayerAbsolute(gp, 12, true, true);
			}
			else if (pos < 28) {
				game.movePlayerAbsolute(gp, 28, true, true);
			}
			else {
				game.movePlayerAbsolute(gp, 12, true, true);
			}
			Utility util = (Utility) game.board.get(gp.getPosition());
			util.onRNGLand(gp);
			game.isBusy = false;
		}}.runTaskLater(game.main, 40L);
	}
}
