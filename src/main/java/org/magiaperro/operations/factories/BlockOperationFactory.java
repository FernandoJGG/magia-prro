package org.magiaperro.operations.factories;

import org.bukkit.block.TileState;
import org.magiaperro.operations.BaseOperation;

public interface BlockOperationFactory {
    BaseOperation createOperation(TileState tileState);
}
