package org.magiaperro.blocks.base;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.BlockRegistry;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.main.Keys;

import com.jeff_media.morepersistentdatatypes.DataType;

public abstract class CustomBlock {

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
	
    /**
     * Instancia un bloque custom con sus atributos en el PDC
     */
	public void instantiateBlock(TileState tileState) {
    	tileState.getPersistentDataContainer().set(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER, this.id.ordinal());
    	tileState.getPersistentDataContainer().set(Keys.BLOCK_INSTANCE_GUID, DataType.UUID , UUID.randomUUID());
    	
    	tileState.update(false, false);
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
    
    
    public static CustomBlock fromId(BlockID blockId) {
    	return BlockRegistry.getCustomBlock(blockId);
    }
    
    public static CustomBlock fromId(int blockId) {
    	return BlockRegistry.getCustomBlock(blockId);
    }
    
    public void onBlockDisappear(TileState tileState) {
    	Block block = tileState.getBlock();
    	CustomItem itemBlock = CustomItem.fromId(this.itemBlockId);
    	
    	block.getWorld().dropItemNaturally(block.getLocation(), itemBlock.buildItemStack());
    }


}
