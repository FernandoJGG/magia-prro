package org.magiaperro.gui.base.strategies;

import java.util.UUID;

import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.gui.base.PersistentGui;
import org.magiaperro.helpers.LogHelper;
import org.magiaperro.helpers.TileStateHelper;

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
	protected PersistentDataContainer getPDC() {
		return this.tileState.getPersistentDataContainer();
	}
	
	@Override
	public void save(PersistentGui persistentGui) {
    	// Lee la GUID del tileState y comprueba si es la original antes de guardar
		// TODO: Testing (evitar el cerrado en el evento onDestroy y ver si se vuelve a crear)
		
		// TODO: Actualizar para incluir el seguro de TileStateHelper
		// Además, comprobar que el otro SaveStrategy no rompa por lo mismo
		this.tileState = TileStateHelper.getUpdatedTileState(tileState);
		if(tileState != null) {
			super.save(persistentGui);
			boolean success = this.tileState.update();
			if(!success) {
				LogHelper.logTileState("Error al actualizar, el tileState ha sido modificado.", tileState);
			}
		}
		else {
			LogHelper.logTileState("Se intenta guardar un inventario en tileState no valido:", tileState);
		}
	}

}
