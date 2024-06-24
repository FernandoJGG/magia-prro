package org.magiaperro.blocks.base;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.main.Keys;

import com.jeff_media.morepersistentdatatypes.DataType;

//TODO: Subclase con inventario sin GUI?
public abstract class InventoryBlock extends CustomBlock implements IBlockClickable {
	
	final int inventorySize;

	public InventoryBlock(BlockID id, ItemID itemBlockId, int inventorySize) {
		super(id, itemBlockId);
		this.inventorySize = inventorySize;
	}
	
	@Override
	public void instantiateBlock(TileState tileState) {
    	tileState.getPersistentDataContainer().set(Keys.PERSISTED_INVENTORY, DataType.ITEM_STACK_ARRAY , new ItemStack[this.inventorySize]);
    	tileState.getPersistentDataContainer().set(Keys.BLOCK_INSTANCE_GUID, DataType.UUID , UUID.randomUUID());

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
    		return getInventoryFromPDC(tileState);
    	}
    }
	
	
	private ItemStack[] getInventoryFromPDC(TileState tileState) {
    	ItemStack[] items = tileState.getPersistentDataContainer().get(Keys.PERSISTED_INVENTORY, DataType.ITEM_STACK_ARRAY);
    	if (items != null ) {
    		return items;
    	}
    	else {
    		return new ItemStack[this.inventorySize];
    	}
    }
	
	public void setItem(TileState tileState, ItemStack item, int index) {
    	UUID guid = getGuidFromTileState(tileState);
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null && inventoryHolder.getViewers().size() > 0) {
    		inventoryHolder.setPersistibleSlot(item, index);
    	}
    	else {
    		ItemStack[] items = getInventoryFromPDC(tileState);
    		items[index] = item;
			saveInventoryToPDC(tileState, items);
    	}
    	
    }
	
	private void saveInventoryToPDC(TileState tileState, ItemStack[] items) {
    	tileState.getPersistentDataContainer().set(Keys.PERSISTED_INVENTORY, DataType.ITEM_STACK_ARRAY, items);
    }
	

    public static UUID getGuidFromTileState(TileState tileState) {
		UUID guid = tileState.getPersistentDataContainer().get(Keys.BLOCK_INSTANCE_GUID, DataType.UUID);
    	return guid;
    }


}
