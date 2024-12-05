package org.magiaperro.gui;

import java.util.UUID;
import java.util.stream.IntStream;

import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.strategies.MachineSaveStrategy;
import org.magiaperro.machines.base.IMachineData;

import net.kyori.adventure.text.Component;

public class CustomChest extends ConcurrentPersistentGui {

	public CustomChest(int size, Component title, IMachineData machineData, UUID guid) {
		super(
				size, 
				title, 
				IntStream.range(0, size).toArray(), 
				new int[0], 
				new MachineSaveStrategy(machineData, guid), 
				guid
			);
	}
	
	
	
	

}
