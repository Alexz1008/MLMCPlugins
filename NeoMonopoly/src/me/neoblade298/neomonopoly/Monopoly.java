package me.neoblade298.neomonopoly;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.neoblade298.neomonopoly.Commands.Commands;
import me.neoblade298.neomonopoly.Objects.*;
import me.neoblade298.neomonopoly.RNGCards.*;
import me.neoblade298.neomonopoly.SpaceCards.*;

public class Monopoly extends JavaPlugin implements org.bukkit.event.Listener {
	private YamlConfiguration conf;
	
	// Player data structures
	public HashMap<Player, Lobby> inlobby;
	public HashMap<Player, Game> ingame;
	public HashMap<String, Lobby> lobbies;
	public HashMap<String, Game> games;
	
	public HashMap<ChatColor, String> colorToString;

	public void onEnable() {
		Bukkit.getServer().getLogger().info("NeoMonopoly Enabled");
		getServer().getPluginManager().registerEvents(this, this);
		this.getCommand("cmd").setExecutor(new Commands(this));

		// Load in items from config
		loadConfigs();
		
		// Chat color to string
		colorToString = new HashMap<ChatColor, String>();
		colorToString.put(ChatColor.BLUE, "blue");
		colorToString.put(ChatColor.GOLD, "orange");
		colorToString.put(ChatColor.DARK_PURPLE, "purple");
		colorToString.put(ChatColor.LIGHT_PURPLE, "pink");
		colorToString.put(ChatColor.DARK_AQUA, "cyan");
		colorToString.put(ChatColor.GREEN, "green");
		colorToString.put(ChatColor.GRAY, "grey");
		colorToString.put(ChatColor.DARK_GRAY, "dark grey");
		colorToString.put(ChatColor.YELLOW, "yellow");
	}

	public void loadConfigs() {
		File file = new File(getDataFolder(), "config.yml");

		// Save config if doesn't exist
		if (!file.exists()) {
			saveResource("config.yml", false);
		}
		this.conf = YamlConfiguration.loadConfiguration(file);
	}

	public void loadBoard(ArrayList<Space> board, Game game, HashMap<ChatColor, ArrayList<BuildableProperty>> colors) throws Exception {
		ConfigurationSection spacesConfig = conf.getConfigurationSection("spaces");
		for (String key : spacesConfig.getKeys(false)) {
			ConfigurationSection spaceConfig = spacesConfig.getConfigurationSection(key);
			String type = spaceConfig.getString("type");

			String name = null;
			Property property = null;
			int[] rent = null;
			int price = 0;
			ChatColor color = null;
			switch (type) {
			case "go":
				board.add(new FreeSpace(game));
				break;
			case "free":
				board.add(new FreeSpace(game));
				break;
			case "property":
				name = spaceConfig.getString("name");
				rent = spaceConfig.getIntegerList("rent").stream().mapToInt(Integer::intValue).toArray();
				price = spaceConfig.getInt("price");
				int houseprice = spaceConfig.getInt("houseprice");
				color = ChatColor.getByChar(spaceConfig.getString("color").charAt(0));
				BuildableProperty buildableProperty = new BuildableProperty(name, rent, price, houseprice, color, game);
				sortColor(buildableProperty, colors);
				board.add(buildableProperty);
				break;
			case "railroad":
				name = spaceConfig.getString("name");
				rent = spaceConfig.getIntegerList("rent").stream().mapToInt(Integer::intValue).toArray();
				color = ChatColor.getByChar(spaceConfig.getString("color").charAt(0));
				price = spaceConfig.getInt("price");
				property = new Railroad(name, rent, color, game, price);
				board.add(property);
				break;
			case "utility":
				name = spaceConfig.getString("name");
				rent = spaceConfig.getIntegerList("rent").stream().mapToInt(Integer::intValue).toArray();
				color = ChatColor.getByChar(spaceConfig.getString("color").charAt(0));
				price = spaceConfig.getInt("price");
				property = new Utility(name, rent, color, game, price);
				board.add(property);
				break;
			case "jail":
				board.add(new Jail(game));
				break;
			case "communitychest":
				board.add(new CommunityChest(game));
				break;
			case "chance":
				board.add(new Jail(game));
				break;
			default:
				throw new Exception("Improper space card type");
			}
		}
	}
	
	public void onDisable() {
		org.bukkit.Bukkit.getServer().getLogger().info("NeoMonopoly Disabled");
		super.onDisable();
	}

	private void sortColor(BuildableProperty property, HashMap<ChatColor, ArrayList<BuildableProperty>> colors) {
		if (colors.containsKey(property.getColor())) {
			colors.get(property.getColor()).add(property);
		}
		else {
			ArrayList<BuildableProperty> color = new ArrayList<BuildableProperty>();
			color.add(property);
			colors.put(property.getColor(), color);
		}
	}

	public void loadRNGCards(ArrayList<RNGCard> list, Game game, String configSec) throws Exception {
		ConfigurationSection sec = conf.getConfigurationSection(configSec);
		for (String key : sec.getKeys(false)) {
			ConfigurationSection card = sec.getConfigurationSection(key);
			String type = card.getString("type");
			
			switch (type) {
			case "buildingtax":
				list.add(new BuildingTaxCard(game, card.getString("name"), card.getInt("house"), card.getInt("hotel")) );
				break;
			case "gainmoney":
				list.add(new GainMoneyCard(game, card.getString("name"), card.getInt("amount")));
				break;
			case "jail":
				list.add(new JailCard(game, card.getString("name")));
				break;
			case "jailfree":
				list.add(new JailFreeCard(game, card.getString("name")));
				break;
			case "losemoney":
				list.add(new LoseMoneyCard(game, card.getString("name"), card.getInt("amount")));
				break;
			case "move":
				list.add(new MoveCard(game, card.getString("name"), card.getInt("move")));
				break;
			case "moverelative":
				list.add(new MoveRelativeCard(game, card.getString("name"), card.getInt("move")));
				break;
			case "nearestrailroad":
				list.add(new NearestRailroadCard(game, card.getString("name")));
				break;
			case "nearestutility":
				list.add(new NearestUtilityCard(game, card.getString("name")));
				break;
			case "paymoney":
				list.add(new PayMoneyCard(game, card.getString("name"), card.getInt("amount")));
				break;
			case "takemoney":
				list.add(new TakeMoneyCard(game, card.getString("name"), card.getInt("amount")));
				break;
			default:
				throw new Exception("Improper space card type");
			}
		}
	}
	
}
