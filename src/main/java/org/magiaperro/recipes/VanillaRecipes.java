package org.magiaperro.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.main.Main;

public class VanillaRecipes {
	public static void register() {
		ShapedRecipe recipe = new ShapedRecipe(
				new NamespacedKey(Main.instance, "varita_recipe"),
				CustomItem.fromId(ItemID.Varita).buildItemStack());
		recipe.shape(" D", 
					 "B ");
		recipe.setIngredient('D', new ItemStack(Material.DIAMOND));
		recipe.setIngredient('B', new ItemStack(Material.BLAZE_ROD));
		Bukkit.addRecipe(recipe);
	}
}
