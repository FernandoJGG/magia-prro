package org.magiaperro.machines.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.helpers.pdc.PDCProperty;
import org.magiaperro.items.base.ItemID;

import com.jeff_media.morepersistentdatatypes.DataType;

//TODO: Subclase con inventario sin GUI? directamente no extender IBlockClickable?
public abstract class InventoryMachine extends Machine implements IMachineClickable {
	
	final int inventorySize;
	
	public static PDCProperty<ItemStack[]> inventory = new PDCProperty<ItemStack[]>("persisted_inventory", DataType.ITEM_STACK_ARRAY);

	public InventoryMachine(MachineID id, ItemID itemBlockId, int inventorySize) {
		super(id, itemBlockId);
		this.inventorySize = inventorySize;
	}
	
	@Override
	public void instantiateMachine(IMachineData machineData) {
    	inventory.setValue(machineData.getPDC(), new ItemStack[this.inventorySize]);

    	super.instantiateMachine(machineData);
	}
	
	@Override
	public void onDisappear(IMachineData machineData) {
    	super.onDisappear(machineData);
    	
    	UUID guid = getGuidFromMachine(machineData);
    	
    	//Comprueba si hay instancias del inventario abiertas y las cierra
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null && inventoryHolder.getViewers().size() > 0) {
        	List<HumanEntity> viewers = new ArrayList<>(inventoryHolder.getViewers());
    		viewers.stream().forEach(v -> v.closeInventory());
    	}
    	
    	ItemStack[] items = getInventoryFromMachine(machineData);
    	for(ItemStack item: items) {
    		if(item != null) {
    			machineData.getWorld().dropItemNaturally(machineData.getLocation(), item);
    		}
    	}
    }
	
	public ItemStack[] getInventoryFromMachine(IMachineData machineData) {
		UUID guid = getGuidFromMachine(machineData);
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	
    	if(inventoryHolder != null /*&& inventoryHolder.getViewers().size() > 0*/) {
    		return inventoryHolder.getPersistedItems();
    	}
    	else {
    		return inventory.getValue(machineData.getPDC());
    	}
    }
	
	public void setItem(IMachineData machineData, ItemStack item, int index) {
    	UUID guid = getGuidFromMachine(machineData);
    	ConcurrentPersistentGui inventoryHolder = ConcurrentPersistentGui.getInventoryHolderFromGuid(guid);
    	if(inventoryHolder != null /*&& inventoryHolder.getViewers().size() > 0*/) {
    		inventoryHolder.setPersistibleSlot(item, index);
    	}
    	else {
    		ItemStack[] items = inventory.getValue(machineData.getPDC());
    		items[index] = item;
    		inventory.setValue(machineData.getPDC(), items);
    		machineData.update();
    	}
    	
    }
	


}
