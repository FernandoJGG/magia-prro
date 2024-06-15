package org.magiaperro.items.base;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;


public interface IClickable{
	
	public abstract void onRightClick(PlayerInteractEvent event, EquipmentSlot hand); 

}
