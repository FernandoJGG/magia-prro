package org.magiaperro.listeners;

import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.blocks.base.ILoadBlock;
import org.magiaperro.main.Keys;

public class ChunkLoadListener implements Listener {
	@EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        
        for (BlockState blockState : chunk.getTileEntities()) {
        	if(blockState instanceof TileState) {
        		TileState tileState = (TileState) blockState;
        		if (tileState.getPersistentDataContainer().has(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER)) {
                    int customBlockId = tileState.getPersistentDataContainer().get(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER);
                    CustomBlock customBlock = CustomBlock.fromId(customBlockId);
                    if (customBlock instanceof ILoadBlock) {
                        ((ILoadBlock) customBlock).onLoad(tileState);
                    }
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
	            if (tileState.getPersistentDataContainer().has(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER)) {
	                int customBlockId = tileState.getPersistentDataContainer().get(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER);
	                CustomBlock customBlock = CustomBlock.fromId(customBlockId);
	                if (customBlock instanceof ILoadBlock) {
	                    ((ILoadBlock) customBlock).onUnload(tileState);
	                }
	            }
        	}
        }
    }
}
