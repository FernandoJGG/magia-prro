package org.magiaperro.machines.base;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataContainer;

public interface IMachineData {
    PersistentDataContainer getPDC();
    boolean update();
    Location getLocation();
    World getWorld();

}
