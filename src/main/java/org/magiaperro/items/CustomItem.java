package org.magiaperro.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.main.Keys;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;

public class CustomItem {
	
    private String itemName;
    private Material material;
    private List<String> lore;
    private String id;

    public CustomItem(String id, String itemName, Material material, List<String> lore) {
        this.itemName = itemName;
        this.material = material;
        this.lore = lore;
        this.id = id;
        
        ItemRegistry.registerCustomItem(this);
    }

    public String getItemName() {
        return itemName;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getId() {
        return id;
    }

    public ItemStack buildItemStack() {
        ItemStack itemStack = new ItemStack(material);

        // Personalizar las propiedades del item
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(itemName)
        		.decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.YELLOW)
                .decoration(TextDecoration.BOLD, true));
        itemMeta.lore(this.lore.stream().map(x -> Component.text(x)).toList());
        
        itemMeta.getPersistentDataContainer().set(Keys.CUSTOM_ITEM_ID, PersistentDataType.STRING, this.id);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    // Agrega aquí cualquier lógica adicional o acciones especiales para el item customizado
}