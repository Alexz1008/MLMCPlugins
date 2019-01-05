package me.Neoblade298.NeoProfessions.Methods;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Neoblade298.NeoProfessions.Main;
import me.Neoblade298.NeoProfessions.Items.CommonItems;
import me.Neoblade298.NeoProfessions.Items.IngredientRecipeItems;
import me.Neoblade298.NeoProfessions.Items.Tier1RecipeItems;
import me.Neoblade298.NeoProfessions.Utilities.CulinarianUtils;
import me.Neoblade298.NeoProfessions.Utilities.Util;
import net.milkbowl.vault.economy.Economy;

public class CulinarianMethods {
	
	Main main;
	Economy econ;
	
	// Constants
	final static int GARNISH_COST = 500;
	final static int GARNISH_ESSENCE = 1;
	final static int PRESERVE_COST = 500;
	final static int PRESERVE_ESSENCE = 1;
	final static int SPICE_COST = 500;
	final static int SPICE_ESSENCE = 1;
	final static int CRAFT_COST = 50;
	
	public CulinarianMethods(Main main) {
		this.main = main;
		this.econ = main.getEconomy();
	}
	
	public void garnish(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(!item.equals(Material.AIR)) {
			int level = CulinarianUtils.getFoodLevel(item);
			if(p.hasPermission("culinarian.garnish." + level)) {
				boolean isFood = false;
				boolean isBoosted = false;
				for(String line : item.getItemMeta().getLore()) {
					if(line.contains("Recipe")) {
						isFood = true;
					}
					if(line.contains("Garnished")) {
						isBoosted = true;
					}
					if(line.contains("Spiced")) {
						isBoosted = true;
					}
					if(line.contains("Preserved")) {
						isBoosted = true;
					}
				}
				if(isFood) {
					if(!isBoosted) {
						if(p.getInventory().containsAtLeast(CommonItems.getEssence(level), GARNISH_ESSENCE)) {
							if(econ.has(p, GARNISH_COST)) {
								ItemMeta meta = item.getItemMeta();
								ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
								lore.add("�9Garnished (1.3x Attribute Boost)");
								meta.setLore(lore);
								item.setItemMeta(meta);
								p.getInventory().removeItem(Util.setAmount(CommonItems.getEssence(level), GARNISH_ESSENCE));
								econ.withdrawPlayer(p, GARNISH_COST);
								Util.sendMessage(p, "&7Successfully garnished dish!");
							}
							else {
								Util.sendMessage(p, "&cYou gold the materials to garnish this!");
							}
						}
						else {
							Util.sendMessage(p, "&cYou lack the materials to garnish this!");
						}
					}
					else {
						Util.sendMessage(p, "&cThis food cannot be boosted any further!");
					}
				}
				else {
					Util.sendMessage(p, "&cCannot garnish this item!");
				}
			}
			else {
				Util.sendMessage(p, "&cYou do not yet have the required skill!");
			}
		}
		else {
			Util.sendMessage(p, "&cMain hand is empty!");
		}
	}
	
	public void preserve(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(!item.equals(Material.AIR)) {
			int level = CulinarianUtils.getFoodLevel(item);
			if(p.hasPermission("culinarian.preserve." + level)) {
				boolean isFood = false;
				boolean isBoosted = false;
				for(String line : item.getItemMeta().getLore()) {
					if(line.contains("Recipe")) {
						isFood = true;
					}
					if(line.contains("Garnished")) {
						isBoosted = true;
					}
					if(line.contains("Spiced")) {
						isBoosted = true;
					}
					if(line.contains("Preserved")) {
						isBoosted = true;
					}
				}
				if(isFood) {
					if(!isBoosted) {
						if(p.getInventory().containsAtLeast(CommonItems.getEssence(level), PRESERVE_ESSENCE)) {
							if(econ.has(p, PRESERVE_COST)) {
								ItemMeta meta = item.getItemMeta();
								ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
								lore.add("�9Preserved (1.3x Duration Boost)");
								meta.setLore(lore);
								item.setItemMeta(meta);
								p.getInventory().removeItem(Util.setAmount(CommonItems.getEssence(level), PRESERVE_ESSENCE));
								econ.withdrawPlayer(p, PRESERVE_COST);
								Util.sendMessage(p, "&7Successfully preserved dish!");
							}
							else {
								Util.sendMessage(p, "&cYou gold the materials to preserve  this!");
							}
						}
						else {
							Util.sendMessage(p, "&cYou lack the materials to preserve this!");
						}
					}
					else {
						Util.sendMessage(p, "&cThis food cannot be boosted any further!");
					}
				}
				else {
					Util.sendMessage(p, "&cCannot preserve this item!");
				}
			}
			else {
				Util.sendMessage(p, "&cYou do not yet have the required skill!");
			}
		}
		else {
			Util.sendMessage(p, "&cMain hand is empty!");
		}
	}
	
