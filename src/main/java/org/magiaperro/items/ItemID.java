package org.magiaperro.items;

public enum ItemID {
	Varita(0),
	Varita2(1);
	
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
