package org.magiaperro.operations;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;
import org.magiaperro.main.Main;

/**
 * Wrapper para encapsular el funcionamiento de BukkitRunnable
 * No realiza ninguna lógica, solo ejecuta el Consumer en el intervalo determinado
 */
public class BaseOperation extends BukkitRunnable {

    protected final OperationConsumer continueFunction;
    
    protected final Long ticksPerCycle;
    protected int cycle = 0;

    protected UUID callerID;
    protected OperationHandler<?> handler;

    public BaseOperation(OperationConsumer continueFunction, Long ticksPerCycle) {
		this.continueFunction = continueFunction;
		this.ticksPerCycle = ticksPerCycle;
	}
    
    public void setCaller(UUID callerID, OperationHandler<?> handler) {
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
