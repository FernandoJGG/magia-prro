package org.magiaperro.machines.base;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Skull;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.helpers.pdc.PDCProperty;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
//import org.magiaperro.main.Keys;
import org.magiaperro.machines.MachineRegistry;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.jeff_media.morepersistentdatatypes.DataType;


//TODO: Mover el OperationDelegator a la base. Da igual si no tiene operacion.
//HECHO? --TODO: Remodelar por CustomMachine--
//Una maquina puede ser tanto un tilestate, una entidad, cualquier cosa que incluya un PDC
//Una posible manera seria crear una clase IPersistible, que luego implementaria con 
//IPersistibleBlock, IPersistibleEntity, cada una teniendo como parametro su TileState, Entity, etc
public abstract class Machine {

	private MachineID id;
	protected ItemID dropItemId;
	
	public static PDCProperty<Integer> machineId = new PDCProperty<Integer>("custom_block_id", PersistentDataType.INTEGER);
	public static PDCProperty<UUID> instanceId = new PDCProperty<UUID>("block_instance_guid", DataType.UUID);
	
	public Machine(MachineID id, ItemID itemBlockId) {
		this.id = id;
		this.dropItemId = itemBlockId;
		
		MachineRegistry.registerMachine(this);
	}

	public MachineID getId() {
		return id;
	}
	
    /**
     * Instancia un bloque custom con sus atributos en el PDC
     */
	public void instantiateMachine(IMachineData machineData) {
		
		machineId.setValue(machineData.getPDC(), this.id.getIndex());
		instanceId.setValue(machineData.getPDC(), UUID.randomUUID());
		
		// WORKAROUND TEMPORAL: establece un jugador para evitar excepciones
		// Deberia crear una forma de añadir una id de jugador
    	if(machineData instanceof MachineBlock) {
    		TileState tileState = ((MachineBlock) machineData).getTileState();
    		
    		if(tileState instanceof Skull) {
	       		 UUID playerUUID = UUID.fromString("ebd0b3d0-c4d3-4c62-9d25-3fa58d202022");
	
	       		 PlayerProfile profile = Bukkit.createProfile(playerUUID);
	                profile.getProperties().add(new ProfileProperty("textures", 
	               		 "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q4YTU4ODEzZGJhYmQ3ZTY0MzExZmY1M2QyNzM3NWIzMjFlZWQ2ZjcyZjQxZjFlYzY0NzU2NDU0Mjg0In19fQ=="));
	                
	                
	       		((Skull) tileState).setPlayerProfile(profile);
    		}
    	}
    	
    	machineData.update();
	}
    
    
    public static Machine fromId(MachineID blockId) {
    	return MachineRegistry.getMachine(blockId);
    }
    
    public static Machine fromId(int blockId) {
    	return MachineRegistry.getMachine(blockId);
    }
    
    public void onDisappear(IMachineData machineData) {
    	CustomItem itemDrop  = CustomItem.fromId(this.dropItemId);
    	
    	if(this.dropItemId != null) {
        	machineData.getWorld().dropItemNaturally(machineData.getLocation(), itemDrop.buildItemStack());
    	}
    }

    public static UUID getGuidFromMachine(IMachineData machineData) {
		return instanceId.getValue(machineData.getPDC());
    }
    
    public static UUID getGuidFromPDC(PersistentDataContainer pdc) {
		return instanceId.getValue(pdc);
    }
    
    public static Machine fromPDC(PersistentDataContainer pdc) {
    	if(machineId.hasValue(pdc)) {
    		Integer id = machineId.getValue(pdc);
    		return MachineRegistry.getMachine(id);
		}
    	return null;
    }


}
