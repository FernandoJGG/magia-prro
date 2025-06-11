package org.magiaperro.machines.base;

public enum MachineID {
	CustomCrafter(0),
	CofreMagico(1),
	AlloyFurnace(2),
	Brewery(3);
	
	private final int index;

    private MachineID(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    
    public static MachineID getByIndex(int index) {
        for (MachineID element : MachineID.values()) {
            if (element.getIndex() == index) {
                return element;
            }
        }
        return null;
    }
}
