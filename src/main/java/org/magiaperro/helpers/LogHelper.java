package org.magiaperro.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.TileState;

public class LogHelper {

	public static void logTileState(String message, TileState tileState) {
		logLocation(message, tileState.getLocation());
	}

	public static void logLocation(String message, Location location) {
		Bukkit.getLogger().severe(message + "\nEn: (" + 
				location.getX() + "," +
				location.getY() + "," +
				location.getZ() + "," +
				location.getWorld().toString() + ")"
			);
		
	}
}
