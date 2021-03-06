package me.neoblade298.neomythicextension.mechanics;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.INoTargetSkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import me.neoblade298.neobossinstances.Main;

public class RandomizeGoldMechanic extends SkillMechanic implements INoTargetSkill {
	
	protected final int min;
	protected final int max;
	protected final String boss;
	protected final Main nbi;

	public RandomizeGoldMechanic(MythicLineConfig config) {
		super(config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);
        
        this.min = config.getInteger("min", 0);
        this.max = config.getInteger("max", 0);
        this.boss = config.getString("boss", "Ratface");
        this.nbi = (Main) Bukkit.getPluginManager().getPlugin("NeoBossInstances");
	}

	@Override
	public boolean cast(SkillMetadata arg0) {
		ArrayList<Player> players = nbi.getActiveFights().get(this.boss);

		// Get gold min and max for party size, generate gold
		double gold = Math.random() * (max - min) + min;
		gold = Math.round(gold / 25.0D) * 25L;
		
		// Give gold to each player
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		for (Player player : players) {
			String command = "eco give " + player.getName() + " " + (int) gold;
			Bukkit.dispatchCommand(console, command);
			player.sendMessage("�4[�c�lMLMC�4] �7You gained �e" + (int) gold + " �7gold!");
		}
		return true;
	}
}
