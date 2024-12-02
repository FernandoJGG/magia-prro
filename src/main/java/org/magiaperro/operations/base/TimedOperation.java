package org.magiaperro.operations.base;

import java.time.Instant;

// Una operacion que termina en un instante de tiempo determinado
// Notar que si se pausa y se reaunuda ya completa, se saltan todos los continueFunction intermedios
// Por lo que en bloques las acciones de continueFunction solo deben ser esteticos (particulas, sonidos, etc)
public class TimedOperation extends BaseOperation {

    protected OperationFunction finishFunction;
    
    // Si es null, se finaliza instantaneamente
    protected Long finishInstant;

    public TimedOperation(OperationFunction continueFunction, OperationFunction finishFunction, Long finishInstant, Long ticksPerCycle) {
    	super(continueFunction, ticksPerCycle);
		this.finishFunction = finishFunction;
		this.finishInstant = finishInstant;
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
//        Location location = tileState.getLocation();
//        Chunk chunk = location.getChunk();
//
//        if (!chunk.isLoaded()) {
//            // Cancelar la tarea si el chunk no está cargado
//            this.cancel();
//            return;
//        }
    	if(this.finishInstant != null) {
            Instant endTime = Instant.ofEpochMilli(this.finishInstant);
            if (Instant.now().isBefore(endTime)) {
                super.runCycle();
                return;
            }
    	}
    	this.finishFunction.run(this.cycle);
    	this.unregisterAndCancel();
    }

}
