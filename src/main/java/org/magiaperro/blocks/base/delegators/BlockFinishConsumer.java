package org.magiaperro.blocks.base.delegators;

import org.bukkit.block.TileState;

@FunctionalInterface
public interface BlockFinishConsumer {
	void finish(TileState tileState, Long fullCycles, Long excess);
}
