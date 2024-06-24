package org.magiaperro.gui.base.strategies;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.magiaperro.gui.base.PersistentGui;
import org.magiaperro.main.Keys;
import org.magiaperro.main.Main;

import com.jeff_media.morepersistentdatatypes.DataType;

public class PDCSaveStrategy implements SaveStrategy {

	protected PersistentDataContainer pdc;
	protected NamespacedKey pdcKey = Keys.PERSISTED_INVENTORY;
	
	public PDCSaveStrategy(PersistentDataContainer pdc) {
		this.pdc = pdc;
	}
	
	PDCSaveStrategy(PersistentDataContainer pdc, String pdcKey) {
		this.pdc = pdc;
		this.pdcKey = new NamespacedKey(Main.instance, pdcKey);
	}
	
	@Override
	public void save(PersistentGui persistentGui) {
		ItemStack[] items = persistentGui.getPersistedItems();
		if(items != null && items.length>0) {
			pdc.set(this.pdcKey, DataType.ITEM_STACK_ARRAY, items);
		}
		
	}

	@Override
	public void load(PersistentGui persistentGui) {
		ItemStack[] items = pdc.get(this.pdcKey, DataType.ITEM_STACK_ARRAY);
		if(items != null && items.length>0) {
			for(int i=0;i < Math.min(items.length, persistentGui.persistentSlots.length);i++) {
				persistentGui.getInventory().setItem(persistentGui.persistentSlots[i], items[i]);
			}
		}
	}

}
