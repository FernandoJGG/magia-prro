package org.magiaperro.recipes.crafter;

import org.bukkit.inventory.ItemStack;
import org.magiaperro.recipes.base.RecipeItem;

public class CrafterRecipe {
    private final RecipeItem[] ingredients; // 3x3 grid
    private final ItemStack result;
    private final boolean isShapeless;

    public CrafterRecipe(RecipeItem[] ingredients, ItemStack result, boolean isShapeless) {
        if (!isShapeless && ingredients.length != 9) {
            throw new IllegalArgumentException("Una receta shaped debe definir los 9 espacios del grid");
        }
        else if (isShapeless && ingredients.length > 9) {
            throw new IllegalArgumentException("Una receta shapeless no puede contener más de 9 ingredientes");
        }
        this.ingredients = ingredients;
        this.result = result;
        this.isShapeless = isShapeless;
    }
    
    public CrafterRecipe(RecipeItem[] ingredients, ItemStack result) {
        this(ingredients, result, false);
    }

    public RecipeItem[] getIngredients() {
        return this.ingredients;
    }

    public ItemStack getResult() {
        return this.result;
    }
    
    public boolean isShapeless() {
        return this.isShapeless;
    }

}
