package me.Neoblade298.NeoProfessions.Commands;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerClass;
import me.Neoblade298.NeoProfessions.Main;
import me.Neoblade298.NeoProfessions.Items.BlacksmithItems;
import me.Neoblade298.NeoProfessions.Items.CommonItems;
import me.Neoblade298.NeoProfessions.Items.DrinksRecipeItems;
import me.Neoblade298.NeoProfessions.Items.IngredientRecipeItems;
import me.Neoblade298.NeoProfessions.Items.MasonItems;
import me.Neoblade298.NeoProfessions.Items.StonecutterItems;
import me.Neoblade298.NeoProfessions.Legacy.Converter;
import me.Neoblade298.NeoProfessions.Methods.BlacksmithMethods;
import me.Neoblade298.NeoProfessions.Methods.CulinarianMethods;
import me.Neoblade298.NeoProfessions.Methods.MasonMethods;
import me.Neoblade298.NeoProfessions.Methods.StonecutterMethods;
import me.Neoblade298.NeoProfessions.Utilities.BlacksmithUtils;
import me.Neoblade298.NeoProfessions.Utilities.Util;


public class NeoprofessionsCommands implements CommandExecutor {
	
	Main main;
	BlacksmithMethods blacksmithMethods;
	StonecutterMethods stonecutterMethods;
	CulinarianMethods culinarianMethods;
	MasonMethods masonMethods;
	Util util;
	CommonItems common;
	BlacksmithItems bItems;
	StonecutterItems sItems;
	MasonItems mItems;
	IngredientRecipeItems ingr;
	DrinksRecipeItems drink;
	
