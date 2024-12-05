package org.magiaperro.machines.base;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;

public class MachineBlock implements IMachineData {
    private TileState tileState;

    public MachineBlock(TileState tileState) {
        this.tileState = tileState;
    }

    @Override
    public PersistentDataContainer getPDC() {
        return tileState.getPersistentDataContainer();
    }

    @Override
    public boolean update() {
        return tileState.update();
    }
    
    public TileState getTileState() {
        return tileState;
    }

	@Override
	public Location getLocation() {
		return tileState.getLocation();
	}

	@Override
	public World getWorld() {
		return tileState.getWorld();
	}

	public void setTileState(TileState updatedTileState) {
		this.tileState = updatedTileState;
	}
}
