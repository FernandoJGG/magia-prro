package org.magiaperro.blocks;

import java.util.HashMap;
import java.util.Map;

import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.blocks.base.CustomBlock;

public class BlockRegistry {
    public static final Map<BlockID, CustomBlock> customBlocks = new HashMap<>();

    @SuppressWarnings("unused")
	public static void register() {
    	CustomCrafter crafter = new CustomCrafter(BlockID.CustomCrafter);
    	CofreMagico cofreMagico = new CofreMagico(BlockID.CofreMagico);
    	AlloyFurnace horno = new AlloyFurnace(BlockID.AlloyFurnace);
    }
    
    public static void registerCustomBlock(CustomBlock customBlock) {
    	customBlocks.put(customBlock.getId(), customBlock);
    }
    
    public static CustomBlock getCustomBlock(BlockID blockId) {
    	if (customBlocks.containsKey(blockId))
            return customBlocks.get(blockId);
    	else 
    		return null;
    }
    
    public static CustomBlock getCustomBlock(int id) {
    	BlockID blockId = BlockID.getByIndex(id);
    	return getCustomBlock(blockId);
    }
}