package org.magiaperro.gui.base;

import java.util.List;

import org.bukkit.Material;

import net.kyori.adventure.text.Component;

public class GuiGraphic {
	public GuiGraphic(Material material, int[] slots, Component text) {
		super();
		this.material = material;
		this.slots = slots;
		this.text = text;
	}

	public GuiGraphic(Material material, int[] slots, Component text, List<Component> lore, IGuiButton funcion) {
		this.material = material;
		this.slots = slots;
		this.text = text;
		this.lore = lore;
		this.funcion = funcion;
	}
	
	public GuiGraphic(Material material, int[] slots, Component text, Component lore, IGuiButton funcion) {
		this.material = material;
		this.slots = slots;
		this.text = text;
		this.lore = List.of(lore);
		this.funcion = funcion;
	}

	public Material material;
	public int[] slots;
	public Component text;
	public List<Component> lore;
	
	public IGuiButton funcion;
}