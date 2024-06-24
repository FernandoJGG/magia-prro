package org.magiaperro.gui.base;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface IGuiButton {
    void execute(InventoryClickEvent event);
}