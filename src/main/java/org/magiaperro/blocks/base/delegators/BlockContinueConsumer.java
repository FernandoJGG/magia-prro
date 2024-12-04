package org.magiaperro.blocks.base.delegators;

import org.bukkit.block.TileState;

@FunctionalInterface
public interface BlockContinueConsumer {
    void run(TileState tileState, int cycle);
}
