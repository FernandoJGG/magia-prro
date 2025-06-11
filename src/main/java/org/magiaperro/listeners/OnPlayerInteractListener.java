package org.magiaperro.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.IClickable;
import org.magiaperro.items.base.IConsumable;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineBlock;
import org.magiaperro.machines.base.MachineEntity;
import org.magiaperro.machines.base.IMachineClickable;

public class OnPlayerInteractListener implements Listener {
    
    // Escucha el evento de interacción del jugador
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	//Verifica que se haya hecho click derecho
        if (event.getAction().isRightClick()) {
            Player player = event.getPlayer();
            EquipmentSlot[] hands = new EquipmentSlot[]{EquipmentSlot.HAND,  EquipmentSlot.OFF_HAND};
            
        	Block block = event.getClickedBlock();
    		if(block != null && block.getState() != null && block.getState() instanceof TileState) {
    			TileState tileState = (TileState) block.getState();
            	IMachineClickable clickedBlock = this.getClickableBlock(tileState);
            	if (clickedBlock != null) {
            		if(!player.isSneaking()) {
	            		clickedBlock.onRightClick(event, new MachineBlock(tileState));
	                    event.setCancelled(true);
	            		return;
            		}
            		else if(!event.isBlockInHand()) {
	                    event.setCancelled(true);
	                    //return;
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
    @EventHandler
    private void onPlayerEntityInteract(PlayerInteractEntityEvent event) {
    	if(event.getHand().equals(EquipmentSlot.HAND)) {
    		Entity targetEntity = event.getRightClicked();
    		Player player = event.getPlayer();
            Bukkit.getLogger().info("Se llama al evento");
            if (targetEntity != null) {
           		IMachineClickable clickedEntity = this.getClickableEntity(targetEntity);
    	       	if (clickedEntity != null) {
    	       		if(!player.isSneaking()) {
    	       			clickedEntity.onRightClick(event, new MachineEntity(targetEntity));
    	                event.setCancelled(true);
    	           		return;
    	       		}
    	       		event.setCancelled(true);
    	       	}
            }
    	}
    }
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        ItemStack consumedItem = event.getItem();

    	CustomItem customItem = CustomItem.fromItemStack(consumedItem);
    	if (customItem != null && customItem instanceof IConsumable) {
    		IConsumable consumable = (IConsumable) customItem; 
    		consumable.onConsume(event);
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
    
    private IMachineClickable getClickableBlock(TileState tileState) {
    	if(tileState != null) {
    		Machine machine = Machine.fromPDC(tileState.getPersistentDataContainer());
    		if (machine != null && machine instanceof IMachineClickable) {
        		return (IMachineClickable) machine;
    		}
    	}
    	return null;
    }
    
    private IMachineClickable getClickableEntity(Entity entity) {
    	if(entity != null) {
    		Machine machine = Machine.fromPDC(entity.getPersistentDataContainer());
    		if (machine != null && machine instanceof IMachineClickable) {
        		return (IMachineClickable) machine;
    		}
    	}
    	return null;
    }

}
