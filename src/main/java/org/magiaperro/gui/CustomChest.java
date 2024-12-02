package org.magiaperro.gui;

import java.util.UUID;
import java.util.stream.IntStream;

import org.bukkit.block.TileState;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.strategies.PDCTileSaveStrategy;

import net.kyori.adventure.text.Component;

public class CustomChest extends ConcurrentPersistentGui {

	public CustomChest(int size, Component title, TileState tileState, UUID guid) {
		super(
				size, 
				title, 
				IntStream.range(0, size).toArray(), 
				new int[0], 
				new PDCTileSaveStrategy(tileState, guid), 
				guid
			);
	}
	
	
	
	

}