	public NeoprofessionsCommands(Main main, BlacksmithMethods b, StonecutterMethods s, CulinarianMethods c, MasonMethods m) {
		this.main = main;
		this.blacksmithMethods = b;
		this.stonecutterMethods = s;
		this.culinarianMethods = c;
		this.masonMethods = m;
		util = new Util();
		common = new CommonItems();
		bItems = new BlacksmithItems();
		sItems = new StonecutterItems();
		mItems = new MasonItems();
		ingr = new IngredientRecipeItems();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		Player p = null;
		if(sender instanceof Player) {
			p = (Player) sender;
		}
		
		if (args.length == 0) {
			sender.sendMessage("�7- �c/neoprofessions convert");
			sender.sendMessage("�7- �c/neoprofessions pay [player] [essence/oretype] [level] [amount]");
			sender.sendMessage("�7- �c/neoprofessions liquidate �7- Virtualizes all ore and essence in inventory");
			sender.sendMessage("�7- �c/neoprofessions solidify [essence/oretype] [amount] �7- Turns ore/essence into an item in inventory");
			sender.sendMessage("�7- �c/neoprofessions balance <player> [essence/oretype] [level]");
		}
		else if (args.length == 5 && args[0].equalsIgnoreCase("pay")) {
			if (Bukkit.getPlayer(args[1]) == null) {
				util.sendMessage(p, "&cPlayer must be online!");
				return true;
			}
			if (!main.cManager.validType(args[2])) {
				util.sendMessage(p, "&cInvalid type!");
				return true;
			}
			int level = Integer.parseInt(args[3]);
			if (!(level <= 60 && level > 0 && level % 5 == 0)) {
				util.sendMessage(p, "&cInvalid level!");
				return true;
			}
			int amount = Integer.parseInt(args[4]);
			if (amount <= 0 || amount >= 99999 || !main.cManager.hasEnough(p, args[2], level, amount)) {
				util.sendMessage(p, "&cInvalid amount!");
				return true;
			}
			Player recipient = Bukkit.getPlayer(args[1]);
			main.cManager.add(recipient, args[2], level, amount);
			main.cManager.subtract(p, args[2], level, amount);
			util.sendMessage(recipient, "&7You paid you &e" + recipient.getName() + " " + amount + " Lv " + level + " " + args[2]);
			util.sendMessage(recipient, "&e" + p.getName() + "&7 has paid you &e" + amount + " Lv " + level + " " + args[2]);
			return true;
		}
		else if (args.length == 4 && args[0].equalsIgnoreCase("balance")) {
			if (Bukkit.getPlayer(args[1]) == null) {
				util.sendMessage(p, "&cPlayer must be online!");
				return true;
			}
			if (!main.cManager.validType(args[2])) {
				util.sendMessage(p, "&cInvalid type!");
				return true;
			}
			int level = Integer.parseInt(args[3]);
			if (!(level <= 60 && level > 0 && level % 5 == 0)) {
				util.sendMessage(p, "&cInvalid level!");
				return true;
			}
			util.sendMessage(p, "&7Balance: &e" + main.cManager.get(Bukkit.getPlayer(args[1]), args[2], level));
		}
		else if (args.length == 3 && args[0].equalsIgnoreCase("balance")) {
			if (!main.cManager.validType(args[1])) {
				util.sendMessage(p, "&cInvalid type!");
				return true;
			}
			int level = Integer.parseInt(args[2]);
			if (!(level <= 60 && level > 0 && level % 5 == 0)) {
				util.sendMessage(p, "&cInvalid level!");
				return true;
			}
			util.sendMessage(p, "&7Balance: &e" + main.cManager.get(p, args[1], level));
		}
		else if (args.length == 1 && args[0].equalsIgnoreCase("convert")) {
			ItemStack[] inv = p.getInventory().getStorageContents();
			Converter conv = new Converter(main);
			for (int i = 0; i < inv.length; i++) {
				if (inv[i] != null) {
					int amt = inv[i].getAmount();
					inv[i] = util.setAmount(conv.convertItem(inv[i]), amt);
				}
			}
			p.getInventory().setStorageContents(inv);
		}
		else if (args.length == 1 && args[0].equalsIgnoreCase("convert")) {
			ItemStack[] inv = p.getInventory().getStorageContents();
			Converter conv = new Converter(main);
			for (int i = 0; i < inv.length; i++) {
				if (inv[i] != null) {
					int amt = inv[i].getAmount();
					inv[i] = util.setAmount(conv.convertItem(inv[i]), amt);
				}
			}
			p.getInventory().setStorageContents(inv);
		}
		// /neoprofessions liquidate [type] [level] [amount]
		else if (args.length == 3) {
			
		}
		
		if(sender.hasPermission("neoprofessions.admin") || sender.isOp()) {
			if (args.length == 0) {
				sender.sendMessage("�7- �4/neoprofessions level [playername] <amount>");
				sender.sendMessage("�7- �4/neoprofessions lore [line (from 0)] [newlore]");
				sender.sendMessage("�7- �4/neoprofessions removelore [line (from 0)]");
				sender.sendMessage("�7- �4/neoprofessions {reset/sober/repair} [playername]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get {essence/repair} [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get ingr [22-24]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get durability [weapon/armor] [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get ore [attribute or 1-7] [level] <amount>");
				sender.sendMessage("�7- �4/neoprofessions <playername> get {gem/overload} [weapon/armor] [attribute] [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get [basic/advanced] [charm]");
				sender.sendMessage("�7- �4/neoprofessions <playername> add [essence/oretype] [level] [amount]");
				return true;
			}
			else {
				// /neoprofessions add [essence/oretype] [level] [amount]
				if (args[0].equalsIgnoreCase("add")) {
					this.main.cManager.add(p, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
					util.sendMessage(p, "&7Success!");
				}
				// /neoprofessions level playername
				else if (args[0].equalsIgnoreCase("sober")) {
					if (args.length == 2) {
						main.culinarianListeners.drunkness.put(Bukkit.getPlayer(args[1]), 0);
						util.sendMessage(Bukkit.getPlayer(args[1]), "&7Successfully sobered!");
					}
				}
				else if (args.length == 1 && args[0].equalsIgnoreCase("convert")) {
					ItemStack[] inv = p.getInventory().getStorageContents();
					Converter conv = new Converter(main);
					for (int i = 0; i < inv.length; i++) {
						if (inv[i] != null) {
							int amt = inv[i].getAmount();
							inv[i] = util.setAmount(conv.convertItem(inv[i]), amt);
						}
					}
					p.getInventory().setStorageContents(inv);
				}
				else if (args[0].equalsIgnoreCase("lore")) {
					if (args.length >= 3) {
						ItemStack item = p.getInventory().getItemInMainHand();
						ItemMeta meta = item.getItemMeta();
						ArrayList<String> lore = meta.hasLore() ? (ArrayList<String>) meta.getLore() : new ArrayList<String>();
						int line = Integer.parseInt(args[1]);
						String newLore = args[3];
						for (int i = 4; i < args.length; i++) {
							newLore += " " + args[i];
						}
						while (lore.size() <= line) {
							if (lore.size() == line) {
								lore.add(newLore.replaceAll("&", "�"));
								return true;
							}
							else {
								lore.add("");
							}
						}
						lore.set(line, newLore);
						return true;
					}
				}
				else if (args[0].equalsIgnoreCase("removelore") && args.length == 1) {
					ItemStack item = p.getInventory().getItemInMainHand();
					ItemMeta meta = item.getItemMeta();
					ArrayList<String> lore = (ArrayList<String>) meta.getLore();
					lore.remove(Integer.parseInt(args[1]));
					meta.setLore(lore);
					item.setItemMeta(meta);
					return true;
				}
				else if (args[0].equalsIgnoreCase("repair")) {
					if (args.length == 2) {
						Player target = Bukkit.getPlayer(args[1]);
						ItemStack item = target.getInventory().getItemInMainHand();
						Util util = new Util();
						BlacksmithUtils bUtils = new BlacksmithUtils();
						ItemMeta im = item.getItemMeta();
						((Damageable) im).setDamage(0);
						item.setItemMeta(im);
						if (bUtils.canRepair(item)) {
							util.setCurrentDurability(item, util.getMaxDurability(item));
						}
						util.sendMessage(Bukkit.getPlayer(args[1]), "&7Item repaired successfully!");
					}
				}
				else if (args[0].equalsIgnoreCase("level")) {
					if (args.length == 2) {
						PlayerClass pClass = SkillAPI.getPlayerData(Bukkit.getPlayer(args[1])).getClass("profession");
						if (pClass != null) {
							if (pClass.getLevel() <= 60) {
								pClass.setLevel(pClass.getLevel() + 1);
								pClass.setPoints(pClass.getPoints() + 2);
								util.sendMessage(Bukkit.getPlayer(args[1]), "&7Your profession level is now &e" + pClass.getLevel() + "&7!");
							}
						}
					}
					else if (args.length == 3) {
						PlayerClass pClass = SkillAPI.getPlayerData(Bukkit.getPlayer(args[1])).getClass("profession");
						int levels = Integer.parseInt(args[2]);
						if (pClass != null) {
							if (pClass.getLevel() + levels <= 60) {
								pClass.setLevel(pClass.getLevel() + levels);
								pClass.setPoints(pClass.getPoints() + (2 * levels));
								util.sendMessage(Bukkit.getPlayer(args[1]), "&4[&c&lMLMC&4] &7Your profession level is now &e" + pClass.getLevel() + "&7!");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("reset")) {
					PlayerClass pClass = SkillAPI.getPlayerData(Bukkit.getPlayer(args[1])).getClass("profession");
					if (pClass != null) {
						if (pClass.getData().getName().equalsIgnoreCase("Blacksmith")) {
							blacksmithMethods.resetPlayer(Bukkit.getPlayer(args[1]));
						}
						else if (pClass.getData().getName().equalsIgnoreCase("Stonecutter")) {
							stonecutterMethods.resetPlayer(Bukkit.getPlayer(args[1]));
						}
						else if (pClass.getData().getName().equalsIgnoreCase("Culinarian")) {
							culinarianMethods.resetPlayer(Bukkit.getPlayer(args[1]));
						}
						else if (pClass.getData().getName().equalsIgnoreCase("Mason")) {
							masonMethods.resetPlayer(Bukkit.getPlayer(args[1]));
						}
					}
				}
				else if (args[0].equalsIgnoreCase("get")) {
					if(args[1].equalsIgnoreCase("essence")) {
						p.getInventory().addItem(common.getEssence(Integer.parseInt(args[2]), true));
					}
					else if(args[1].equalsIgnoreCase("repair")) {
						p.getInventory().addItem(bItems.getRepairItem(Integer.parseInt(args[2])));
					}
					else if(args[1].equalsIgnoreCase("durability")) {
						p.getInventory().addItem(bItems.getDurabilityItem(Integer.parseInt(args[3]), args[2]));
					}
					else if(args[1].equalsIgnoreCase("ore")) {
						int amount = 1;
						if (args.length == 5) {
							amount = Integer.parseInt(args[4]);
						}
						if (StringUtils.isNumeric(args[2])) {
							ItemStack ore = sItems.getOre(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
							ore.setAmount(amount);
							p.getInventory().addItem(ore);
						}
						else {
							ItemStack ore = sItems.getOre(args[2], Integer.parseInt(args[3]));
							ore.setAmount(amount);
							p.getInventory().addItem(ore);
						}
					}
					else if(args[1].equalsIgnoreCase("gem")) {
						if(args[2].equalsIgnoreCase("weapon")) {
							p.getInventory().addItem(sItems.getWeaponGem(args[3], Integer.parseInt(args[4]), false));
						}
						else if(args[2].equalsIgnoreCase("armor")) {
							p.getInventory().addItem(sItems.getArmorGem(args[3], Integer.parseInt(args[4]), false));
						}
					}
					else if(args[1].equalsIgnoreCase("ingr")) {
						if (args[2].equals("22")) {
							p.getInventory().addItem(ingr.getVodka());
						}
						else if (args[2].equals("23")) {
							p.getInventory().addItem(ingr.getRum());
						}
						else if (args[2].equals("24")) {
							p.getInventory().addItem(ingr.getTequila());
						}
					}
					else if(args[1].equalsIgnoreCase("overload")) {
						if(args[2].equalsIgnoreCase("weapon")) {
							p.getInventory().addItem(sItems.getWeaponGem(args[3], Integer.parseInt(args[4]), true));
						}
						else if(args[2].equalsIgnoreCase("armor")) {
							p.getInventory().addItem(sItems.getArmorGem(args[3], Integer.parseInt(args[4]), true));
						}
					}
					else if(args[1].equalsIgnoreCase("basic")) {
						if(args[2].equalsIgnoreCase("exp")) {
							p.getInventory().addItem(mItems.getExpCharm(false));
						}
						else if(args[2].equalsIgnoreCase("drop")) {
							p.getInventory().addItem(mItems.getDropCharm(false));
						}
						else if(args[2].equalsIgnoreCase("looting")) {
							p.getInventory().addItem(mItems.getLootingCharm(false));
						}
						else if(args[2].equalsIgnoreCase("traveler")) {
							p.getInventory().addItem(mItems.getTravelerCharm());
						}
						else if(args[2].equalsIgnoreCase("recovery")) {
							p.getInventory().addItem(mItems.getRecoveryCharm());
						}
					}
					else if(args[1].equalsIgnoreCase("advanced")) {
						if(args[2].equalsIgnoreCase("exp")) {
							p.getInventory().addItem(mItems.getExpCharm(true));
						}
						else if(args[2].equalsIgnoreCase("drop")) {
							p.getInventory().addItem(mItems.getDropCharm(true));
						}
						else if(args[2].equalsIgnoreCase("looting")) {
							p.getInventory().addItem(mItems.getLootingCharm(true));
						}
						else if(args[2].equalsIgnoreCase("hunger")) {
							p.getInventory().addItem(mItems.getHungerCharm());
						}
						else if(args[2].equalsIgnoreCase("secondchance")) {
							p.getInventory().addItem(mItems.getSecondChanceCharm());
						}
						else if(args[2].equalsIgnoreCase("quickeat")) {
							p.getInventory().addItem(mItems.getQuickEatCharm());
						}
					}
				}
				else {
					p = Bukkit.getPlayer(args[0]);
					if (args[1].equalsIgnoreCase("add")) {
						this.main.cManager.add(p, args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
						util.sendMessage(p, "&7Success!");
					}
					if (args[1].equalsIgnoreCase("get")) {
						if(args[2].equalsIgnoreCase("essence")) {
							p.getInventory().addItem(common.getEssence(Integer.parseInt(args[3]), true));
						}
						else if(args[2].equalsIgnoreCase("repair")) {
							p.getInventory().addItem(bItems.getRepairItem(Integer.parseInt(args[3])));
						}
						else if(args[2].equalsIgnoreCase("durability")) {
							p.getInventory().addItem(bItems.getDurabilityItem(Integer.parseInt(args[4]), args[3]));
						}
						else if(args[2].equalsIgnoreCase("ore")) {
							int amount = 1;
							if (args.length == 6) {
								amount = Integer.parseInt(args[5]);
							}
							if (StringUtils.isNumeric(args[3])) {
								ItemStack ore = sItems.getOre(Integer.parseInt(args[3]), Integer.parseInt(args[4]));
								ore.setAmount(amount);
								p.getInventory().addItem(ore);
							}
							else {
								ItemStack ore = sItems.getOre(args[3], Integer.parseInt(args[4]));
								ore.setAmount(amount);
								p.getInventory().addItem(ore);
							}
						}
						else if(args[1].equalsIgnoreCase("ingr")) {
							if (args[2].equals("22")) {
								p.getInventory().addItem(ingr.getVodka());
							}
							else if (args[2].equals("23")) {
								p.getInventory().addItem(ingr.getRum());
							}
							else if (args[2].equals("24")) {
								p.getInventory().addItem(ingr.getTequila());
							}
						}
						else if(args[2].equalsIgnoreCase("gem")) {
							if(args[3].equalsIgnoreCase("weapon")) {
								p.getInventory().addItem(sItems.getWeaponGem(args[4], Integer.parseInt(args[5]), false));
							}
							else if(args[3].equalsIgnoreCase("armor")) {
								p.getInventory().addItem(sItems.getArmorGem(args[4], Integer.parseInt(args[5]), false));
							}
						}
						else if(args[2].equalsIgnoreCase("overload")) {
							if(args[3].equalsIgnoreCase("weapon")) {
								p.getInventory().addItem(sItems.getWeaponGem(args[4], Integer.parseInt(args[5]), true));
							}
							else if(args[3].equalsIgnoreCase("armor")) {
								p.getInventory().addItem(sItems.getArmorGem(args[4], Integer.parseInt(args[5]), true));
							}
						}
						else if(args[2].equalsIgnoreCase("basic")) {
							if(args[3].equalsIgnoreCase("exp")) {
								p.getInventory().addItem(mItems.getExpCharm(false));
							}
							else if(args[3].equalsIgnoreCase("drop")) {
								p.getInventory().addItem(mItems.getDropCharm(false));
							}
							else if(args[3].equalsIgnoreCase("looting")) {
								p.getInventory().addItem(mItems.getLootingCharm(false));
							}
							else if(args[3].equalsIgnoreCase("traveler")) {
								p.getInventory().addItem(mItems.getTravelerCharm());
							}
							else if(args[3].equalsIgnoreCase("recovery")) {
								p.getInventory().addItem(mItems.getRecoveryCharm());
							}
						}
						else if(args[2].equalsIgnoreCase("advanced")) {
							if(args[3].equalsIgnoreCase("exp")) {
								p.getInventory().addItem(mItems.getExpCharm(true));
							}
							else if(args[3].equalsIgnoreCase("drop")) {
								p.getInventory().addItem(mItems.getDropCharm(true));
							}
							else if(args[3].equalsIgnoreCase("looting")) {
								p.getInventory().addItem(mItems.getLootingCharm(true));
							}
							else if(args[3].equalsIgnoreCase("hunger")) {
								p.getInventory().addItem(mItems.getHungerCharm());
							}
							else if(args[3].equalsIgnoreCase("secondchance")) {
								p.getInventory().addItem(mItems.getSecondChanceCharm());
							}
							else if(args[3].equalsIgnoreCase("quickeat")) {
								p.getInventory().addItem(mItems.getQuickEatCharm());
							}
						}
					}
				}
			}
		}
		return true;
	}
}