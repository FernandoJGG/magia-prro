package org.magiaperro.blocks.base;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;

import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.main.Keys;

import com.jeff_media.morepersistentdatatypes.DataType;

public class InventoryBlock extends CustomBlock {

	public InventoryBlock(BlockID id, ItemID itemBlockId) {
		super(id, itemBlockId);
	}
	
	@Override
	public void dropBlock(Block block) {
    	super.dropBlock(block);

    	TileState tileState = (TileState) block.getState();
    	
    	UUID guid = CustomBlock.getGuidFromTileState(tileState);
    	
    	//Comprueba si hay instancias del inventario abierto
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null) {
    		inventoryHolder.getViewers().stream().forEach(v -> v.closeInventory());
    	}
    	
    	//Dropea el inventario
    	ItemStack[] items = InventoryBlock.getInventoryFromTileState(tileState);
    	for(ItemStack item: items) {
    		if(item != null) {
        		block.getWorld().dropItemNaturally(block.getLocation(), item);
    		}
    	}
    }
	
	public static ItemStack[] getInventoryFromTileState(TileState tileState) {
    	if (tileState == null) {
    		return null;
    	}
    	ItemStack[] items = tileState.getPersistentDataContainer().get(Keys.PERSISTED_INVENTORY, DataType.ITEM_STACK_ARRAY);
    	if (items != null ) {
    		return items;
    	}
    	else {
    		return new ItemStack[0];
    	}
    }


}
