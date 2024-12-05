package org.magiaperro.listeners;

import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineBlock;
import org.magiaperro.machines.base.MachineEntity;
import org.magiaperro.machines.base.IMachineLoadable;

public class LoadListener implements Listener {
	@EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        
        for (BlockState blockState : chunk.getTileEntities()) {
        	if(blockState instanceof TileState) {
        		TileState tileState = (TileState) blockState;
        		Machine machine = Machine.fromPDC(tileState.getPersistentDataContainer());
                if (machine != null && machine instanceof IMachineLoadable) {
                    ((IMachineLoadable) machine).onLoad(new MachineBlock(tileState));
                }
        	}
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();
        for (BlockState blockState : chunk.getTileEntities()) {
        	if(blockState instanceof TileState) {
        		TileState tileState = (TileState) blockState;
        		Machine machine = Machine.fromPDC(tileState.getPersistentDataContainer());
                if (machine != null && machine instanceof IMachineLoadable) {
                    ((IMachineLoadable) machine).onUnload(new MachineBlock(tileState));
                }
        	}
        }
    }
    
    @EventHandler
    public void onEntitiesdLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) {
        	Machine machine = Machine.fromPDC(entity.getPersistentDataContainer());
            if (machine != null && machine instanceof IMachineLoadable) {
                ((IMachineLoadable) machine).onLoad(new MachineEntity(entity));
            }
        }
    }

    @EventHandler
    public void onEntitiesUnload(EntitiesUnloadEvent event) {
        for (Entity entity : event.getEntities()) {
        	Machine machine = Machine.fromPDC(entity.getPersistentDataContainer());
            if (machine != null && machine instanceof IMachineLoadable) {
                ((IMachineLoadable) machine).onUnload(new MachineEntity(entity));
            }
        }
    }
}
