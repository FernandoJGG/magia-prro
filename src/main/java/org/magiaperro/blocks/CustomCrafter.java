package org.magiaperro.blocks;

import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.blocks.base.IBlockClickable;
import org.magiaperro.gui.CustomChest;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.factories.CustomChestGuiFactory;

import net.kyori.adventure.text.Component;

public class CustomCrafter extends CustomBlock implements IBlockClickable {

	public CustomCrafter(BlockID id) {
		super(id);
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
