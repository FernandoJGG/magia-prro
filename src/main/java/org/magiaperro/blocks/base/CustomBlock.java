package org.magiaperro.blocks.base;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.BlockRegistry;
import org.magiaperro.helpers.pdc.TileStateProperty;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
//import org.magiaperro.main.Keys;

import com.jeff_media.morepersistentdatatypes.DataType;

//TODO: Remodelar por CustomMachine
//Una maquina puede ser tanto un tilestate, una entidad, cualquier cosa que incluya un PDC
//Una posible manera seria crear una clase IPersistible, que luego implementaria con 
//IPersistibleBlock, IPersistibleEntity, cada una teniendo como parametro su TileState, Entity, etc
public abstract class CustomBlock {

	private BlockID id;
	protected ItemID itemBlockId;
	
	public static TileStateProperty<Integer> blockId = new TileStateProperty<Integer>("custom_block_id", PersistentDataType.INTEGER);
	public static TileStateProperty<UUID> instanceId = new TileStateProperty<UUID>("block_instance_guid", DataType.UUID);
	
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
		blockId.setValue(tileState, this.id.getIndex());
		instanceId.setValue(tileState, UUID.randomUUID());
    	
    	tileState.update(false, false);
	}
    
    public static CustomBlock fromTileState(TileState tileState) {
    	if(tileState != null && blockId.hasValue(tileState)) {
    		Integer id = blockId.getValue(tileState);
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

    public static UUID getGuidFromTileState(TileState tileState) {
		UUID guid = instanceId.getValue(tileState);
    	return guid;
    }


}
