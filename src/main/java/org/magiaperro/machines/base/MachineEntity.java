package org.magiaperro.machines.base;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;

public class MachineEntity implements IMachineData {
    private final Entity entity;

    public MachineEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PersistentDataContainer getPDC() {
        return entity.getPersistentDataContainer();
    }

    @Override
    public boolean update() {
        // Las entidades no necesitan un `.update()` explícito como TileState
    	return true;
    }

    public Entity getEntity() {
        return entity;
    }

	@Override
	public Location getLocation() {
		return entity.getLocation();
	}

	@Override
	public World getWorld() {
		return entity.getWorld();
	}
}
