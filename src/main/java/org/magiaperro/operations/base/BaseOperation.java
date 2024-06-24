package org.magiaperro.operations.base;
import java.util.UUID;

import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.magiaperro.main.Main;

public class BaseOperation extends BukkitRunnable implements Listener {

    protected OperationFunction continueFunction;
    
    protected Long ticksPerCycle;
    protected int cycle = 0;

    protected UUID callerID;
    protected OperationHandler<?> handler;

    public BaseOperation(OperationFunction continueFunction, Long ticksPerCycle) {
		this.continueFunction = continueFunction;
		this.ticksPerCycle = ticksPerCycle;
	}
    
    public void setHandler(UUID callerID, OperationHandler<?> handler) {
    	this.callerID = callerID;
    	this.handler = handler;
    }

    
    public void unregisterAndCancel() {
    	if(handler != null && callerID != null) {
    		handler.removeOperation(callerID);
    	}
    	this.cancel();
    }
    
    public void startOperation() {
    	this.runTaskTimer(Main.instance, 0L, this.ticksPerCycle);
    }

    @Override
    public void run() {
    	this.runCycle();
    	this.cycle++;
    }
    
   protected void runCycle() {
    	this.continueFunction.run(this.cycle);
    }
    
    
}
