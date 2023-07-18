package org.magiaperro.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

public class ItemRegistry {
    public static final Map<String, CustomItem> customItems = new HashMap<>();

    @SuppressWarnings("unused")
	public static void register() {
        Varita varita = new Varita("varita", "Toma tu magia, prro", Material.STICK, Arrays.asList("Le da su magia al prro"));
        Varita2 varitaNieve = new Varita2("varita_nieve", "Toma tu nieve, prro", Material.STICK, Arrays.asList("Le da su magia al prro"));

    }
    
    public static void registerCustomItem(CustomItem customItem) {
        customItems.put(customItem.getId(), customItem);
    }

    public static CustomItem getCustomItem(String itemId) {
        return customItems.get(itemId);
    }
}