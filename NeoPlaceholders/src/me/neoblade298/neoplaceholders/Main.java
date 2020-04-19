package me.neoblade298.neoplaceholders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements org.bukkit.event.Listener {

	public void onEnable() {
		Bukkit.getServer().getLogger().info("NeoPlaceholders Enabled");
		getServer().getPluginManager().registerEvents(this, this);
		
		new SkillAPIPlaceholders(this).registerPlaceholders();
		new NeoprofessionsPlaceholders().register();
		new NeoBossInstancesPlaceholders().register();
		new MinibossPlaceholders().register();
	}

	public void onDisable() {
		org.bukkit.Bukkit.getServer().getLogger().info("NeoPlaceholders Disabled");
		super.onDisable();
	}
}
