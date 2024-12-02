package org.magiaperro.listeners;

import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.blocks.base.ILoadBlock;

public class ChunkLoadListener implements Listener {
	@EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        
        for (BlockState blockState : chunk.getTileEntities()) {
        	if(blockState instanceof TileState) {
        		TileState tileState = (TileState) blockState;
        		CustomBlock customBlock = CustomBlock.fromTileState(tileState);
                if (customBlock != null && customBlock instanceof ILoadBlock) {
                    ((ILoadBlock) customBlock).onLoad(tileState);
                }
        	}
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        for (BlockState blockState : chunk.getTileEntities()) {
        	if(blockState instanceof TileState) {
        		TileState tileState = (TileState) blockState;
        		CustomBlock customBlock = CustomBlock.fromTileState(tileState);
                if (customBlock != null && customBlock instanceof ILoadBlock) {
                    ((ILoadBlock) customBlock).onUnload(tileState);
                }
        	}
        }
    }
}
