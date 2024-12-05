package org.magiaperro.machines.base.delegators;

import org.magiaperro.machines.base.IMachineData;

@FunctionalInterface
public interface MachineFinishConsumer {
	void finish(IMachineData machineData, Long fullCycles, Long excess);
}