	public void spice(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(!item.equals(Material.AIR)) {
			int level = CulinarianUtils.getFoodLevel(item);
			if(p.hasPermission("culinarian.spice." + level)) {
				boolean isFood = false;
				boolean isBoosted = false;
				for(String line : item.getItemMeta().getLore()) {
					if(line.contains("Recipe")) {
						isFood = true;
					}
					if(line.contains("Garnished")) {
						isBoosted = true;
					}
					if(line.contains("Spiced")) {
						isBoosted = true;
					}
					if(line.contains("Preserved")) {
						isBoosted = true;
					}
				}
				if(isFood) {
					if(!isBoosted) {
						if(p.getInventory().containsAtLeast(CommonItems.getEssence(level), SPICE_ESSENCE)) {
							if(econ.has(p, SPICE_COST)) {
								ItemMeta meta = item.getItemMeta();
								ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
								lore.add("�9Spiced (1.3x Restoration Boost)");
								meta.setLore(lore);
								item.setItemMeta(meta);
								p.getInventory().removeItem(Util.setAmount(CommonItems.getEssence(level), SPICE_ESSENCE));
								econ.withdrawPlayer(p, SPICE_COST);
								Util.sendMessage(p, "&7Successfully spiced dish!");
							}
							else {
								Util.sendMessage(p, "&cYou lack the gold to spice this!");
							}
						}
						else {
							Util.sendMessage(p, "&cYou lack the materials to spice this!");
						}
					}
					else {
						Util.sendMessage(p, "&cThis food cannot be boosted any further!");
					}
				}
				else {
					Util.sendMessage(p, "&cCannot spice this item!");
				}
			}
			else {
				Util.sendMessage(p, "&cYou do not yet have the required skill!");
			}
		}
		else {
			Util.sendMessage(p, "&cMain hand is empty!");
		}
	}
	
