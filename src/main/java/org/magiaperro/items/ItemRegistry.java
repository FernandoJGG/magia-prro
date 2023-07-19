package org.magiaperro.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

public class ItemRegistry {
    public static final Map<ItemID, CustomItem> customItems = new HashMap<>();

    @SuppressWarnings("unused")
	public static void register() {
        Varita varita = new Varita(ItemID.Varita, "Toma tu magia, prro", Material.STICK, Arrays.asList("Le da su magia al prro"));
        Varita2 varitaNieve = new Varita2(ItemID.Varita2, "Toma tu nieve, prro", Material.STICK, Arrays.asList("Le da su magia al prro"));

    }
    
    public static void registerCustomItem(CustomItem customItem) {
        customItems.put(customItem.getId(), customItem);
    }
    
    public static CustomItem getCustomItem(ItemID itemId) {
        return customItems.get(itemId);
    }
    
    public static CustomItem getCustomItem(int itemId) {
        return customItems.get(ItemID.getByIndex(itemId));
    }
}