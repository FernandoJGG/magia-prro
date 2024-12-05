package org.magiaperro.gui.base;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
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
		// Permitimos mover objetos del inventario del jugador a la GUI
		// Cuidado: No se comprueba el tipo del slot destino
		// TODO: Evitar mover a slots no input
		if (event.getRawSlot() >= inventory.getSize() && 
				event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
			return;
		}
		Bukkit.getLogger().info("Llega al evento");
        
		boolean isPersistent = isPersistentSlot(event.getRawSlot());
		if(!isPersistent) {
			super.handleClickEvent(event);
		}
		else if(this.onPlaceItem != null && isPersistent){
			// No deja colocar objetos en slots de output
			if(InventoryHelper.isPutAction(event.getAction())
					&& isOutputSlot(event.getRawSlot())) {
	        	event.setCancelled(true);
	        }
			Bukkit.getLogger().info("Llega al if");
			// Al proximo tick para tener el inventario actualizado
			Bukkit.getScheduler().runTask(Main.instance, () -> {
				Bukkit.getLogger().info("Llega al runnable");
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

		if(this.onPlaceItem != null && event.getRawSlots().stream().anyMatch(x-> isInputSlot(x))){
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
