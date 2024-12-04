package org.magiaperro.operations;

import java.time.Instant;

import org.bukkit.Bukkit;

// Una operacion que termina en un instante de tiempo determinado, que sigue corriendo incluso al cerrar el servidor (Tiempo real del server)
// Algo bugeada, deprecada a favor de OfflineOperation, pero lo dejo por si aca
// Notar que si se pausa y se reaunuda ya completa, se saltan todos los continueFunction intermedios
// Por lo que en bloques las acciones de continueFunction solo deben ser esteticos (particulas, sonidos, etc)
@Deprecated
public class RealtimeTimedOperation extends BaseOperation {

    protected final OperationFinishConsumer finishFunction;
    
    protected final Long startTicks;
    protected final Long duration;

    public RealtimeTimedOperation(OperationConsumer continueFunction, OperationFinishConsumer finishFunction, 
    		Long startInstant, Long duration, Long ticksPerCycle) {
    	super(continueFunction, ticksPerCycle);
    	
    	Bukkit.getLogger().info("desde timedOperation llegan " + startInstant);
    	if(startInstant <= 0) {
    		throw new IllegalArgumentException("Parámetro startInstant no válido.");
    	}

    	// El instante se divide en 20 para que cuadre con los ticks
		this.startTicks = Math.ceilDiv(startInstant, 50);
		this.finishFunction = finishFunction;
		this.duration = duration;
	}
// Comentado porque se mueve la responsabilidad a la propia entidad de manejar su descarga
//    public static boolean onLoad(TileState tileState, IOperationFunction cont, IOperationFunction finish) {
//        return onLoad(tileState, cont, finish, Keys.BLOCK_FINISH_OPERATION_TIME, Keys.BLOCK_INSTANCE_GUID);
//    }
//
//    public static boolean onLoad(TileState tileState, IOperationFunction cont, IOperationFunction finish, NamespacedKey endTime, NamespacedKey operationGuid) {
//        if (tileState.getPersistentDataContainer().has(endTime, PersistentDataType.LONG)) {
//            long endTimeMillis = tileState.getPersistentDataContainer().get(endTime, PersistentDataType.LONG);
//            UUID blockUUID = tileState.getPersistentDataContainer().get(operationGuid, DataType.UUID);
//
//            if (!runningOperations.containsKey(blockUUID)) {
//                BlockOperation operation =  new BlockOperation(tileState, cont, finish);
//                
//                
//                runningOperations.put(blockUUID, operation);
//                operation.runTaskTimer(Main.instance, 0L, 20L);
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    protected void runCycle() {
        Long currentTicks = Math.ceilDiv(Instant.now().toEpochMilli(),50);
    	Long endTicks = this.startTicks + duration;

    	Bukkit.getLogger().info("Ciclo " + this.cycle);
    	Bukkit.getLogger().info("Ticks current " + currentTicks);
    	Bukkit.getLogger().info("Ticks end " + endTicks);
    	
        if (endTicks > currentTicks) {
            super.runCycle();
            return;
        }

        Long elapsedTicks = currentTicks - this.startTicks;
        Long fullCycles = (elapsedTicks / duration);
        Long remainingTicks = (elapsedTicks % duration);
    	
    	Bukkit.getLogger().info("Ticks pasados " + elapsedTicks);
        
    	this.finishFunction.finish(fullCycles, remainingTicks);
    	this.unregisterAndCancel();
    }

}
