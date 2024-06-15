package org.magiaperro.gui;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.magiaperro.main.Main;

import com.jeff_media.morepersistentdatatypes.DataType;

public class CustomInventoryHolder implements InventoryHolder {
    private final Inventory customInventory;
    private final PersistentDataContainer pdc;

    public CustomInventoryHolder(Inventory customInventory, PersistentDataContainer pdc) {
        this.customInventory = customInventory;
        this.pdc = pdc;
    }

    @Override
    public Inventory getInventory() {
        return customInventory;
    }

    // Método para guardar el inventario en el PersistentDataContainer del BlockState
    public void saveInventory() {
    	ItemStack itemStack = new ItemStack(Material.ACACIA_BUTTON);
        NamespacedKey key = new NamespacedKey(Main.instance, "customdata");
        
        this.pdc.set(key, DataType.ITEM_STACK, itemStack);
    }
//
//    // Método para cargar el inventario desde el PersistentDataContainer del BlockState
//    public void loadInventory() {
//        TileState tileState = (TileState) block.getState();
//        String serializedInventory = tileState.getPersistentDataContainer().get(
//            new NamespacedKey(YourPlugin.getInstance(), "custom_inventory_data"), 
//            PersistentDataType.STRING
//        );
//        if (serializedInventory != null) {
//            customInventory.setContents(deserializeInventory(serializedInventory));
//        }
//    }
//    // Métodos para serializar y deserializar el inventario
//    private String serializeInventory(ItemStack[] inventory) {
//        // Implementación de la serialización del inventario a una cadena
//    	return null;
//    }
//
//    private ItemStack[] deserializeInventory(String serializedInventory) {
//        // Implementación de la deserialización de una cadena a un inventario
//    	return null;
//    }
}
