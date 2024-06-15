package org.magiaperro.blocks.base;

public enum BlockID {
	CustomCrafter(0);
	
	private final int index;

    private BlockID(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    
    public static BlockID getByIndex(int index) {
        for (BlockID element : BlockID.values()) {
            if (element.getIndex() == index) {
                return element;
            }
        }
        return null;
    }
}
