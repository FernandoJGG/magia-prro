package org.magiaperro.gui;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.magiaperro.items.Varita;
import org.magiaperro.spells.Spell;
import org.magiaperro.spells.SpellID;
import org.magiaperro.spells.SpellRegistry;

import net.kyori.adventure.text.Component;

public class SpellsGui implements InventoryHolder {
	private Inventory inventory;

	@SuppressWarnings("deprecation")
	public SpellsGui() {
		inventory = Bukkit.createInventory(this, 9, "Hechizos"); // Tamaño del inventario (9) y título

		// Agrega los elementos y opciones a tu inventario
		Collection<Spell> spells = SpellRegistry.spells.values();
		for (Spell spell : spells) {
			Material material = spell.getMaterial();
			String name = spell.getName();

			ItemStack itemStack = new ItemStack(material);
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.displayName(Component.text(name));
			itemMeta.setCustomModelData(spell.getId().getIndex());
			itemStack.setItemMeta(itemMeta);

			inventory.addItem(itemStack);
		}
	}

	public void openInterface(Player player) {
		player.openInventory(inventory);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public void handleClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getInventory().getHolder() instanceof SpellsGui) {
			event.setCancelled(true); 
			
			if (event.getClickedInventory().getHolder() instanceof SpellsGui) {
				ItemStack clickedItem = event.getCurrentItem();
				if (clickedItem != null) {
					SpellID spellId = SpellID.getByIndex(clickedItem.getCustomModelData());
					Varita.selectSpell(spellId, player);
				}
			}
		}
	}

	// Maneja el evento de cierre del inventario
	public void handleCloseEvent(InventoryCloseEvent event) {
		// Verificar si el evento se produjo en tu interfaz
		if (event.getInventory().getHolder() instanceof SpellsGui) {
			// Realiza la lógica correspondiente al cerrar la interfaz, si es necesario
			event.getPlayer().sendMessage("Cerrado menú");
		}
	}
}
