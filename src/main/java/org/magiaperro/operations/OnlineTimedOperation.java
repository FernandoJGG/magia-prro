package org.magiaperro.operations;

/**
 * Operación con un tiempo de finalización, en ciclos de operación.
 * Por lo tanto, solo avanzará mientras la operación este activa (Online).
 * Si se necesita reanudar, hay que guardar cada paso de ciclo, lo que puede ser costoso en escritura.
 * TODO: Metodo para consultar el ciclo y guardarlo al descargar
 */
public class OnlineTimedOperation extends BaseOperation {

    protected final OnlineOpFinishConsumer finishFunction;
    
    protected final int duration;

    public OnlineTimedOperation(OperationConsumer continueFunction, OnlineOpFinishConsumer finishFunction, 
    		int cycle, int duration, Long ticksPerCycle) {
    	super(continueFunction, ticksPerCycle);
    
    	if(cycle < 0) {
    		throw new IllegalArgumentException("Parámetro cycle no válido.");
    	}

		this.cycle = cycle;
		this.duration = duration;
		this.finishFunction = finishFunction;
	}

    @Override
    protected void runCycle() {
        if (this.cycle < this.duration) {
            super.runCycle();
        }
        else {
        	this.finishFunction.finish();
        	this.unregisterAndCancel();
        }
    }

}
