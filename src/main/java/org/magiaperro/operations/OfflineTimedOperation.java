package org.magiaperro.operations;

import org.magiaperro.main.Main;

/**
 * Operación con un tiempo de finalización, que se calcula a través del tiempo del mundo.
 * Por lo tanto, si se reanuda con el instante de inicio original, podrá calcular el
 * tiempo transcurrido mientras la operacion estaba pausada (Offline).
 */
public class OfflineTimedOperation extends BaseOperation {

    protected final OfflineOpFinishConsumer finishFunction;
    
    // Instante en el que se inicia la operación, en ticks de mundo
    protected final Long startTicks;
    protected final Long duration;

    public OfflineTimedOperation(OperationConsumer continueFunction, OfflineOpFinishConsumer finishFunction, 
    		Long startTicks, Long duration, Long ticksPerCycle) {
    	super(continueFunction, ticksPerCycle);
    
    	if(startTicks <= 0) {
    		throw new IllegalArgumentException("Parámetro startTicks no válido.");
    	}

		this.startTicks = startTicks;
		this.finishFunction = finishFunction;
		this.duration = duration;
	}

    @Override
    protected void runCycle() {
        Long currentTicks = Main.getWorldFullTime();
    	Long endTicks = this.startTicks + duration;

//    	Bukkit.getLogger().info("Ciclo " + this.cycle);
//    	Bukkit.getLogger().info("Ticks current " + currentTicks);
//    	Bukkit.getLogger().info("Ticks end " + endTicks);
    	
        if (endTicks > currentTicks) {
            super.runCycle();
            return;
        }

        Long elapsedTicks = currentTicks - this.startTicks;
        Long fullCycles = (elapsedTicks / duration);
        Long remainingTicks = (elapsedTicks % duration);
    	
//    	Bukkit.getLogger().info("Ticks pasados " + elapsedTicks);
        
    	this.finishFunction.finish(fullCycles, remainingTicks);
    	this.unregisterAndCancel();
    }

}
