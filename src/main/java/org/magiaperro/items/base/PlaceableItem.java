package org.magiaperro.items.base;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.main.Keys;

import com.jeff_media.morepersistentdatatypes.DataType;

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

        	// TODO: Mover a funcion de CustomBlock
        	tileState.getPersistentDataContainer().set(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER, this.blockToPlace.ordinal());
        	tileState.getPersistentDataContainer().set(Keys.BLOCK_INSTANCE_GUID, DataType.UUID , UUID.randomUUID());

        	tileState.update(false, false);
        }
        else {
        	Bukkit.getLogger().severe("No se ha podido instanciar un bloque custom, el bloque colocado no contiene TileState"
        			+ "\nBloque afectado: " + placedBlock.getBlockData().getAsString() + " en " + placedBlock.getLocation());
        }
		
	}

}
