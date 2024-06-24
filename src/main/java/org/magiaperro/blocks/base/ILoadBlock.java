package org.magiaperro.blocks.base;

import org.bukkit.block.TileState;

public interface ILoadBlock {
	void onLoad(TileState tileState);
	void onUnload(TileState tileState);
}
