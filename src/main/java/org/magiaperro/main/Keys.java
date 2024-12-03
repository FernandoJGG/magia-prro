package org.magiaperro.main;

import org.bukkit.NamespacedKey;

public class Keys {
	// Registries
	public static final NamespacedKey CUSTOM_ITEM_ID = new NamespacedKey(Main.instance, "custom_item_id");
    public static final NamespacedKey SELECTED_SPELL_ID = new NamespacedKey(Main.instance, "selected_spell_id");
	//public static final NamespacedKey CUSTOM_BLOCK_ID = new NamespacedKey(Main.instance, "custom_block_id");
	public static final NamespacedKey CUSTOM_PROJECTILE_ID = new NamespacedKey(Main.instance, "custom_projectile_id");
    
    // Inventarios
	public static final NamespacedKey IS_GUI_BUTTON = new NamespacedKey(Main.instance, "is_gui_button");
	public static final NamespacedKey PERSISTED_INVENTORY = new NamespacedKey(Main.instance, "persisted_inventory");
    
	//Bloques
	//public static final NamespacedKey BLOCK_INSTANCE_GUID = new NamespacedKey(Main.instance, "block_instance_guid");
	//public static final NamespacedKey BLOCK_OPERATION_FINISH_TIME = new NamespacedKey(Main.instance, "operation_finish_time");
	
	//TEST
	public static final NamespacedKey MANA = new NamespacedKey(Main.instance, "mana");

	
	//String metadata = itemOnHand.getPersistentDataContainer().get(Keys.CUSTOM_ITEM_ID, PersistentDataType.STRING);
    //itemMeta.getPersistentDataContainer().set(Keys.CUSTOM_ITEM_ID, PersistentDataType.STRING, this.id);

}
