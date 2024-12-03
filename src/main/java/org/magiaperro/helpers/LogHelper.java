package org.magiaperro.helpers;

import org.bukkit.Bukkit;
import org.bukkit.block.TileState;

public class LogHelper {

	public static void logTileState(String message, TileState tileState) {
		Bukkit.getLogger().info(message + "\nTileState en: (" + 
				tileState.getX() + "," +
				tileState.getY() + "," +
				tileState.getZ() + "," +
				tileState.getWorld().toString() + ")"
			);
		
	}
}
