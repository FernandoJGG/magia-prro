package org.magiaperro.machines;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.magiaperro.gui.CustomChest;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.factories.CustomChestGuiFactory;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.machines.base.MachineID;
import org.magiaperro.machines.base.IMachineData;
import org.magiaperro.machines.base.InventoryMachine;

import net.kyori.adventure.text.Component;

public class CustomCrafter extends InventoryMachine {

	public static final int size = 9;
	
	public CustomCrafter(MachineID id) {
		super(id, ItemID.CustomCrafter, size);
	}

	@Override
	public void onRightClick(PlayerEvent event, IMachineData machineData) {
		Player player = event.getPlayer();
		
		ConcurrentPersistentGui gui = CustomChest.getInventoryHolder(
			new CustomChestGuiFactory(
		     	/* Size */		size, 
		      	/* Title */		Component.text("Crafter que no craftea"), 
		    	/* tileState */	machineData
		    ),
			getGuidFromMachine(machineData)
	    );
		
		gui.openInterface(player);
	}

}
