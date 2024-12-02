package org.magiaperro.helpers;

import java.util.Set;

import org.bukkit.event.inventory.InventoryAction;

public class InventoryHelper {
	private static final Set<InventoryAction> putActions = Set.of(
			InventoryAction.SWAP_WITH_CURSOR,
			InventoryAction.PLACE_SOME,
			InventoryAction.PLACE_ONE,
			InventoryAction.PLACE_ALL,
			InventoryAction.HOTBAR_SWAP
			//InventoryAction.MOVE_TO_OTHER_INVENTORY
	);
	
	public static boolean isPutAction(InventoryAction action) {
		return putActions.contains(action);
	}
}
