package org.magiaperro.recipes.base;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;

//TODO: Replantear.
public class RecipeItem {
    private final Material material;
    private final ItemID customItemId;

    public RecipeItem(Material material) {
        this.material = material;
        this.customItemId = null;
    }

    public RecipeItem(ItemID customItem) {
        this.material = null;
        this.customItemId = customItem;
    }

    public RecipeItem(ItemStack itemStack) {
        if (itemStack == null) {
            this.material = null;
            this.customItemId = null;
        } else {
            ItemID customItemId = CustomItem.getIdFromItemStack(itemStack);
            if (customItemId != null) {
                this.material = null;
                this.customItemId = customItemId;
            } else {
                this.material = itemStack.getType();
                this.customItemId = null;
            }
        }
    }
    
    public boolean isCustom() {
        return customItemId != null;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemID getCustomItem() {
        return customItemId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RecipeItem that = (RecipeItem) obj;
        if (this.isCustom() && that.isCustom()) {
            return this.customItemId == that.customItemId;
        } else if (!this.isCustom() && !that.isCustom()) {
            return this.material == that.material;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, customItemId);
    }
}
