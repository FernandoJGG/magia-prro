package org.magiaperro.machines.base;

import org.bukkit.event.player.PlayerEvent;

public interface IMachineClickable {
	
	public void onRightClick(PlayerEvent event, IMachineData machineData); 

}
