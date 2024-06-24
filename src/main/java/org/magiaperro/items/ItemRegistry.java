package org.magiaperro.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.items.base.PlaceableItem;

public class ItemRegistry {
    public static final Map<ItemID, CustomItem> customItems = new HashMap<>();

    @SuppressWarnings("unused")
	public static void register() {
        Varita varita = new Varita(ItemID.Varita, "Toma tu magia, prro", 
        		Material.STICK, Arrays.asList("Le da su magia al prro"));
        
        Varita2 varitaNieve = new Varita2(ItemID.Varita2, "Toma tu nieve, prro", 
        		Material.STICK, Arrays.asList("Le da su magia al prro"));
        
        PlaceableItem customCrafter = new PlaceableItem(ItemID.CustomCrafter, "Krfter prron", 
        		Material.DAYLIGHT_DETECTOR, Arrays.asList("Crafteador perrón que aún no craftea"), 
        		BlockID.CustomCrafter);
        PlaceableItem customCrafter2 = new PlaceableItem(ItemID.CustomCrafter2, "Krfter prron 2", 
        		Material.PLAYER_HEAD, Arrays.asList("Crafteador perrón que aún no craftea"), 
        		BlockID.CustomCrafter);
        PlaceableItem cofreMagico = new PlaceableItem(ItemID.CofreMagico, "Kfre majiko", 
        		Material.ENCHANTING_TABLE, Arrays.asList("Un cofre que le da su magia al prro"), 
        		BlockID.CofreMagico);
        PlaceableItem horno = new PlaceableItem(ItemID.AlloyFurnace, "Horno de aleación", 
        		Material.FURNACE, Arrays.asList("Un horno que hace aleaciones :V"), 
        		BlockID.AlloyFurnace);
        
        CustomItem bronzeIngot = new CustomItem(ItemID.BronzeIngot, "Lingote de bronce", 
        		Material.COPPER_INGOT, Arrays.asList()); 
        CustomItem leadOre = new CustomItem(ItemID.LeadOre, "Mena de estaño", 
                		Material.RAW_IRON, Arrays.asList());

    }
    
    public static void registerCustomItem(CustomItem customItem) {
        customItems.put(customItem.getId(), customItem);
    }
    
    public static CustomItem getCustomItem(ItemID itemId) {
    	if (customItems.containsKey(itemId))
            return customItems.get(itemId);
    	else 
    		return null;
    }
    
    public static CustomItem getCustomItem(int id) {
    	ItemID itemId = ItemID.getByIndex(id);
        return getCustomItem(itemId);
    }
}