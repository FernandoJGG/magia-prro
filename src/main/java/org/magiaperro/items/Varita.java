package org.magiaperro.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.items.interfaces.IClickable;
import org.magiaperro.main.Keys;
import org.magiaperro.spells.Spell;
import org.magiaperro.spells.SpellID;
import org.magiaperro.spells.SpellRegistry;

import org.magiaperro.gui.SpellsGui;

import java.util.List;

public class Varita extends CustomItem implements IClickable {
	
	
    public Varita(ItemID id, String itemName, Material material, List<String> lore) {
		super(id, itemName, material, lore);
	}

    
    // Manejar la acción al darle click derecho al item
    public void onRightClick(PlayerInteractEvent event, EquipmentSlot hand) {
        Player player = event.getPlayer();
    	ItemStack wand = player.getInventory().getItem(hand);

        if (player.isSneaking()) {
        	// ToDo: Paquete gui. Implementar interfaz.
            SpellsGui gui = new SpellsGui();
            gui.openInterface(player);
        }
        else {
        Integer spellId = wand.getPersistentDataContainer().get(Keys.SELECTED_SPELL_ID, PersistentDataType.INTEGER);

        if (spellId != null) {
	        Spell spell = SpellRegistry.getSpell(spellId);
	        
	        player.swingHand(hand);
	        
	        spell.castSpell(player);
        }
        else {
        	player.sendMessage("Hechisu no seleccionado");
        }
        
        }
    }


	public static void selectSpell(SpellID spellId, Player player) {
    	ItemStack itemOnHand = player.getInventory().getItemInMainHand();
    	
    	// TODO: Comprobar que es una varita
    	
        ItemMeta itemMeta = itemOnHand.getItemMeta();

        itemMeta.getPersistentDataContainer().set(Keys.SELECTED_SPELL_ID, PersistentDataType.INTEGER, spellId.getIndex());

        itemOnHand.setItemMeta(itemMeta);
	}
}
