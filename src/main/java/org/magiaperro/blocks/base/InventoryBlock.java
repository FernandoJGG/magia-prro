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

public class InventoryBlock extends CustomBlock {

	public InventoryBlock(BlockID id, ItemID itemBlockId) {
		super(id, itemBlockId);
	}
	
	@Override
	public void onBlockDisappear(TileState tileState) {
    	super.onBlockDisappear(tileState);
    	Block block = tileState.getBlock();
    	
    	UUID guid = CustomBlock.getGuidFromTileState(tileState);
    	
    	//TODO: Si hay inventario abierto, dropear a partir de dicho inventario, no del PDC
    	//Averiguar el fallo que se daba al romperse un bloque con su inventario abierto. ¿Se intentaba leer 
    	//la PDC a la vez que se escribia?
    	//TODO: Averiguar que hace el bloque concurrent, y ver si es necesario implementarlo siempre
    	//TODO: al cerrar un inventario, evitar escribir la NBT si el bloque ya no existe.
    	
    	//Comprueba si hay instancias del inventario abierto
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null && inventoryHolder.getViewers().size() > 0) {
        	List<HumanEntity> viewers = new ArrayList<>(inventoryHolder.getViewers());
    		viewers.stream().forEach(v -> v.closeInventory());

    		// Dropea objetos desde el inventario abierto
    		ItemStack[] items = inventoryHolder.getPersistedItems();
        	for(ItemStack item: items) {
        		if(item != null) {
            		block.getWorld().dropItemNaturally(block.getLocation(), item);
        		}
        	}
    		
    	}
    	else {
    		// Dropea objetos desde el PDC
        	ItemStack[] items = InventoryBlock.getInventoryFromTileState(tileState);
        	for(ItemStack item: items) {
        		if(item != null) {
            		block.getWorld().dropItemNaturally(block.getLocation(), item);
        		}
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
