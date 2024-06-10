package org.magiaperro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.magiaperro.gui.SpellsGui;

public class SpellsGuiListener implements Listener {
	
	//TODO: Revamp. Este listener esta escuchando a TODOS los eventos de inventario, no solo a los de SpellsGui.
	
    private SpellsGui spellsGui;

    public SpellsGuiListener() {
    	spellsGui = new SpellsGui(); // Crea una instancia de la interfaz
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    	spellsGui.handleClickEvent(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

    	spellsGui.handleCloseEvent(event);
    }
}
