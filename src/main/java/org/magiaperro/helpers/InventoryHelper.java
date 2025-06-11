package org.magiaperro.helpers;

import java.util.Set;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.EquipmentSlot;

public class InventoryHelper {
	private static final Set<InventoryAction> putActions = Set.of(
			InventoryAction.SWAP_WITH_CURSOR,
			InventoryAction.PLACE_SOME,
			InventoryAction.PLACE_ONE,
			InventoryAction.PLACE_ALL,
			InventoryAction.HOTBAR_SWAP
			//InventoryAction.MOVE_TO_OTHER_INVENTORY
	);
	
	// como funcion o como atributo, nose
	public static EquipmentSlot[] hands() {
		return new EquipmentSlot[]{EquipmentSlot.HAND,  EquipmentSlot.OFF_HAND};
	}
	
	public static boolean isPutAction(InventoryAction action) {
		return putActions.contains(action);
	}
}
