package org.magiaperro.gui.base;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.gui.base.strategies.SaveStrategy;
import org.magiaperro.helpers.InventoryHelper;
import org.magiaperro.main.Main;

import net.kyori.adventure.text.Component;


public class PersistentGui extends BaseGui {
    private final int[] inputSlots;
    private final int[] outputSlots;
	private final SaveStrategy saveStrategy;
	private PlaceItemFunction onPlaceItem;
	
	public PersistentGui(int size, Component title, int[] inputSlots, SaveStrategy saveStrategy) {
		super(size, title);
		this.inputSlots = inputSlots;
		this.saveStrategy = saveStrategy;
		this.outputSlots = new int[0];
		
		this.load();
	}
	
	public PersistentGui(int size, Component title, int[] inputSlots, int[] outputSlots, SaveStrategy saveStrategy) {
		super(size, title);
		this.inputSlots = inputSlots;
		this.outputSlots = outputSlots;
		this.saveStrategy = saveStrategy;
		
		this.load();
	}
	
	public PersistentGui(int size, Component title, int[] inputSlots, int[] outputSlots, SaveStrategy saveStrategy, 
			GuiGraphic[] graphics) {
		this(size, title, inputSlots, outputSlots, saveStrategy);
		this.DrawGui(graphics);
//		super(size, title);
//		this.inputSlots = inputSlots;
//		this.saveStrategy = saveStrategy;
//		this.DrawGui(graphics);
//		this.outputSlots = new int[0];
//
//		this.load();
	}

	@Override
	public void handleClickEvent(InventoryClickEvent event) {
		// Gestiona y precide los movimientos a la gui con shift click
		if (event.getRawSlot() >= inventory.getSize() && 
				event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
			ItemStack itemToMove = event.getCurrentItem();
            int stackSizeToMove = itemToMove.getAmount();

	        Inventory targetInventory = event.getInventory();
	        
	        for (int i = 0; i < this.size; i++) {
	            ItemStack slotItem = targetInventory.getItem(i);

	            if (slotItem == null || slotItem.getType() == Material.AIR) {
	                // Primer slot vacío encontrado
	            	if(isInputSlot(i)) {
		                this.callListener();
		                return;
	            	}
	            	else {
	            		event.setCancelled(true);
	    	        	return;
	            	}
	            } 
	            else if (slotItem.isSimilar(itemToMove) && slotItem.getAmount() < slotItem.getMaxStackSize()) {
	                // Slot con ítem similar y stack incompleto
	            	if(slotItem.getAmount() + stackSizeToMove <= slotItem.getMaxStackSize()) {
	            		if(isInputSlot(i)) {
			                this.callListener();
			                return;
		            	}
		            	else {
		            		event.setCancelled(true);
		    	        	return;
		            	}
	            	}
	            	else {
	            		if(isInputSlot(i)) {
	            			stackSizeToMove -=  slotItem.getMaxStackSize() - slotItem.getAmount();
	            		}
		            	else {
		            		event.setCancelled(true);
		    	        	return;
		            	}
	            	}
	            }
	        }
			return;
		}
		
		boolean isPersistent = isPersistentSlot(event.getRawSlot());
		if(!isPersistent) {
			// Dejamos al padre BaseGui gestionar la interfaz
			super.handleClickEvent(event);
		}
		else {
			// No deja colocar objetos en slots de output
			if(InventoryHelper.isPutAction(event.getAction())
					&& isOutputSlot(event.getRawSlot())) {
	        	event.setCancelled(true);
	        	return;
	        }
			this.callListener();
		}
	}
	
	public void callListener() {
		if(this.onPlaceItem != null) {
			// Al proximo tick para tener el inventario actualizado
			Bukkit.getScheduler().runTask(Main.instance, () -> {
	            this.onPlaceItem.onPlace();
	        });
		}
	}
	
	@Override
	public void handleDragEvent(InventoryDragEvent event) {
		for (int slot : event.getRawSlots()) {
            if (slot < inventory.getSize() && !isInputSlot(slot)) {
                event.setCancelled(true);
                return;
            }
        }

		if(event.getRawSlots().stream().anyMatch(x-> isInputSlot(x))){
			this.callListener();
		}
	}
	
	@Override
	public void handleCloseEvent(InventoryCloseEvent event) {
		this.save();
	}
	
	public void setListener(PlaceItemFunction onPlaceItem) {
		this.onPlaceItem = onPlaceItem;
	}

	public int[] getPersistentSlots() {
		return IntStream.concat(Arrays.stream(inputSlots), Arrays.stream(outputSlots))
				.toArray();
	}
	
	public boolean isPersistentSlot(int slot) {
		return Arrays.stream(getPersistentSlots()).anyMatch(s -> s == slot);
	}
	
	public boolean isInputSlot(int slot) {
		return Arrays.stream(inputSlots).anyMatch(s -> s == slot);
	}
	
	public boolean isOutputSlot(int slot) {
		return Arrays.stream(outputSlots).anyMatch(s -> s == slot);
	}
	
	public ItemStack[] getPersistedItems() {
		return this.getItemStacksFromPositions(this.getPersistentSlots());
	}
	
	protected void save() {
		this.saveStrategy.save(this);
	}
	
	protected void load() {
		this.saveStrategy.load(this);
	}

}
