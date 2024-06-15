package org.magiaperro.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.magiaperro.blocks.base.CustomBlock;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;

public class OnDestroyListener implements Listener {

    @EventHandler
	public void onDestroy (BlockDestroyEvent event) {
        Block block = event.getBlock();
        BlockState state = block.getState();
        
        if (state != null && state instanceof TileState) {
            TileState tileState = (TileState) state;
            CustomBlock customBlock = CustomBlock.fromTileState(tileState);

            if (customBlock != null) {
                customBlock.onDestroy(event);
            }
        }
		
	}
    
    @EventHandler
	public void onBreak (BlockBreakEvent event) {
        Block block = event.getBlock();
        BlockState state = block.getState();
        
        // mover a CustomBlock.fromBlock()
        if (state != null && state instanceof TileState) {
            TileState tileState = (TileState) state;
            CustomBlock customBlock = CustomBlock.fromTileState(tileState);

            if (customBlock != null) {
                customBlock.onBreak(event);
            }
        }
	}
    
    

}
