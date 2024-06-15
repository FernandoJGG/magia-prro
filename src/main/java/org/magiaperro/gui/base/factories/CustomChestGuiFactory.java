package org.magiaperro.gui.base.factories;

import java.util.UUID;

import org.bukkit.block.TileState;
import org.magiaperro.gui.CustomChest;
import org.magiaperro.gui.base.ConcurrentPersistentGui;

import net.kyori.adventure.text.Component;

public class CustomChestGuiFactory implements ConcurrentGuiFactory {
	private int size;
	private Component title;
	private TileState tilestate;
	
	public CustomChestGuiFactory(int size, Component title, TileState tilestate) {
		this.size = size;
		this.title = title;
		this.tilestate = tilestate;
	}


	@Override
	public ConcurrentPersistentGui createGui(UUID id) {
		return new CustomChest(size, title, tilestate, id);
	}

}