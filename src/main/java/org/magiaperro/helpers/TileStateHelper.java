package org.magiaperro.helpers;

import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.magiaperro.machines.base.IMachineData;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineBlock;

public class TileStateHelper {
	
	// Método genérico que recibe una función que toma un TileState, actualiza el TileState antes de pasárselo.
    public static <T> void updateAndExecute(TileState tileState, Consumer<TileState> function) {
        TileState updatedTileState = getUpdatedTileState(tileState);
        if (updatedTileState != null) {
            function.accept(updatedTileState);
        }
    }
    
    // TODO: Logeo y testing
    public static <T> void updateAndExecute(IMachineData machineData, Consumer<IMachineData> function) {
    	if(machineData instanceof MachineBlock) {
    		MachineBlock machineBlock = (MachineBlock) machineData;
    		TileState updatedTileState = getUpdatedTileState(machineBlock.getTileState());
    		if (updatedTileState == null) {
    			//TODO: cancelar la op. Para ello pasar como parametro su op handler.
    			return;
    		}
    		machineBlock.setTileState(updatedTileState);
    		machineData = machineBlock;
    	}
    	function.accept(machineData);
    }
    
	// Actualiza el tileState al estado actual, a traves de su posición
	// Nulo si el tileState no existe o no tiene la misma GUID (Es un bloque distinto)
    public static TileState getUpdatedTileState(TileState tileState) {
    	Block block = tileState.getLocation().getBlock();
    	if(block != null && block.getState() != null && block.getState() instanceof TileState) {
			TileState currentTileState = (TileState) block.getState();
			Machine machine = Machine.fromPDC(tileState.getPersistentDataContainer());
    		if (machine != null) {
    			UUID currentId = Machine.getGuidFromPDC(currentTileState.getPersistentDataContainer());
    			UUID originalId = Machine.getGuidFromPDC(tileState.getPersistentDataContainer());
    			if(currentId.equals(originalId)) {
    				return currentTileState;
    			}
    		}
    	}
    	LogHelper.logTileState("El tileState no existe o ha sido modificado.", tileState);
    	return null;
    }
}
