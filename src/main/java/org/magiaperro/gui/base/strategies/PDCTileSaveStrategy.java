package org.magiaperro.gui.base.strategies;

import org.bukkit.block.TileState;
import org.magiaperro.gui.base.PersistentGui;

public class PDCTileSaveStrategy extends PDCSaveStrategy {
	
	protected TileState tileState;
	
	public PDCTileSaveStrategy(TileState tileState) {
		super(tileState.getPersistentDataContainer());
		this.tileState = tileState;
	}
	
	public PDCTileSaveStrategy(TileState tileState, String pdcKey) {
		super(tileState.getPersistentDataContainer(), pdcKey);
		this.tileState = tileState;
	}
	
	@Override
	public void save(PersistentGui persistentGui) {
		super.save(persistentGui);
		this.tileState.update(true, false);
	}

}
