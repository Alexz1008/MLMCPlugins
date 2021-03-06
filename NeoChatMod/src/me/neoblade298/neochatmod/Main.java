package me.neoblade298.neochatmod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.palmergames.bukkit.TownyChat.events.AsyncChatHookEvent;

public class Main extends JavaPlugin implements Listener {
	File file = null;
	FileConfiguration conf = null;
	ArrayList<String> bannedWords = null;
	boolean muteGlobal = false;
	
	boolean muteTutorial = false;
	List<String> punishCmds = null; 

	int QUEST_X_BOUND_1 = -1578;
	int QUEST_X_BOUND_2 = -1168;
	int QUEST_Z_BOUND_1 = 1243;
	int QUEST_Z_BOUND_2 = 1805;
	int TOWNY_X_BOUND_1 = -1638;
	int TOWNY_X_BOUND_2 = -1468;
	int TOWNY_Z_BOUND_1 = 764;
	int TOWNY_Z_BOUND_2 = 1034;

	public void onEnable() {
		Bukkit.getServer().getLogger().info("NeoChatMod Enabled");
		getServer().getPluginManager().registerEvents(this, this);
	    this.getCommand("servermute").setExecutor(new Commands(this));

		// Save config if doesn't exist
		file = new File(getDataFolder(), "config.yml");
		if (!file.exists()) {
			saveResource("config.yml", false);
		}
		conf = YamlConfiguration.loadConfiguration(file);
		
		// Load settings
		muteTutorial = getConfig().getBoolean("mute-tutorial");
		punishCmds = getConfig().getStringList("punish-commands");
		
		// Load banned words
		bannedWords = (ArrayList<String>) getConfig().getStringList("banned-words");
		
		for (int i = 0; i < bannedWords.size(); i++) {
			bannedWords.set(i, bannedWords.get(i).toUpperCase());
		}
	}

	public void onDisable() {
		Bukkit.getServer().getLogger().info("NeoChatMod Disabled");
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onChat(AsyncChatHookEvent e) {
		if (this.muteGlobal && !e.getPlayer().hasPermission("towny.chat.mod")) {
			e.getPlayer().sendMessage("�4[�c�lMLMC�4] &cThere is currently a server mute!");
			e.setCancelled(true);
			return;
		}
		String msg = e.getMessage();
		double x = e.getPlayer().getLocation().getX();
		double z = e.getPlayer().getLocation().getZ();
		World w = e.getPlayer().getWorld();
		Player sender = e.getPlayer();
		
		// Check if they're in the tutorial world
		if (w.getName().equalsIgnoreCase("Argyll")) {
			if ((QUEST_X_BOUND_1 <= x && x <= QUEST_X_BOUND_2) &&
			(QUEST_Z_BOUND_1 <= z && z <= QUEST_Z_BOUND_2) &&
			!sender.hasPermission("tutorial.chat.receive")) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("tutorial.chat.receive")) {
						p.sendMessage("�4[�c�lMLMC�4] �c" + e.getPlayer().getName() + " �7spoke in tutorial: �c" + msg);
					}
				}
				e.getPlayer().sendMessage("�4[�c�lMLMC�4] �cYou cannot speak in the tutorial, but staff can still hear you!");
				e.setCancelled(true);
			}
			else if ((TOWNY_X_BOUND_1 <= x && x <= TOWNY_X_BOUND_2) &&
			(TOWNY_Z_BOUND_1 <= z && z <= TOWNY_Z_BOUND_2) &&
			!sender.hasPermission("tutorial.chat.receive")) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("tutorial.chat.receive")) {
						p.sendMessage("�4[�c�lMLMC�4] �c" + e.getPlayer().getName() + " �7spoke in tutorial: �c" + msg);
					}
				}
				e.getPlayer().sendMessage("�4[�c�lMLMC�4] �cYou cannot speak in the tutorial, but staff can still hear you!");
				e.setCancelled(true);
			}
		}
		
		// Check if it contained a curse word
		for (String word : bannedWords) {
			if (msg.toUpperCase().contains(word)) {
				e.setCancelled(true);
				try {
					for (String cmd : punishCmds) {
						Bukkit.getScheduler().callSyncMethod(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", e.getPlayer().getName())
								.replaceAll("%word%", word))).get();
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (p.hasPermission("tutorial.staff.receive")) {
						p.sendMessage("�4[�c�lMLMC�4] �c" + e.getPlayer().getName() + " �7was punished for saying: �c" + msg);
					}
				}
				return;
			}
		}
	}
	
	public void toggleMute() {
		this.muteGlobal = !this.muteGlobal;
	}
	
	public boolean getMute() {
		return this.muteGlobal;
	}
}