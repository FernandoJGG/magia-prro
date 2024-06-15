package org.magiaperro.blocks;

import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.blocks.base.InventoryBlock;
import org.magiaperro.blocks.base.IBlockClickable;
import org.magiaperro.gui.CustomChest;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.factories.CustomChestGuiFactory;
import org.magiaperro.items.base.ItemID;

import net.kyori.adventure.text.Component;

public class CustomCrafter extends InventoryBlock implements IBlockClickable {

	public CustomCrafter(BlockID id, ItemID itemBlockId) {
		super(id, itemBlockId);
	}

	@Override
	public void onRightClick(PlayerInteractEvent event, TileState tileState) {
		Player player = event.getPlayer();
		
		ConcurrentPersistentGui gui = CustomChest.getInventoryHolder(
			new CustomChestGuiFactory(
		     	/* Size */		27, 
		      	/* Title */		Component.text("Crafter que no craftea"), 
		    	/* tileState */	tileState
		    ),
			CustomBlock.getGuidFromTileState(tileState)
	    );
		
		gui.openInterface(player);
	}

}
