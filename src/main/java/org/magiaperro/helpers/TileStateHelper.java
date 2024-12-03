package org.magiaperro.helpers;

import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.magiaperro.blocks.base.CustomBlock;

public class TileStateHelper {
	
	// Método genérico que recibe una función que toma un TileState, actualiza el TileState antes de pasárselo.
    public static <T> void updateAndExecute(TileState tileState, Consumer<TileState> function) {
        TileState updatedTileState = getUpdatedTileState(tileState);
        if (updatedTileState != null) {
            function.accept(updatedTileState);
        }
    }
    
	// Actualiza el tileState al estado actual, a traves de su posición
	// Nulo si el tileState no existe o no tiene la misma GUID (Es un bloque distinto)
    public static TileState getUpdatedTileState(TileState tileState) {
    	Block block = tileState.getLocation().getBlock();
    	if(block != null && block.getState() != null && block.getState() instanceof TileState) {
			TileState currentTileState = (TileState) block.getState();
			CustomBlock customBlock = CustomBlock.fromTileState(tileState);
    		if (customBlock != null) {
    			UUID currentId = CustomBlock.getGuidFromTileState(currentTileState);
    			UUID originalId = CustomBlock.getGuidFromTileState(tileState);
    			if(currentId.equals(originalId)) {
    				return currentTileState;
    			}
    		}
    	}
    	LogHelper.logTileState("El tileState no existe o ha sido modificado.", tileState);
    	return null;
    }
}
