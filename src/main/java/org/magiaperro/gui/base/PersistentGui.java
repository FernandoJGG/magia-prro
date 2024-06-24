package org.magiaperro.gui.base;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.gui.base.strategies.SaveStrategy;
import org.magiaperro.main.Main;

import net.kyori.adventure.text.Component;


public class PersistentGui extends BaseGui {

	public int[] persistentSlots;
	private SaveStrategy saveStrategy;
	private PlaceItemFunction onPlaceItem;
	
	public PersistentGui(int size, Component title, int[] persistentSlots, SaveStrategy saveStrategy) {
		super(size, title);
		this.persistentSlots = persistentSlots;
		this.saveStrategy = saveStrategy;
		
		this.load();
	}
	public PersistentGui(int size, Component title, int[] persistentSlots, SaveStrategy saveStrategy, 
			GuiGraphic[] graphics) {
		super(size, title);
		this.persistentSlots = persistentSlots;
		this.saveStrategy = saveStrategy;
		this.DrawGui(graphics);

		this.load();
	}

	@Override
	public void handleClickEvent(InventoryClickEvent event) {
		boolean isPersistent = isPersistentSlot(event.getRawSlot());
		if(!isPersistent) {
			super.handleClickEvent(event);
		}
		else if(this.onPlaceItem != null && isPersistent){
			Bukkit.getScheduler().runTask(Main.instance, () -> {
	            this.onPlaceItem.onPlace();
	        });
		}
	}
	
	@Override
	public void handleDragEvent(InventoryDragEvent event) {
		for (int slot : event.getRawSlots()) {
            if (slot < inventory.getSize() && !isPersistentSlot(slot)) {
                event.setCancelled(true);
                return;
            }
        }

		if(this.onPlaceItem != null && event.getRawSlots().stream().anyMatch(x-> isPersistentSlot(x))){
			this.onPlaceItem.onPlace();
			Bukkit.getScheduler().runTask(Main.instance, () -> {
	            this.onPlaceItem.onPlace();
	        });
		}
	}
	
	@Override
	public void handleCloseEvent(InventoryCloseEvent event) {
		this.save();
	}
	
	public void setListener(PlaceItemFunction onPlaceItem) {
		this.onPlaceItem = onPlaceItem;
	}
	
	public boolean isPersistentSlot(int slot) {
		return Arrays.stream(this.persistentSlots).anyMatch(s -> s == slot);
	}
	public SaveStrategy getSaveStrategy() {
		return saveStrategy;
	}
	
	public ItemStack[] getPersistedItems() {
		return this.getItemStacksFromPositions(persistentSlots);
	}
	
	public void save() {
		this.saveStrategy.save(this);
	}
	
	public void load() {
		this.saveStrategy.load(this);
	}

}
