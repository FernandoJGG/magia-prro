package org.magiaperro.gui.base.strategies;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.magiaperro.gui.base.PersistentGui;
import org.magiaperro.main.Keys;
import org.magiaperro.main.Main;

import com.jeff_media.morepersistentdatatypes.DataType;

public class PDCSaveStrategy implements SaveStrategy {

	private PersistentDataContainer pdc;
	protected NamespacedKey pdcKey = Keys.PERSISTED_INVENTORY;
	
	public PDCSaveStrategy(PersistentDataContainer pdc) {
		this.pdc = pdc;
	}
	
	public PDCSaveStrategy(PersistentDataContainer pdc, String pdcKey) {
		this.pdc = pdc;
		this.pdcKey = new NamespacedKey(Main.instance, pdcKey);
	}
	
	protected PersistentDataContainer getPDC() {
		return this.pdc;
	}
	
	@Override
	public void save(PersistentGui persistentGui) {
		ItemStack[] items = persistentGui.getPersistedItems();
		if(items != null && items.length>0) {
			this.getPDC().set(this.pdcKey, DataType.ITEM_STACK_ARRAY, items);
		}
		
	}

	@Override
	public void load(PersistentGui persistentGui) {
		ItemStack[] items = this.getPDC().get(this.pdcKey, DataType.ITEM_STACK_ARRAY);
		int[] persistentSlots = persistentGui.getPersistentSlots();
		if(items != null && items.length>0) {
			for(int i=0;i < Math.min(items.length, persistentSlots.length);i++) {
				persistentGui.getInventory().setItem(persistentSlots[i], items[i]);
			}
		}
	}

}