	public void parseIngredient(Player p, String recipe) {
		if(p.hasPermission("culinarian.craft.1")) {
			if(recipe.equalsIgnoreCase("salt")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getSaltRecipe(), IngredientRecipeItems.getSalt(), true, "Salt");
			}
			else if(recipe.equalsIgnoreCase("spices")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getSpicesRecipe(), IngredientRecipeItems.getSpices(), false, "Spices");
			}
			else if(recipe.equalsIgnoreCase("greens")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getGreensRecipe(), IngredientRecipeItems.getGreens(), false, "Greens");
			}
			else if(recipe.equalsIgnoreCase("toast")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getToastRecipe(), IngredientRecipeItems.getToast(), true, "Toast");
			}
			else if(recipe.equalsIgnoreCase("oil")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getOilRecipe(), IngredientRecipeItems.getOil(), true, "Oil");
			}
			else if(recipe.equalsIgnoreCase("tomato")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getTomatoRecipe(), IngredientRecipeItems.getTomato(), false, "Tomato");
			}
			else if(recipe.equalsIgnoreCase("butter")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getButterRecipe(), IngredientRecipeItems.getButter(), false, "Butter");
			}
			else if(recipe.equalsIgnoreCase("lemon")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getLemonRecipe(), IngredientRecipeItems.getLemon(), false, "Lemon");
			}
			else if(recipe.equalsIgnoreCase("corn")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getCornRecipe(), IngredientRecipeItems.getCorn(), false, "Corn");
			}
			else if(recipe.equalsIgnoreCase("honey")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getHoneyRecipe(), IngredientRecipeItems.getHoney(), false, "Honey");
			}
			else if(recipe.equalsIgnoreCase("yeast")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getYeastRecipe(), IngredientRecipeItems.getYeast(), false, "Yeast");
			}
			else if(recipe.equalsIgnoreCase("beetroot sauce")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getBeetrootSauceRecipe(), IngredientRecipeItems.getBeetrootSauce(), false, "Beetroot Sauce");
			}
			else if(recipe.equalsIgnoreCase("pasta")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getPastaRecipe(), IngredientRecipeItems.getPasta(), false, "Pasta");
			}
			else if(recipe.equalsIgnoreCase("onion")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getOnionRecipe(), IngredientRecipeItems.getOnion(), false, "Onion");
			}
			else if(recipe.equalsIgnoreCase("hops")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getHopsRecipe(), IngredientRecipeItems.getHops(), false, "Hops");
			}
			else if(recipe.equalsIgnoreCase("cheese")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getCheeseRecipe(), IngredientRecipeItems.getCheese(), true, "Cheese");
			}
			else if(recipe.equalsIgnoreCase("tortilla")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getTortillaRecipe(), IngredientRecipeItems.getTortilla(), false, "Tortilla");
			}
			else if(recipe.equalsIgnoreCase("exotic greens")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getExoticGreensRecipe(), IngredientRecipeItems.getExoticGreens(), false, "Exotic Greens");
			}
			else if(recipe.equalsIgnoreCase("rice")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getRiceRecipe(), IngredientRecipeItems.getRice(), true, "Rice");
			}
			else if(recipe.equalsIgnoreCase("popcorn")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getPopcornRecipe(), IngredientRecipeItems.getPopcorn(), true, "Popcorn");
			}
			else if(recipe.equalsIgnoreCase("pepper")) {
				CulinarianUtils.craftRecipeMax(p, econ, IngredientRecipeItems.getPepperRecipe(), IngredientRecipeItems.getPepper(), false, "Pepper");
			}
		}
		else {
			Util.sendMessage(p, "&cYou do not yet have the required skill!");
		}
	}
	
	public void parseIngredient(Player p, String recipe, int amount) {
		if(p.hasPermission("culinarian.craft.1")) {
			if(recipe.equalsIgnoreCase("salt")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getSaltRecipe(), IngredientRecipeItems.getSalt(), true, "Salt");
			}
			else if(recipe.equalsIgnoreCase("spices")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getSpicesRecipe(), IngredientRecipeItems.getSpices(), false, "Spices");
			}
			else if(recipe.equalsIgnoreCase("greens")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getGreensRecipe(), IngredientRecipeItems.getGreens(), false, "Greens");
			}
			else if(recipe.equalsIgnoreCase("toast")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getToastRecipe(), IngredientRecipeItems.getToast(), true, "Toast");
			}
			else if(recipe.equalsIgnoreCase("oil")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getOilRecipe(), IngredientRecipeItems.getOil(), true, "Oil");
			}
			else if(recipe.equalsIgnoreCase("tomato")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getTomatoRecipe(), IngredientRecipeItems.getTomato(), false, "Tomato");
			}
			else if(recipe.equalsIgnoreCase("butter")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getButterRecipe(), IngredientRecipeItems.getButter(), false, "Butter");
			}
			else if(recipe.equalsIgnoreCase("lemon")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getLemonRecipe(), IngredientRecipeItems.getLemon(), false, "Lemon");
			}
			else if(recipe.equalsIgnoreCase("corn")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getCornRecipe(), IngredientRecipeItems.getCorn(), false, "Corn");
			}
			else if(recipe.equalsIgnoreCase("honey")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getHoneyRecipe(), IngredientRecipeItems.getHoney(), false, "Honey");
			}
			else if(recipe.equalsIgnoreCase("yeast")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getYeastRecipe(), IngredientRecipeItems.getYeast(), false, "Yeast");
			}
			else if(recipe.equalsIgnoreCase("beetroot sauce")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getBeetrootSauceRecipe(), IngredientRecipeItems.getBeetrootSauce(), false, "Beetroot Sauce");
			}
			else if(recipe.equalsIgnoreCase("pasta")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getPastaRecipe(), IngredientRecipeItems.getPasta(), false, "Pasta");
			}
			else if(recipe.equalsIgnoreCase("onion")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getOnionRecipe(), IngredientRecipeItems.getOnion(), false, "Onion");
			}
			else if(recipe.equalsIgnoreCase("hops")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getHopsRecipe(), IngredientRecipeItems.getHops(), false, "Hops");
			}
			else if(recipe.equalsIgnoreCase("cheese")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getCheeseRecipe(), IngredientRecipeItems.getCheese(), true, "Cheese");
			}
			else if(recipe.equalsIgnoreCase("tortilla")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getTortillaRecipe(), IngredientRecipeItems.getTortilla(), false, "Tortilla");
			}
			else if(recipe.equalsIgnoreCase("exotic greens")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getExoticGreensRecipe(), IngredientRecipeItems.getExoticGreens(), false, "Exotic Greens");
			}
			else if(recipe.equalsIgnoreCase("rice")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getRiceRecipe(), IngredientRecipeItems.getRice(), true, "Rice");
			}
			else if(recipe.equalsIgnoreCase("popcorn")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getPopcornRecipe(), IngredientRecipeItems.getPopcorn(), true, "Popcorn");
			}
			else if(recipe.equalsIgnoreCase("pepper")) {
				CulinarianUtils.craftRecipe(p, econ, amount, IngredientRecipeItems.getPepperRecipe(), IngredientRecipeItems.getPepper(), false, "Pepper");
			}
		}
		else {
			Util.sendMessage(p, "&cYou do not yet have the required skill!");
		}
	}
	
	public void parseTier1(Player p, String recipe) {
		if(p.hasPermission("culinarian.craft.1")) {
			if(recipe.equalsIgnoreCase("cured flesh")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getCuredFleshRecipe(), Tier1RecipeItems.getCuredFlesh(), true, "Cured Flesh");
			}
			else if(recipe.equalsIgnoreCase("sunflower seeds")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getSunflowerSeedsRecipe(), Tier1RecipeItems.getSunflowerSeeds(), false, "Sunflower Seeds");
			}
			else if(recipe.equalsIgnoreCase("boiled egg")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getBoiledEggRecipe(), Tier1RecipeItems.getBoiledEgg(), true, "Boiled Egg");
			}
			else if(recipe.equalsIgnoreCase("ice cream")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getIceCreamRecipe(), Tier1RecipeItems.getIceCream(), false, "Ice Cream");
			}
			else if(recipe.equalsIgnoreCase("hot chocolate")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getHotChocolateRecipe(), Tier1RecipeItems.getHotChocolate(), true, "Hot Chocolate");
			}
			else if(recipe.equalsIgnoreCase("green tea")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getGreenTeaRecipe(), Tier1RecipeItems.getGreenTea(), false, "Green Tea");
			}
			else if(recipe.equalsIgnoreCase("barley tea")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getBarleyTeaRecipe(), Tier1RecipeItems.getBarleyTea(), false, "Barley Tea");
			}
			else if(recipe.equalsIgnoreCase("mashed potatoes")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getMashedPotatoesRecipe(), Tier1RecipeItems.getMashedPotatoes(), false, "Mashed Potatoes");
			}
			else if(recipe.equalsIgnoreCase("spinach")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getSpinachRecipe(), Tier1RecipeItems.getSpinach(), false, "Spinach");
			}
			else if(recipe.equalsIgnoreCase("chocolate truffle")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getChocolateTruffleRecipe(), Tier1RecipeItems.getChocolateTruffle(), false, "Chocolate Truffle");
			}
			else if(recipe.equalsIgnoreCase("musli")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getMusliRecipe(), Tier1RecipeItems.getMusli(), false, "Musli");
			}
			else if(recipe.equalsIgnoreCase("candied apple")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getCandiedAppleRecipe(), Tier1RecipeItems.getCandiedApple(), false, "Candied Apple");
			}
			else if(recipe.equalsIgnoreCase("mac and cheese")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getMacAndCheeseRecipe(), Tier1RecipeItems.getMacAndCheese(), false, "Mac and Cheese");
			}
			else if(recipe.equalsIgnoreCase("chocolate milk")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getChocolateMilkRecipe(), Tier1RecipeItems.getChocolateMilk(), false, "Chocolate Milk");
			}
			else if(recipe.equalsIgnoreCase("cheese tortilla")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getCheeseTortillaRecipe(), Tier1RecipeItems.getCheeseTortilla(), false, "Cheese Tortilla");
			}
			else if(recipe.equalsIgnoreCase("sushi")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getSushiRecipe(), Tier1RecipeItems.getSushi(), false, "Sushi");
			}
			else if(recipe.equalsIgnoreCase("pottage")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getPottageRecipe(), Tier1RecipeItems.getPottage(), false, "Pottage");
			}
			else if(recipe.equalsIgnoreCase("buttered popcorn")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getButteredPopcornRecipe(), Tier1RecipeItems.getButteredPopcorn(), false, "Buttered Popcorn");
			}
			else if(recipe.equalsIgnoreCase("chips and salsa")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getChipsAndSalsaRecipe(), Tier1RecipeItems.getChipsAndSalsa(), false, "Chips and Salsa");
			}
			else if(recipe.equalsIgnoreCase("gruel")) {
				CulinarianUtils.craftRecipeMax(p, econ, Tier1RecipeItems.getGruelRecipe(), Tier1RecipeItems.getGruel(), false, "Gruel");
			}
		}
		else {
			Util.sendMessage(p, "&cYou do not yet have the required skill!");
		}
	}
	
	public void parseTier1(Player p, String recipe, int amount) {
		if(p.hasPermission("culinarian.craft.1")) {
			if(recipe.equalsIgnoreCase("cured flesh")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getCuredFleshRecipe(), Tier1RecipeItems.getCuredFlesh(), true, "Cured Flesh");
			}
			else if(recipe.equalsIgnoreCase("sunflower seeds")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getSunflowerSeedsRecipe(), Tier1RecipeItems.getSunflowerSeeds(), false, "Sunflower Seeds");
			}
			else if(recipe.equalsIgnoreCase("boiled egg")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getBoiledEggRecipe(), Tier1RecipeItems.getBoiledEgg(), true, "Boiled Egg");
			}
			else if(recipe.equalsIgnoreCase("ice cream")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getIceCreamRecipe(), Tier1RecipeItems.getIceCream(), false, "Ice Cream");
			}
			else if(recipe.equalsIgnoreCase("hot chocolate")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getHotChocolateRecipe(), Tier1RecipeItems.getHotChocolate(), true, "Hot Chocolate");
			}
			else if(recipe.equalsIgnoreCase("green tea")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getGreenTeaRecipe(), Tier1RecipeItems.getGreenTea(), false, "Green Tea");
			}
			else if(recipe.equalsIgnoreCase("barley tea")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getBarleyTeaRecipe(), Tier1RecipeItems.getBarleyTea(), false, "Barley Tea");
			}
			else if(recipe.equalsIgnoreCase("mashed potatoes")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getMashedPotatoesRecipe(), Tier1RecipeItems.getMashedPotatoes(), false, "Mashed Potatoes");
			}
			else if(recipe.equalsIgnoreCase("spinach")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getSpinachRecipe(), Tier1RecipeItems.getSpinach(), false, "Spinach");
			}
			else if(recipe.equalsIgnoreCase("chocolate truffle")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getChocolateTruffleRecipe(), Tier1RecipeItems.getChocolateTruffle(), false, "Chocolate Truffle");
			}
			else if(recipe.equalsIgnoreCase("musli")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getMusliRecipe(), Tier1RecipeItems.getMusli(), false, "Musli");
			}
			else if(recipe.equalsIgnoreCase("candied apple")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getCandiedAppleRecipe(), Tier1RecipeItems.getCandiedApple(), false, "Candied Apple");
			}
			else if(recipe.equalsIgnoreCase("mac and cheese")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getMacAndCheeseRecipe(), Tier1RecipeItems.getMacAndCheese(), false, "Mac and Cheese");
			}
			else if(recipe.equalsIgnoreCase("chocolate milk")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getChocolateMilkRecipe(), Tier1RecipeItems.getChocolateMilk(), false, "Chocolate Milk");
			}
			else if(recipe.equalsIgnoreCase("cheese tortilla")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getCheeseTortillaRecipe(), Tier1RecipeItems.getCheeseTortilla(), false, "Cheese Tortilla");
			}
			else if(recipe.equalsIgnoreCase("sushi")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getSushiRecipe(), Tier1RecipeItems.getSushi(), false, "Sushi");
			}
			else if(recipe.equalsIgnoreCase("pottage")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getPottageRecipe(), Tier1RecipeItems.getPottage(), false, "Pottage");
			}
			else if(recipe.equalsIgnoreCase("buttered popcorn")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getButteredPopcornRecipe(), Tier1RecipeItems.getButteredPopcorn(), false, "Buttered Popcorn");
			}
			else if(recipe.equalsIgnoreCase("chips and salsa")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getChipsAndSalsaRecipe(), Tier1RecipeItems.getChipsAndSalsa(), false, "Chips and Salsa");
			}
			else if(recipe.equalsIgnoreCase("gruel")) {
				CulinarianUtils.craftRecipe(p, econ, amount, Tier1RecipeItems.getGruelRecipe(), Tier1RecipeItems.getGruel(), false, "Gruel");
			}
		}
		else {
			Util.sendMessage(p, "&cYou do not yet have the required skill!");
		}
	}

}
