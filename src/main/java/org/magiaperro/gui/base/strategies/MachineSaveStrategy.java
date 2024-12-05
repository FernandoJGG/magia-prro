package org.magiaperro.gui.base.strategies;

import java.util.UUID;

import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.magiaperro.gui.base.PersistentGui;
import org.magiaperro.helpers.LogHelper;
import org.magiaperro.helpers.TileStateHelper;
import org.magiaperro.machines.base.IMachineData;
import org.magiaperro.machines.base.MachineBlock;

public class MachineSaveStrategy extends PDCSaveStrategy {
	
	protected IMachineData machineData;
	protected UUID guid;
	
	public MachineSaveStrategy(IMachineData machineData, UUID guid) {
		super(machineData.getPDC());
		this.machineData = machineData;
		this.guid = guid;
	}
	
	public MachineSaveStrategy(IMachineData machineData, UUID guid, String pdcKey) {
		super(machineData.getPDC(), pdcKey);
		this.machineData = machineData;
		this.guid = guid;
	}
	protected PersistentDataContainer getPDC() {
		return this.machineData.getPDC();
	}
	
	@Override
	public void save(PersistentGui persistentGui) {
    	// Lee la GUID del tileState y comprueba si es la original antes de guardar
		// TODO: Testing (evitar el cerrado en el evento onDestroy y ver si se vuelve a crear)
		
		// TODO: Actualizar para incluir el seguro de TileStateHelper
		// Además, comprobar que el otro SaveStrategy no rompa por lo mismo
		if(this.machineData instanceof MachineBlock) {
			MachineBlock machineBlock = (MachineBlock)this.machineData;
			TileState updatedTileState = TileStateHelper.getUpdatedTileState(machineBlock.getTileState());
			if(updatedTileState == null) {
				LogHelper.logTileState("Se intenta guardar un inventario en tileState no valido:", machineBlock.getTileState());
				throw new IllegalStateException("Se intenta guardar un inventario en tileState no valido");
			}
			machineBlock.setTileState(updatedTileState);
			
		}
		super.save(persistentGui);
		boolean success = this.machineData.update();
		if(!success) {
			LogHelper.logLocation("Error al actualizar el IMachineData", this.machineData.getLocation());
			throw new IllegalStateException("Error al actualizar el IMachineData");
		}
	}

}
