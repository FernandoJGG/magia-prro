package org.magiaperro.helpers.pdc;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public interface IPDCProperty <T> {
    String getName();

    PersistentDataType<?, T> getDataType();

    T getValue(PersistentDataContainer pdc);

    void setValue(PersistentDataContainer pdc, T value);

    boolean hasValue(PersistentDataContainer pdc);
}