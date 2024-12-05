package org.magiaperro.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineBlock;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;

public class BlockDisappearListener implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
	    boolean isCustom = handleBlockDisappear(event.getBlock(), BlockDisappearReason.BLOCK_BREAK);
	    if (isCustom) {
//	        event.setDropItems(false);
            event.getBlock().setType(Material.AIR, true);
	    }
	}
	
	@EventHandler
	public void onBlockDestroy(BlockDestroyEvent event) {
	    boolean isCustom = handleBlockDisappear(event.getBlock(), BlockDisappearReason.BLOCK_DESTROY);
	    if (isCustom) {
//	        event.setWillDrop(false);
            event.getBlock().setType(Material.AIR, true);
	    }
	}
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
	    for (Block block : event.blockList()) {
	        boolean isCustom = handleBlockDisappear(block, BlockDisappearReason.BLOCK_EXPLODE);
	        if (isCustom) {
	            block.setType(Material.AIR, true);
	        }
	    }
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
	    for (Block block : event.blockList()) {
	        boolean isCustom = handleBlockDisappear(block, BlockDisappearReason.ENTITY_EXPLODE);
	        if (isCustom) {
	            block.setType(Material.AIR, true);
	        }
	    }
	}
	
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		boolean isCustom = handleBlockDisappear(event.getBlock(), BlockDisappearReason.BLOCK_BURN);
        if (isCustom) {
            event.getBlock().setType(Material.AIR, true);
        }
	}
	
	@EventHandler
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
	    for (Block block : event.getBlocks()) {
	        if (block.getPistonMoveReaction() == PistonMoveReaction.BREAK) {
	            boolean isCustom = handleBlockDisappear(block, BlockDisappearReason.PISTON_EXTEND);
	            if (isCustom) {
	                block.setType(Material.AIR, true);
	            }
	        }
	    }
	}
	
	@EventHandler
	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
	    for (Block block : event.getBlocks()) {
	        if (block.getPistonMoveReaction() == PistonMoveReaction.BREAK) {
	            boolean isCustom = handleBlockDisappear(block, BlockDisappearReason.PISTON_RETRACT);
	            if (isCustom) {
	                block.setType(Material.AIR, true);
	            }
	        }
	    }
	}

    
	public boolean handleBlockDisappear(Block block, BlockDisappearReason reason) {
        BlockState state = block.getState();
        
        if (state != null && state instanceof TileState) {
            TileState tileState = (TileState) state;
            Machine machine = Machine.fromPDC(tileState.getPersistentDataContainer());
            
            if (machine != null) {
                machine.onDisappear(new MachineBlock(tileState));
                return true;
            }
        }
        return false;
	}
	
	public enum BlockDisappearReason {
	    BLOCK_BREAK,
	    BLOCK_DESTROY,
	    BLOCK_EXPLODE,
	    ENTITY_EXPLODE,
	    BLOCK_BURN,
	    PISTON_EXTEND,
	    PISTON_RETRACT
	}

    
    

}
