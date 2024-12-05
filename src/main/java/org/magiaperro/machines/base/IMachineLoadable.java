package org.magiaperro.machines.base;

public interface IMachineLoadable {
	void onLoad(IMachineData machineData);
	void onUnload(IMachineData machineData);
}
