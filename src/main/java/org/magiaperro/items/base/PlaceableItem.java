package org.magiaperro.items.base;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.block.BlockPlaceEvent;
import org.magiaperro.machines.base.MachineID;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineBlock;

public class PlaceableItem extends CustomItem {
	protected MachineID blockToPlace;
	
	public PlaceableItem(ItemID id, String itemName, Material material, List<String> lore, MachineID blockToPlace) {
		super(id, itemName, material, lore);
		this.blockToPlace = blockToPlace;
	}

	public void onBlockPlace(BlockPlaceEvent event) {

        Block placedBlock = event.getBlock();
        if(placedBlock.getState() instanceof TileState) {
        	TileState tileState = (TileState) placedBlock.getState();

        	Machine machine = Machine.fromId(blockToPlace);
        	machine.instantiateMachine(new MachineBlock(tileState));

        }
        else {
        	Bukkit.getLogger().severe("No se ha podido instanciar un bloque custom, el bloque colocado no contiene TileState"
        			+ "\nBloque afectado: " + placedBlock.getBlockData().getAsString() + " en " + placedBlock.getLocation());
        }
		
	}

}
