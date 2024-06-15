package org.magiaperro.blocks.base;

import java.util.UUID;

import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.BlockRegistry;
import org.magiaperro.main.Keys;

import com.jeff_media.morepersistentdatatypes.DataType;

public class CustomBlock {

	private BlockID id;
	
	public CustomBlock(BlockID id) {
		this.id = id;
		
		BlockRegistry.registerCustomBlock(this);
	}

	public BlockID getId() {
		return id;
	}
    
    public static CustomBlock fromTileState(TileState tileState) {
    	if (tileState == null) {
    		return null;
    	}
    	
		Integer id = tileState.getPersistentDataContainer().get(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER);
    	if(id != null) {
    		return BlockRegistry.getCustomBlock(id);
    	}
    	else {
    		return null;
    	}
    }
    
    public static UUID getGuidFromTileState(TileState tileState) {
    	if (tileState == null) {
    		return null;
    	}
    	
		UUID guid = tileState.getPersistentDataContainer().get(Keys.BLOCK_INSTANCE_GUID, DataType.UUID);
    	return guid;
    }

}
