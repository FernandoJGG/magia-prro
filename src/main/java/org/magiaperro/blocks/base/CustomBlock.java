package org.magiaperro.blocks.base;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.BlockRegistry;
import org.magiaperro.helpers.pdc.TileStateProperty;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
//import org.magiaperro.main.Keys;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.jeff_media.morepersistentdatatypes.DataType;

//TODO: Remodelar por CustomMachine
//Una maquina puede ser tanto un tilestate, una entidad, cualquier cosa que incluya un PDC
//Una posible manera seria crear una clase IPersistible, que luego implementaria con 
//IPersistibleBlock, IPersistibleEntity, cada una teniendo como parametro su TileState, Entity, etc
public abstract class CustomBlock {

	private BlockID id;
	protected ItemID itemBlockId;
	
	public static TileStateProperty<Integer> blockId = new TileStateProperty<Integer>("custom_block_id", PersistentDataType.INTEGER);
	public static TileStateProperty<UUID> instanceId = new TileStateProperty<UUID>("block_instance_guid", DataType.UUID);
	
	public CustomBlock(BlockID id, ItemID itemBlockId) {
		this.id = id;
		this.itemBlockId = itemBlockId;
		
		BlockRegistry.registerCustomBlock(this);
	}

	public BlockID getId() {
		return id;
	}
	
    /**
     * Instancia un bloque custom con sus atributos en el PDC
     */
	public void instantiateBlock(TileState tileState) {
		blockId.setValue(tileState, this.id.getIndex());
		instanceId.setValue(tileState, UUID.randomUUID());
		
		// WORKAROUND TEMPORAL: establece un jugador para evitar excepciones
		// Deberia crear una forma de añadir una id de jugador
    	if(tileState instanceof Skull) {
    		 UUID playerUUID = UUID.fromString("ebd0b3d0-c4d3-4c62-9d25-3fa58d202022");

    		 PlayerProfile profile = Bukkit.createProfile(playerUUID);
             profile.getProperties().add(new ProfileProperty("textures", 
            		 "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q4YTU4ODEzZGJhYmQ3ZTY0MzExZmY1M2QyNzM3NWIzMjFlZWQ2ZjcyZjQxZjFlYzY0NzU2NDU0Mjg0In19fQ=="));
             
             
    		((Skull) tileState).setPlayerProfile(profile);
    	}
    	
    	tileState.update();
	}
    
    public static CustomBlock fromTileState(TileState tileState) {
    	if(tileState != null && blockId.hasValue(tileState)) {
    		Integer id = blockId.getValue(tileState);
    		return BlockRegistry.getCustomBlock(id);
    	}
    	else {
    		return null;
    	}
    }
    
    
    public static CustomBlock fromId(BlockID blockId) {
    	return BlockRegistry.getCustomBlock(blockId);
    }
    
    public static CustomBlock fromId(int blockId) {
    	return BlockRegistry.getCustomBlock(blockId);
    }
    
    public void onBlockDisappear(TileState tileState) {
    	Block block = tileState.getBlock();
    	CustomItem itemBlock = CustomItem.fromId(this.itemBlockId);
    	
    	block.getWorld().dropItemNaturally(block.getLocation(), itemBlock.buildItemStack());
    }

    public static UUID getGuidFromTileState(TileState tileState) {
		UUID guid = instanceId.getValue(tileState);
    	return guid;
    }


}
