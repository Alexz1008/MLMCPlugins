package me.Neoblade298.NeoProfessions.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerClass;
import me.Neoblade298.NeoProfessions.Main;
import me.Neoblade298.NeoProfessions.Items.BlacksmithItems;
import me.Neoblade298.NeoProfessions.Items.CommonItems;
import me.Neoblade298.NeoProfessions.Items.MasonItems;
import me.Neoblade298.NeoProfessions.Items.StonecutterItems;
import me.Neoblade298.NeoProfessions.Methods.BlacksmithMethods;
import me.Neoblade298.NeoProfessions.Methods.CulinarianMethods;
import me.Neoblade298.NeoProfessions.Methods.MasonMethods;
import me.Neoblade298.NeoProfessions.Methods.StonecutterMethods;
import me.Neoblade298.NeoProfessions.Utilities.Util;


public class NeoprofessionsCommands implements CommandExecutor {
	
	Main main;
	BlacksmithMethods blacksmithMethods;
	StonecutterMethods stonecutterMethods;
	CulinarianMethods culinarianMethods;
	MasonMethods masonMethods;
	
	public NeoprofessionsCommands(Main main, BlacksmithMethods b, StonecutterMethods s, CulinarianMethods c, MasonMethods m) {
		this.main = main;
		this.blacksmithMethods = b;
		this.stonecutterMethods = s;
		this.culinarianMethods = c;
		this.masonMethods = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if(sender.hasPermission("neoprofessions.admin")) {
			Player p = null;
			if(sender instanceof Player) {
				p = (Player) sender;
			}
			
			if (args.length == 0) {
				sender.sendMessage("�7- �4/neoprofessions level [playername] <amount>");
				sender.sendMessage("�7- �4/neoprofessions reset [playername]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get essence [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get fragment [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get repair [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get durability [weapon/armor] [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get ore [attribute] [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get gem [weapon/armor] [attribute] [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get overload [weapon/armor] [attribute] [level]");
				sender.sendMessage("�7- �4/neoprofessions <playername> get [basic/advanced] [charm]");
				return true;
			}
			else {
				// /neoprofessions level playername
				if (args[0].equalsIgnoreCase("level")) {
					if (args.length == 1) {
						PlayerClass pClass = SkillAPI.getPlayerData(Bukkit.getPlayer(args[1])).getClass("profession");
						if (pClass != null) {
							if (pClass.getLevel() <= 60) {
								pClass.setLevel(pClass.getLevel() + 1);
								pClass.setPoints(pClass.getPoints() + 2);
								Util.sendMessage(Bukkit.getPlayer(args[1]), "&4[&c&lMLMC&4] &7Your profession level is now &e" + pClass.getLevel() + "&7!");
							}
						}
					}
					else if (args.length == 2) {
						PlayerClass pClass = SkillAPI.getPlayerData(Bukkit.getPlayer(args[1])).getClass("profession");
						int levels = Integer.parseInt(args[2]);
						if (pClass != null) {
							if (pClass.getLevel() + levels <= 60) {
								pClass.setLevel(pClass.getLevel() + levels);
								pClass.setPoints(pClass.getPoints() + (2 * levels));
								Util.sendMessage(Bukkit.getPlayer(args[1]), "&4[&c&lMLMC&4] &7Your profession level is now &e" + pClass.getLevel() + "&7!");
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
						p.getInventory().addItem(CommonItems.getEssence(Integer.parseInt(args[2])));
					}
					else if(args[1].equalsIgnoreCase("fragment")) {
						p.getInventory().addItem(CommonItems.getEssenceFragment(Integer.parseInt(args[2])));
					}
					else if(args[1].equalsIgnoreCase("repair")) {
						p.getInventory().addItem(BlacksmithItems.getRepairItem(Integer.parseInt(args[2])));
					}
					else if(args[1].equalsIgnoreCase("durability")) {
						p.getInventory().addItem(BlacksmithItems.getDurabilityItem(Integer.parseInt(args[3]), args[2]));
					}
					else if(args[1].equalsIgnoreCase("ore")) {
						p.getInventory().addItem(StonecutterItems.getOre(args[2], Integer.parseInt(args[3])));
					}
					else if(args[1].equalsIgnoreCase("gem")) {
						if(args[2].equalsIgnoreCase("weapon")) {
							p.getInventory().addItem(StonecutterItems.getWeaponGem(args[3], Integer.parseInt(args[4]), false));
						}
						else if(args[2].equalsIgnoreCase("armor")) {
							p.getInventory().addItem(StonecutterItems.getArmorGem(args[3], Integer.parseInt(args[4]), false));
						}
					}
					else if(args[1].equalsIgnoreCase("overload")) {
						if(args[2].equalsIgnoreCase("weapon")) {
							p.getInventory().addItem(StonecutterItems.getWeaponGem(args[3], Integer.parseInt(args[4]), true));
						}
						else if(args[2].equalsIgnoreCase("armor")) {
							p.getInventory().addItem(StonecutterItems.getArmorGem(args[3], Integer.parseInt(args[4]), true));
						}
					}
					else if(args[1].equalsIgnoreCase("basic")) {
						if(args[2].equalsIgnoreCase("exp")) {
							p.getInventory().addItem(MasonItems.getExpCharm(false));
						}
						else if(args[2].equalsIgnoreCase("drop")) {
							p.getInventory().addItem(MasonItems.getDropCharm(false));
						}
						else if(args[2].equalsIgnoreCase("looting")) {
							p.getInventory().addItem(MasonItems.getLootingCharm(false));
						}
						else if(args[2].equalsIgnoreCase("traveler")) {
							p.getInventory().addItem(MasonItems.getTravelerCharm());
						}
						else if(args[2].equalsIgnoreCase("recovery")) {
							p.getInventory().addItem(MasonItems.getRecoveryCharm());
						}
					}
					else if(args[1].equalsIgnoreCase("advanced")) {
						if(args[2].equalsIgnoreCase("exp")) {
							p.getInventory().addItem(MasonItems.getExpCharm(true));
						}
						else if(args[2].equalsIgnoreCase("drop")) {
							p.getInventory().addItem(MasonItems.getDropCharm(true));
						}
						else if(args[2].equalsIgnoreCase("looting")) {
							p.getInventory().addItem(MasonItems.getLootingCharm(true));
						}
						else if(args[2].equalsIgnoreCase("hunger")) {
							p.getInventory().addItem(MasonItems.getHungerCharm());
						}
						else if(args[2].equalsIgnoreCase("secondchance")) {
							p.getInventory().addItem(MasonItems.getSecondChanceCharm());
						}
						else if(args[2].equalsIgnoreCase("quickeat")) {
							p.getInventory().addItem(MasonItems.getQuickEatCharm());
						}
					}
				}
				else {
					p = Bukkit.getPlayer(args[0]);
					if (args[1].equalsIgnoreCase("get")) {
						if(args[2].equalsIgnoreCase("essence")) {
							p.getInventory().addItem(CommonItems.getEssence(Integer.parseInt(args[3])));
						}
						else if(args[2].equalsIgnoreCase("fragment")) {
							p.getInventory().addItem(CommonItems.getEssenceFragment(Integer.parseInt(args[3])));
						}
						else if(args[2].equalsIgnoreCase("repair")) {
							p.getInventory().addItem(BlacksmithItems.getRepairItem(Integer.parseInt(args[3])));
						}
						else if(args[2].equalsIgnoreCase("durability")) {
							p.getInventory().addItem(BlacksmithItems.getDurabilityItem(Integer.parseInt(args[4]), args[3]));
						}
						else if(args[2].equalsIgnoreCase("ore")) {
							p.getInventory().addItem(StonecutterItems.getOre(args[3], Integer.parseInt(args[4])));
						}
						else if(args[2].equalsIgnoreCase("gem")) {
							if(args[3].equalsIgnoreCase("weapon")) {
								p.getInventory().addItem(StonecutterItems.getWeaponGem(args[4], Integer.parseInt(args[5]), false));
							}
							else if(args[3].equalsIgnoreCase("armor")) {
								p.getInventory().addItem(StonecutterItems.getArmorGem(args[4], Integer.parseInt(args[5]), false));
							}
						}
						else if(args[2].equalsIgnoreCase("overload")) {
							if(args[3].equalsIgnoreCase("weapon")) {
								p.getInventory().addItem(StonecutterItems.getWeaponGem(args[4], Integer.parseInt(args[5]), true));
							}
							else if(args[3].equalsIgnoreCase("armor")) {
								p.getInventory().addItem(StonecutterItems.getArmorGem(args[4], Integer.parseInt(args[5]), true));
							}
						}
						else if(args[2].equalsIgnoreCase("basic")) {
							if(args[3].equalsIgnoreCase("exp")) {
								p.getInventory().addItem(MasonItems.getExpCharm(false));
							}
							else if(args[3].equalsIgnoreCase("drop")) {
								p.getInventory().addItem(MasonItems.getDropCharm(false));
							}
							else if(args[3].equalsIgnoreCase("looting")) {
								p.getInventory().addItem(MasonItems.getLootingCharm(false));
							}
							else if(args[3].equalsIgnoreCase("traveler")) {
								p.getInventory().addItem(MasonItems.getTravelerCharm());
							}
							else if(args[3].equalsIgnoreCase("recovery")) {
								p.getInventory().addItem(MasonItems.getRecoveryCharm());
							}
						}
						else if(args[2].equalsIgnoreCase("advanced")) {
							if(args[3].equalsIgnoreCase("exp")) {
								p.getInventory().addItem(MasonItems.getExpCharm(true));
							}
							else if(args[3].equalsIgnoreCase("drop")) {
								p.getInventory().addItem(MasonItems.getDropCharm(true));
							}
							else if(args[3].equalsIgnoreCase("looting")) {
								p.getInventory().addItem(MasonItems.getLootingCharm(true));
							}
							else if(args[3].equalsIgnoreCase("hunger")) {
								p.getInventory().addItem(MasonItems.getHungerCharm());
							}
							else if(args[3].equalsIgnoreCase("secondchance")) {
								p.getInventory().addItem(MasonItems.getSecondChanceCharm());
							}
							else if(args[3].equalsIgnoreCase("quickeat")) {
								p.getInventory().addItem(MasonItems.getQuickEatCharm());
							}
						}
					}
				}
				return true;
			}
		}
		else {
			Util.sendMessage((Player)sender, "&cYou are not an admin!");
			return true;
		}
	}
}