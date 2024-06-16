package org.magiaperro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.PlaceableItem;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemInHand = event.getItemInHand();
        
        PlaceableItem placeable = this.getPlaceableItem(itemInHand);
        if(placeable != null) {
        	placeable.onBlockPlace(event);
        }
    }
    
    private PlaceableItem getPlaceableItem(ItemStack itemStack) {
    	CustomItem customItem = CustomItem.fromItemStack(itemStack);
    	if (customItem != null && customItem instanceof PlaceableItem) {
	        return (PlaceableItem) customItem; 
    	}
    	return null;
    }
}
