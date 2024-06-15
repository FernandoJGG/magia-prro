package org.magiaperro.gui.base;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface GuiButton {
    void execute(InventoryClickEvent event);
}