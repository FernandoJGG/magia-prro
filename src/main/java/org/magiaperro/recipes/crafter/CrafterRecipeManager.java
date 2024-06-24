package org.magiaperro.recipes.crafter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.inventory.ItemStack;
import org.magiaperro.recipes.base.RecipeItem;

import java.util.ArrayList;
import java.util.Iterator;

// TODO: Desechamos la idea, ya hay recetas custom en bukkit vanilla
public class CrafterRecipeManager {

    private static List<CrafterRecipe> recipes = new ArrayList<>();

    public static void register() {
    	// Añadir recetas
    }
    
    public static void addRecipe(CrafterRecipe recipe) {
        recipes.add(recipe);
    }

    public static Optional<ItemStack> matchRecipe(ItemStack[] craftingGrid) {
        if (craftingGrid.length != 9) {
            throw new IllegalArgumentException("Crafting grid must have exactly 9 elements.");
        }
        
        for (CrafterRecipe recipe : recipes) {
        	boolean match;
            if(recipe.isShapeless()) {
            	match = matchShapelessRecipe(craftingGrid, recipe.getIngredients());
            }
            else {
            	match = matchShapedRecipe(craftingGrid, recipe.getIngredients());
            }
            if(match) {
            	return Optional.of(recipe.getResult());
            }
        }
        return Optional.empty();
    }
    
    private static boolean matchShapelessRecipe(ItemStack[] craftingGrid, RecipeItem[] recipeItems) {
        List<RecipeItem> remainingRecipeItems = Stream.of(craftingGrid)
						        		.filter(is -> is != null)
						        		.map(is -> new RecipeItem(is))
						        		.collect(Collectors.toList());

        for (RecipeItem ingredient : recipeItems) {
            Iterator<RecipeItem> iterator = remainingRecipeItems.iterator();
            boolean match = false;

            while (iterator.hasNext()) {
                RecipeItem itemGrid = iterator.next();
                if (ingredient.equals(itemGrid)) {
                    iterator.remove();
                    match = true;
                    break;
                }
            }
            if(!match) {
            	// No se encontro el objeto de la receta
            	return false;
            }
        }

        // Comprueba si sobra algun item no incluido en la receta
        return remainingRecipeItems.isEmpty();
    }

    private static boolean matchShapedRecipe(ItemStack[] craftingGrid, RecipeItem[] recipeItems) {
        for (int i = 0; i < 9; i++) {
            RecipeItem gridItem = new RecipeItem(craftingGrid[i]);
            RecipeItem recipeIngredient = recipeItems[i];

            if (!gridItem.equals(recipeIngredient)) {
                return false;
            }
        }
        return true;
    	
    }

}
