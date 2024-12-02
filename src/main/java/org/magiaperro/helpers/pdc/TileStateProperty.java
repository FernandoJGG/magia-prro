package org.magiaperro.helpers.pdc;

import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;

public class TileStateProperty<T> extends PDCProperty<T> {

	public TileStateProperty(String name, PersistentDataType<?, T> dataType) {
		super(name, dataType);
	}

    public T getValue(TileState tileState) {
        return this.getValue(tileState.getPersistentDataContainer());
    }

    public void setValue(TileState tileState, T value) {
        this.setValue(tileState.getPersistentDataContainer(), value);
    }

    public boolean hasValue(TileState tileState) {
        return this.hasValue(tileState.getPersistentDataContainer());
    }
}
