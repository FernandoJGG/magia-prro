package org.magiaperro.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.items.CustomItem;
import org.magiaperro.items.interfaces.IClickable;

public class OnPlayerInteractListener implements Listener {
    
    // Escucha el evento de interacción del jugador
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	IClickable clickableMainHand = hasClickableOnHand(player, EquipmentSlot.HAND);
        	if(clickableMainHand != null) {
        		clickableMainHand.onRightClick(event, EquipmentSlot.HAND);
        	}
        	else {
        		IClickable clickableOffHand = hasClickableOnHand(player, EquipmentSlot.OFF_HAND);
            	if(clickableOffHand != null) {
            		clickableOffHand.onRightClick(event, EquipmentSlot.OFF_HAND);
            	}
        	}
        }
    }
    
    private IClickable hasClickableOnHand(Player player, EquipmentSlot hand) {
    	ItemStack itemOnHand = player.getInventory().getItem(hand);
    	CustomItem itemType = CustomItem.fromItemStack(itemOnHand);
    	if (itemType != null) {
        	if(itemType instanceof IClickable) {
	        	return (IClickable) itemType;
        	}
    	}
    	return null;
    }

}
