package org.magiaperro.gui.base.factories;

import java.util.UUID;

import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.GuiGraphic;
import org.magiaperro.gui.base.strategies.SaveStrategy;

import net.kyori.adventure.text.Component;

public class BaseConcurrentGuiFactory implements ConcurrentGuiFactory {
	private int size;
	private Component title;
	private int[] outputSlots;
	private int[] inputSlots;
	private SaveStrategy strategy;
	private GuiGraphic[] graphics;
	
	public BaseConcurrentGuiFactory(int size, Component title, int[] inputSlots, int[] outputSlots, SaveStrategy strategy, GuiGraphic[] graphics) {
		this.size = size;
		this.title = title;
		this.inputSlots = inputSlots;
		this.outputSlots = outputSlots;
		this.strategy = strategy;
		this.graphics = graphics;
	}


	@Override
	public ConcurrentPersistentGui createGui(UUID id) {
		return new ConcurrentPersistentGui(size, title, inputSlots, outputSlots, strategy, graphics, id);
	}


	public BaseConcurrentGuiFactory setSaveStrategy(SaveStrategy strategy) {
		this.strategy = strategy;
		return this;
	}

}
