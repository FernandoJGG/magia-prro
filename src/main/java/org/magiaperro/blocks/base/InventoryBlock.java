package org.magiaperro.blocks.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.helpers.pdc.TileStateProperty;
import org.magiaperro.items.base.ItemID;

import com.jeff_media.morepersistentdatatypes.DataType;

//TODO: Subclase con inventario sin GUI? directamente no extender IBlockClickable?
public abstract class InventoryBlock extends CustomBlock implements IBlockClickable {
	
	final int inventorySize;
	
	public static TileStateProperty<ItemStack[]> inventory = new TileStateProperty<ItemStack[]>("persisted_inventory", DataType.ITEM_STACK_ARRAY);

	public InventoryBlock(BlockID id, ItemID itemBlockId, int inventorySize) {
		super(id, itemBlockId);
		this.inventorySize = inventorySize;
	}
	
	@Override
	public void instantiateBlock(TileState tileState) {
    	inventory.setValue(tileState, new ItemStack[this.inventorySize]);

    	super.instantiateBlock(tileState);
	}
	
	@Override
	public void onBlockDisappear(TileState tileState) {
    	super.onBlockDisappear(tileState);
    	Block block = tileState.getBlock();
    	
    	UUID guid = getGuidFromTileState(tileState);
    	
    	//Comprueba si hay instancias del inventario abiertas y las cierra
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null && inventoryHolder.getViewers().size() > 0) {
        	List<HumanEntity> viewers = new ArrayList<>(inventoryHolder.getViewers());
    		viewers.stream().forEach(v -> v.closeInventory());
    	}
    	
    	ItemStack[] items = getInventoryFromTileState(tileState);
    	for(ItemStack item: items) {
    		if(item != null) {
        		block.getWorld().dropItemNaturally(block.getLocation(), item);
    		}
    	}
    }
	
	public ItemStack[] getInventoryFromTileState(TileState tileState) {
    	UUID guid = getGuidFromTileState(tileState);
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null && inventoryHolder.getViewers().size() > 0) {
    		return inventoryHolder.getPersistedItems();
    	}
    	else {
    		return inventory.getValue(tileState);
    	}
    }
	
	
//	private ItemStack[] getInventoryFromPDC(TileState tileState) {
//    	ItemStack[] items = this.inventory.getValue(tileState);
//
//    	if (items != null ) {
//    		return items;
//    	}
//    	else {
//    		return new ItemStack[this.inventorySize];
//    	}
//    }
	
	public void setItem(TileState tileState, ItemStack item, int index) {
    	UUID guid = getGuidFromTileState(tileState);
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null && inventoryHolder.getViewers().size() > 0) {
    		inventoryHolder.setPersistibleSlot(item, index);
    	}
    	else {
    		ItemStack[] items = inventory.getValue(tileState);
    		items[index] = item;
    		inventory.setValue(tileState, items);
    	}
    	
    }
	


}
