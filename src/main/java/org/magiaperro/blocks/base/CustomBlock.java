package org.magiaperro.blocks.base;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.BlockRegistry;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.main.Keys;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.jeff_media.morepersistentdatatypes.DataType;

public class CustomBlock {

	private BlockID id;
	protected ItemID itemBlockId;
	
	public CustomBlock(BlockID id, ItemID itemBlockId) {
		this.id = id;
		this.itemBlockId = itemBlockId;
		
		BlockRegistry.registerCustomBlock(this);
	}

	public BlockID getId() {
		return id;
	}
    
    public static CustomBlock fromTileState(TileState tileState) {
    	if(tileState != null && tileState.getPersistentDataContainer().has(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER)) {
    		Integer id = tileState.getPersistentDataContainer().get(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER);
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
    
    public static CustomBlock fromId(BlockID blockId) {
    	return BlockRegistry.getCustomBlock(blockId);
    }
    
    public static CustomBlock fromId(int blockId) {
    	return BlockRegistry.getCustomBlock(blockId);
    }
    
    public void onDestroy(BlockDestroyEvent event) {
    	Block block = event.getBlock();

    	event.setWillDrop(false);
    	this.dropBlock(block);
    }
    
    public void onBreak(BlockBreakEvent event) {
    	Block block = event.getBlock();
    	
    	event.setDropItems(false);
    	this.dropBlock(block);
    }
    
    public void dropBlock(Block block) {
    	CustomItem itemBlock = CustomItem.fromId(this.itemBlockId);
    	
    	block.getWorld().dropItemNaturally(block.getLocation(), itemBlock.buildItemStack());
    }

}
