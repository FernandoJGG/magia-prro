package org.magiaperro.blocks;

import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.blocks.base.InventoryBlock;
import org.magiaperro.gui.CustomChest;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.factories.CustomChestGuiFactory;
import org.magiaperro.items.base.ItemID;

import net.kyori.adventure.text.Component;

public class CustomCrafter extends InventoryBlock {

	public static final int size = 9;
	
	public CustomCrafter(BlockID id) {
		super(id, ItemID.CustomCrafter, size);
	}

	@Override
	public void onRightClick(PlayerInteractEvent event, TileState tileState) {
		Player player = event.getPlayer();
		
		ConcurrentPersistentGui gui = CustomChest.getInventoryHolder(
			new CustomChestGuiFactory(
		     	/* Size */		size, 
		      	/* Title */		Component.text("Crafter que no craftea"), 
		    	/* tileState */	tileState
		    ),
			getGuidFromTileState(tileState)
	    );
		
		gui.openInterface(player);
	}

}
