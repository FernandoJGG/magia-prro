package org.magiaperro.gui.base.strategies;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.TileState;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.gui.base.PersistentGui;

public class PDCTileSaveStrategy extends PDCSaveStrategy {
	
	protected TileState tileState;
	protected UUID guid;
	
	public PDCTileSaveStrategy(TileState tileState, UUID guid) {
		super(tileState.getPersistentDataContainer());
		this.tileState = tileState;
		this.guid = guid;
	}
	
	public PDCTileSaveStrategy(TileState tileState, UUID guid, String pdcKey) {
		super(tileState.getPersistentDataContainer(), pdcKey);
		this.tileState = tileState;
		this.guid = guid;
	}
	
	@Override
	public void save(PersistentGui persistentGui) {
    	// Lee la GUID del tileState y comprueba si es la original antes de guardar
		// TODO: Testing (evitar el cerrado en el evento onDestroy y ver si se vuelve a crear)
		if(CustomBlock.instanceId.hasValue(this.pdc)) {
			UUID pdcGuid = CustomBlock.instanceId.getValue(this.pdc);
			if(this.guid.equals(pdcGuid)) {
				super.save(persistentGui);
				this.tileState.update(true, false);
			}
		}
		else {
			Bukkit.getLogger().severe("Se intenta guardar a un tileState no válido en: (" + 
						tileState.getX() + "," +
						tileState.getY() + "," +
						tileState.getZ() + "," +
						tileState.getWorld().toString() + ")"
					);
		}
	}

}
