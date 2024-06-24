package org.magiaperro.gui.base.strategies;

import java.util.UUID;

import org.bukkit.block.TileState;
import org.magiaperro.gui.base.PersistentGui;
import org.magiaperro.main.Keys;

import com.jeff_media.morepersistentdatatypes.DataType;

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
		if(this.pdc.has(Keys.BLOCK_INSTANCE_GUID)) {
			UUID pdcGuid = this.pdc.get(Keys.BLOCK_INSTANCE_GUID, DataType.UUID);
			if(this.guid.equals(pdcGuid)) {
				super.save(persistentGui);
				this.tileState.update(true, false);
			}
		}
	}

}
