package org.magiaperro.gui.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.main.Keys;

import net.kyori.adventure.text.Component;

public abstract class BaseGui implements InventoryHolder {
	protected Inventory inventory;
    public Map<Integer, IGuiButton> botones = new HashMap<>();
	
	protected int size;
	protected Component title;

	public BaseGui(int size, Component title) {
		this.size = size;
		this.title = title;
		inventory = Bukkit.createInventory(this, size, title);
	}
	
	protected void DrawGui(GuiGraphic[] graphics) {
		this.botones = new HashMap<>();
//		inventory.clear();
		for(GuiGraphic graphic: graphics) {
            ItemStack itemStack = createItemStack(graphic);

            for (int slotIndex : graphic.slots) {
                ItemStack clonedItemStack = itemStack.clone();
                inventory.setItem(slotIndex, clonedItemStack);
                if(graphic.funcion != null) {
                    this.botones.put(slotIndex, graphic.funcion);
                }
            }
		}
	}
	
	private ItemStack createItemStack(GuiGraphic graphic) {
        ItemStack itemStack = new ItemStack(graphic.material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(graphic.text);
        itemMeta.lore(graphic.lore);
        
        itemMeta.getPersistentDataContainer().set(Keys.IS_GUI_BUTTON, PersistentDataType.BOOLEAN, graphic.funcion != null);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

	public void openInterface(Player player) {
		player.openInventory(inventory);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public void handleClickEvent(InventoryClickEvent event) {
		// Si se hace click en el inventario del jugador se ignora
		if (event.getRawSlot() >= inventory.getSize()) {
            return;
        }

		event.setCancelled(true);
		
		ItemStack clickedItem = event.getCurrentItem();
		if(clickedItem == null) {
			return;
		}
    	
    	ItemMeta itemMeta = clickedItem.getItemMeta();
    	if (itemMeta != null) {
    		Boolean isButton = itemMeta.getPersistentDataContainer().get(Keys.IS_GUI_BUTTON, PersistentDataType.BOOLEAN);
    		if(isButton != null) {
        		if(isButton) {
        			int clickedSlot = event.getSlot();
        			if (botones.containsKey(clickedSlot)) {
            			botones.get(clickedSlot).execute(event);
        			}
        		}
    		}
    	}
	}
	
	public void handleDragEvent(InventoryDragEvent event) {
		for (int slot : event.getRawSlots()) {
            if (slot < inventory.getSize()) {
                event.setCancelled(true);
                return;
            }
        }
    }

	public void handleCloseEvent(InventoryCloseEvent event) { }
	
	public ItemStack[] getItemStacksFromPositions(int[] positions) {
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int position : positions) {
            ItemStack itemStack = inventory.getItem(position);
            itemStacks.add(itemStack);
        }
        return itemStacks.toArray(new ItemStack[itemStacks.size()]);
    }
}