package org.magiaperro.machines;

import java.util.HashMap;
import java.util.Map;

import org.magiaperro.machines.base.MachineID;
import org.magiaperro.machines.base.Machine;

public class MachineRegistry {
    public static final Map<MachineID, Machine> machines = new HashMap<>();

    @SuppressWarnings("unused")
	public static void register() {
    	CustomCrafter crafter = new CustomCrafter(MachineID.CustomCrafter);
    	CofreMagico cofreMagico = new CofreMagico(MachineID.CofreMagico);
    	AlloyFurnace horno = new AlloyFurnace(MachineID.AlloyFurnace);
    	Brewery brewery = new Brewery(MachineID.Brewery);
    }
    
    public static void registerMachine(Machine machine) {
    	machines.put(machine.getId(), machine);
    }
    
    public static Machine getMachine(MachineID blockId) {
    	if (machines.containsKey(blockId))
            return machines.get(blockId);
    	else 
    		return null;
    }
    
    public static Machine getMachine(int id) {
    	MachineID blockId = MachineID.getByIndex(id);
    	return getMachine(blockId);
    }
}