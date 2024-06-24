package org.magiaperro.operations.base.factories;

import org.bukkit.block.TileState;
import org.magiaperro.operations.base.BaseOperation;

public interface BlockOperationFactory {
    BaseOperation createOperation(TileState tileState);
}
