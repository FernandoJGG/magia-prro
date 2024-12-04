package org.magiaperro.helpers.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.main.Main;

/**
 * Wrapper para acceder al PersistentDataContainer de una entidad de manera más legible
 */
public class PDCProperty<T> implements IPDCProperty<T> {
    private final String name;
    private final PersistentDataType<?, T> dataType;

    public PDCProperty(String name, PersistentDataType<?, T> dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public PersistentDataType<?, T> getDataType() {
        return dataType;
    }

    public T getValue(PersistentDataContainer pdc) {
        return pdc.get(new NamespacedKey(Main.instance, name), dataType);
    }

    public void setValue(PersistentDataContainer pdc, T value) {
        pdc.set(new NamespacedKey(Main.instance, name), dataType, value);
    }

    public boolean hasValue(PersistentDataContainer pdc) {
        return pdc.has(new NamespacedKey(Main.instance, name), dataType);
    }

}
