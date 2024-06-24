package org.magiaperro.blocks.base;

import org.bukkit.block.TileState;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IBlockClickable {
	
	public void onRightClick(PlayerInteractEvent event, TileState tileState); 

}
