package org.magiaperro.spells.base;

public enum SpellID {
	Fireball(0),
	Snowball(1),
	GoldenApple(2),
	BombaAtomica(3);
	
	private final int index;

    private SpellID(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    
    public static SpellID getByIndex(int index) {
        for (SpellID element : SpellID.values()) {
            if (element.getIndex() == index) {
                return element;
            }
        }
        return null;
    }
}
