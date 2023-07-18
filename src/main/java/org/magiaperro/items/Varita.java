package org.magiaperro.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.magiaperro.items.interfaces.IClickable;
import org.magiaperro.spells.Spell;
import org.magiaperro.spells.SpellRegistry;
import org.magiaperro.gui.SpellsGui;

import java.util.List;

public class Varita extends CustomItem implements IClickable {
	
	
    public Varita(String id, String itemName, Material material, List<String> lore) {
		super(id, itemName, material, lore);
	}

    
    // Manejar la acción al darle click derecho al item
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        
        if (player.isSneaking()) {
        	// ToDo: Paquete gui. Implementar interfaz.
            SpellsGui gui = new SpellsGui();
            gui.openInterface(player);
        }
        
        Spell spell = SpellRegistry.getSpell("fireball");
        spell.castSpell(player);
    }
}
