package org.magiaperro.items.base;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.main.Keys;
import org.magiaperro.main.Main;

import com.jeff_media.morepersistentdatatypes.DataType;

public class PlaceableItem extends CustomItem implements IClickable {
	protected BlockID blockToPlace;
	
	public PlaceableItem(ItemID id, String itemName, Material material, List<String> lore, BlockID blockToPlace) {
		super(id, itemName, material, lore);
		this.blockToPlace = blockToPlace;
	}

	@Override
	public void onRightClick(PlayerInteractEvent event, EquipmentSlot hand) {

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Material blockInHand = event.getPlayer().getInventory().getItemInMainHand().getType();

            if (blockInHand.isBlock()) {
                // Schedule the task to run one tick later to ensure the block is placed
                Location blockLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
                Main.instance.getServer().getScheduler().runTaskLater(Main.instance, () -> {
                    Block placedBlock = blockLocation.getBlock();
                    if(placedBlock.getState() instanceof TileState) {
                    	TileState tileState = (TileState) placedBlock.getState();

                    	tileState.getPersistentDataContainer().set(Keys.CUSTOM_BLOCK_ID, PersistentDataType.INTEGER, this.blockToPlace.ordinal());
                    	tileState.getPersistentDataContainer().set(Keys.BLOCK_INSTANCE_GUID, DataType.UUID , UUID.randomUUID());

                    	tileState.update(true, false);
                    }
                    else {
                    	Bukkit.getLogger().severe("No se ha podido instanciar un bloque custom, el bloque colocado no contiene TileState"
                    			+ "\nBloque afectado: " + placedBlock.getBlockData().getAsString() + " en " + placedBlock.getLocation());
                    }

                }, 4L);
            }
            else {
            	Bukkit.getLogger().severe("No se ha podido instanciar un bloque custom, el itemStack no es un bloque"
            			+ "Material: " + blockInHand);
            }
        }
		
	}

}
