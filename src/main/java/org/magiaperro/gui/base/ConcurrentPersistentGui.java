package org.magiaperro.gui.base;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.magiaperro.gui.base.factories.ConcurrentGuiFactory;
import org.magiaperro.gui.base.strategies.SaveStrategy;

import net.kyori.adventure.text.Component;

public class ConcurrentPersistentGui extends PersistentGui {

	private UUID inventoryGuid;
	
    public static final ConcurrentMap<UUID, ConcurrentPersistentGui> inventoryCache = new ConcurrentHashMap<>();
    
	public ConcurrentPersistentGui(int size, Component title, int[] persistentSlots, SaveStrategy strategy, GuiGraphic[] graphics, UUID guid) {
		super(size, title, persistentSlots, strategy, graphics);
		
		this.inventoryGuid = guid;
	}
	public ConcurrentPersistentGui(int size, Component title, int[] persistentSlots, SaveStrategy strategy, UUID guid) {
		super(size, title, persistentSlots, strategy);
		
		this.inventoryGuid = guid;
	}
	
	public static ConcurrentPersistentGui getInventoryHolder(ConcurrentGuiFactory factory, UUID guid) {
		try {
			synchronized (inventoryCache) {
	            if (inventoryCache.containsKey(guid)) {
	            	ConcurrentPersistentGui inventory = inventoryCache.get(guid);
	                return inventory;
	            } else {
	            	ConcurrentPersistentGui inventory = factory.createGui(guid);
	            	inventoryCache.put(guid, inventory);
	                return inventory;
	            }
			}
		}
		catch(Exception e) {
			Bukkit.getLogger().severe("Error al instanciar inventario concurrente"
					+ "\nFactoria:" + factory.getClass().getName()
					+ "\nGUID:" + guid
					+ "\nExcepcion:" + e);
			throw e;
		}
	}
	
	public static ConcurrentPersistentGui getInventoryHolderFromGuid(UUID guid) {
		if(inventoryCache.containsKey(guid)) {
			return inventoryCache.get(guid);
		}
		return null;
	}
	
	@Override
	public void handleCloseEvent(InventoryCloseEvent event) {
		try {
	        synchronized (inventoryCache) {
	        	if (this.getViewers().size() <= 1) {
	                this.getSaveStrategy().save(this);
	                inventoryCache.remove(this.inventoryGuid);
	            }
	        }
		}
		catch(Exception e) {
			Bukkit.getLogger().severe("Error al instanciar inventario concurrente"
					+ "\nClase:" + this.getClass().getName()
					+ "\nGUID:" + this.inventoryGuid
					+ "\nExcepcion:" + e);
			throw e;
		}
    }
	
	public List<HumanEntity> getViewers() {
		return this.getInventory().getViewers();
	}


}
