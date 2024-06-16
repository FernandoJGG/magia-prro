package org.magiaperro.items.base;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.items.ItemRegistry;
import org.magiaperro.main.Keys;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;

public class CustomItem {
	
    private String itemName;
    private Material material;
    private List<String> lore;
    private ItemID id;

    public CustomItem(ItemID id, String itemName, Material material, List<String> lore) {
        this.itemName = itemName;
        this.material = material;
        this.lore = lore;
        this.id = id;
        
        ItemRegistry.registerCustomItem(this);
    }

    public String getItemName() {
        return itemName;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemID getId() {
        return id;
    }

    public ItemStack buildItemStack(int amount) {
        ItemStack itemStack = new ItemStack(material);

        // Personalizar las propiedades del item
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(itemName)
        		.decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.YELLOW)
                .decoration(TextDecoration.BOLD, true));
        itemMeta.lore(this.lore.stream().map(x -> Component.text(x)).toList());
        itemMeta.setCustomModelData(id.getIndex());
        itemMeta.getPersistentDataContainer().set(Keys.CUSTOM_ITEM_ID, PersistentDataType.INTEGER, id.getIndex());

        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);
        
        return itemStack;
    }

    public ItemStack buildItemStack() {
        return buildItemStack(1);
    }
    
    public static CustomItem fromItemStack(ItemStack itemStack) {
    	ItemID itemId = getIdFromItemStack(itemStack);
    	if(itemId != null) {
    		return fromId(itemId);
    	}
    	else {
    		return null;
    	}
    }
    
    public static ItemID getIdFromItemStack(ItemStack itemStack) {
    	if (itemStack == null) {
    		return null;
    	}
    	
    	ItemMeta itemMeta = itemStack.getItemMeta();
    	if (itemMeta != null && itemMeta.getPersistentDataContainer().has(Keys.CUSTOM_ITEM_ID, PersistentDataType.INTEGER)) {
        	Integer id = itemMeta.getPersistentDataContainer().get(Keys.CUSTOM_ITEM_ID, PersistentDataType.INTEGER);
        	return ItemID.getByIndex(id);
    	}
    	else {
    		return null;
    	}
    }
    
    public static CustomItem fromId(ItemID itemId) {
    	return ItemRegistry.getCustomItem(itemId);
    }
    
    public static CustomItem fromId(int id) {
    	return ItemRegistry.getCustomItem(id);
    }
}