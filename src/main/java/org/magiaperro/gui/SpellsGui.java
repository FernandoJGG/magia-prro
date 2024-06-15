package org.magiaperro.gui;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.magiaperro.gui.base.BaseGui;
import org.magiaperro.gui.base.GuiGraphic;
import org.magiaperro.items.Varita;
import org.magiaperro.spells.SpellRegistry;
import org.magiaperro.spells.base.Spell;
import org.magiaperro.spells.base.SpellID;

import net.kyori.adventure.text.Component;

public class SpellsGui extends BaseGui {

	public SpellsGui() {
		super(27,Component.text("Hechizos"));
		
		List<GuiGraphic> graphics = new ArrayList<>();
		graphics.add(new GuiGraphic (
			/*Material*/ 	Material.GLASS_PANE,
			/*Slots*/ 		new int[] {0,1,2,3,4,5,6,7,8,18,19,20,21,22,23,24,25,26},
			/*Text*/ 		Component.text("")
		));
		
		Collection<Spell> spells = SpellRegistry.spells.values();
		
		int i = 0;
		for (Spell spell : spells) {
			final SpellID spellId = spell.getId();
			graphics.add(new GuiGraphic (
				/*Material*/ 	spell.getMaterial(),
				/*Slots*/ 		new int[] {i+9},
				/*Text*/ 		Component.text(spell.getName()),
				/*Lore*/ 		Component.text(spell.getDescription()),
				/*Function*/ 	(event) -> this.selectSpell(event, spellId)
			));

			i++;
			if(i > 9) {
				break;
			}
		}

		this.DrawGui(graphics.toArray(new GuiGraphic[graphics.size()]));
	}

	public void selectSpell(InventoryClickEvent event, SpellID spellId) {
		Player player = (Player) event.getWhoClicked();
		Varita.selectSpell(spellId, player);
	}

	// Maneja el evento de cierre del inventario
	@Override
	public void handleCloseEvent(InventoryCloseEvent event) {
		super.handleCloseEvent(event);
		Bukkit.getLogger().info("Cerrado menú de hechizos");
	}
}
