package org.magiaperro.machines.base.delegators;

import org.magiaperro.machines.base.IMachineData;

@FunctionalInterface
public interface MachineContinueConsumer {
    void run(IMachineData machineData, int cycle);
}
