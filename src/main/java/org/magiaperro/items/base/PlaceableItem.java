package org.magiaperro.items.base;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.block.BlockPlaceEvent;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.blocks.base.CustomBlock;

public class PlaceableItem extends CustomItem {
	protected BlockID blockToPlace;
	
	public PlaceableItem(ItemID id, String itemName, Material material, List<String> lore, BlockID blockToPlace) {
		super(id, itemName, material, lore);
		this.blockToPlace = blockToPlace;
	}

	public void onBlockPlace(BlockPlaceEvent event) {

        Block placedBlock = event.getBlock();
        if(placedBlock.getState() instanceof TileState) {
        	TileState tileState = (TileState) placedBlock.getState();

        	CustomBlock customBlock = CustomBlock.fromId(blockToPlace);
        	customBlock.instantiateBlock(tileState);

        }
        else {
        	Bukkit.getLogger().severe("No se ha podido instanciar un bloque custom, el bloque colocado no contiene TileState"
        			+ "\nBloque afectado: " + placedBlock.getBlockData().getAsString() + " en " + placedBlock.getLocation());
        }
		
	}

}
