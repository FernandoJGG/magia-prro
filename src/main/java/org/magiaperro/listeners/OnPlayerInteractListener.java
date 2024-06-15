package org.magiaperro.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.blocks.base.IBlockClickable;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.IClickable;

public class OnPlayerInteractListener implements Listener {
    
    // Escucha el evento de interacción del jugador
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        EquipmentSlot[] hands = new EquipmentSlot[]{EquipmentSlot.HAND,  EquipmentSlot.OFF_HAND};

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

        	if(!player.isSneaking()) {
        		Block block = event.getClickedBlock();
        		if(block != null && block.getState() != null && block.getState() instanceof TileState) {
        			TileState tilestate = (TileState) block.getState();
                	IBlockClickable clickedBlock = this.getClickableBlock(tilestate);
                	if (clickedBlock != null) {
                		clickedBlock.onRightClick(event, tilestate);
                        event.setCancelled(true);
                		return;
                	}
        		}
        	}
    		
        	for(EquipmentSlot hand : hands) {
        		IClickable clickedItem = this.getClickableOnHand(player, hand);
        		if(clickedItem != null) {
        			clickedItem.onRightClick(event, hand);
        			return;
        		}
        	}
        }
    }
    
//    private CustomItem hasCustomItemOnHand(Player player, EquipmentSlot hand) {
//    	ItemStack itemOnHand = player.getInventory().getItem(hand);
//    	CustomItem customItem = CustomItem.fromItemStack(itemOnHand);
//
//    	return customItem;
//    }
    
    private IClickable getClickableOnHand(Player player, EquipmentSlot hand) {
    	ItemStack itemOnHand = player.getInventory().getItem(hand);
    	CustomItem customItem = CustomItem.fromItemStack(itemOnHand);
    	if (customItem != null && customItem instanceof IClickable) {
	        return (IClickable) customItem; 
    	}
    	return null;
    }
    
    private IBlockClickable getClickableBlock(TileState tileState) {
    	if(tileState != null) {
    		CustomBlock customBlock = CustomBlock.fromTileState(tileState);
    		if (customBlock != null && customBlock instanceof IBlockClickable) {
        		return (IBlockClickable) customBlock;
    		}
    	}
    	return null;
    }

}
