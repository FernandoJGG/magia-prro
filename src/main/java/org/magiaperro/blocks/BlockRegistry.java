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
    }
    
    public static void registerCustomBlock(CustomBlock customBlock) {
    	customBlocks.put(customBlock.getId(), customBlock);
    }
    
    public static CustomBlock getCustomBlock(BlockID blockId) {
        return customBlocks.get(blockId);
    }
    
    public static CustomBlock getCustomBlock(int blockId) {
        return customBlocks.get(BlockID.getByIndex(blockId));
    }
}