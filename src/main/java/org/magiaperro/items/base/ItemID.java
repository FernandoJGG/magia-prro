package org.magiaperro.items.base;

public enum ItemID {
	Varita(0),
	Varita2(1),
	CustomCrafter(2),
	CustomCrafter2(3),
	CofreMagico(4),
	AlloyFurnace(5),
	BronzeIngot(6),
	LeadOre(7);
	
	private final int index;

    private ItemID(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    
    public static ItemID getByIndex(int index) {
        for (ItemID element : ItemID.values()) {
            if (element.getIndex() == index) {
                return element;
            }
        }
        return null;
    }
}
