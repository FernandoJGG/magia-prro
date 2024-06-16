package org.magiaperro.gui.base;

import java.util.Arrays;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.gui.base.strategies.SaveStrategy;

import net.kyori.adventure.text.Component;


public class PersistentGui extends BaseGui {

	public int[] persistentSlots;
	private SaveStrategy saveStrategy;
	
	public PersistentGui(int size, Component title, int[] persistentSlots, SaveStrategy saveStrategy) {
		super(size, title);
		this.persistentSlots = persistentSlots;
		this.saveStrategy = saveStrategy;
		
		this.load();
	}
	public PersistentGui(int size, Component title, int[] persistentSlots, SaveStrategy saveStrategy, GuiGraphic[] graphics) {
		super(size, title);
		this.persistentSlots = persistentSlots;
		this.saveStrategy = saveStrategy;
		this.DrawGui(graphics);

		this.load();
	}

	@Override
	public void handleClickEvent(InventoryClickEvent event) {
		if(!isPersistentSlot(event.getRawSlot())) {
			super.handleClickEvent(event);
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
	}
	
	@Override
	public void handleCloseEvent(InventoryCloseEvent event) {
		this.save();
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
