package org.magiaperro.gui.base.factories;

import java.util.UUID;

import org.magiaperro.gui.CustomChest;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.machines.base.IMachineData;

import net.kyori.adventure.text.Component;

public class CustomChestGuiFactory implements ConcurrentGuiFactory {
	private int size;
	private Component title;
	private IMachineData machineData;
	
	public CustomChestGuiFactory(int size, Component title, IMachineData machineData) {
		this.size = size;
		this.title = title;
		this.machineData = machineData;
	}


	@Override
	public ConcurrentPersistentGui createGui(UUID id) {
		return new CustomChest(size, title, machineData, id);
	}

}