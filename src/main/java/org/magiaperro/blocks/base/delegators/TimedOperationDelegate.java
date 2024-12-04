package org.magiaperro.blocks.base.delegators;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.helpers.TileStateHelper;
import org.magiaperro.helpers.pdc.TileStateProperty;
import org.magiaperro.operations.OperationHandler;
import org.magiaperro.operations.OfflineTimedOperation;


public class TimedOperationDelegate {

    // Mapa de operaciones registradas, cada una identificada por su ID
	private final Map<String, BlockOperation> operations = new HashMap<>();

    // Clase interna para gestionar cada operación
    private class BlockOperation {
        public String id;
        public OperationHandler<OfflineTimedOperation> handler;
        public TileStateProperty<Long> startTime;
        public BlockContinueConsumer onContinue;
        public BlockFinishConsumer onFinish;
        public Long ticksDuration;

        public BlockOperation(String id, Long duration,
        		BlockContinueConsumer onContinue, 
        		BlockFinishConsumer onFinish) {
            this.id = id;
            this.handler = new OperationHandler<>();
            this.startTime = new TileStateProperty<>("start_time_"+id, PersistentDataType.LONG);
            this.onContinue = onContinue;
            this.onFinish = onFinish;
            this.ticksDuration = duration;
        }
    }

    /**
     * Registra una nueva operación.
     *
     * @param id             Identificador de la operación.
     * @param finishTimeKey  Clave para almacenar el tiempo de finalización en PDC.
     * @param burnTicks      Duración de la operación en ticks.
     * @param onBurn         Función que se ejecuta en cada ciclo de la operación.
     * @param onFinish       Función que se ejecuta al finalizar la operación.
     */
    public void registerOperation(String id, Long duration, 
    							BlockContinueConsumer onContinue,
    							BlockFinishConsumer onFinish) {
        operations.put(id, new BlockOperation(id, duration, onContinue, onFinish));
    }

    /**
     * Carga todas las operaciones registradas desde el estado del bloque.
     */
    public void onLoad(TileState tileState) {
        for (BlockOperation operation : operations.values()) {
        	resumeOperation(tileState, operation);
        }
    }
    
    public void onUnload(TileState tileState) {
        for (BlockOperation operation : operations.values()) {
            stopOperation(tileState, operation.id);
        }
    }
    
    /**
     * Reanuda una operacion que ya estaba iniciada, si la hubiese
     */
    private void resumeOperation(TileState tileState, BlockOperation operation) {
        Long startTime = operation.startTime.getValue(tileState);
        if (startTime != null && startTime > 0) {
            OfflineTimedOperation timedOperation = createTimedOperation(tileState, operation);
            UUID guid = CustomBlock.getGuidFromTileState(tileState);
            operation.handler.startOperation(guid, timedOperation);
        }
    }

    /**
     * Inicia una nueva operacion que inicia en el instante actual
     */
    public void startOperation(TileState tileState, String id) {
        this.startOperation(tileState, id, Bukkit.getWorld("world").getFullTime());
    }
    

    /**
     *  Inicia una operación con un tiempo de inicio determinado
     */
    public void startOperation(TileState tileState, String id, Long startInstant) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        operation.startTime.setValue(tileState, startInstant);
        tileState.update();

        UUID guid = CustomBlock.getGuidFromTileState(tileState);
        OfflineTimedOperation timedOperation = createTimedOperation(tileState, operation);
        operation.handler.startOperation(guid, timedOperation);
    }

    /**
     * Detiene una operación específica.
     */
    public void stopOperation(TileState tileState, String id) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        UUID guid = CustomBlock.getGuidFromTileState(tileState);
        operation.handler.endOperation(guid);
        operation.startTime.setValue(tileState, 0L);
    }
    
    /**
     * Detiene una operacion previa si existiese e inicia una nueva
     */
    public void restartOperation(TileState tileState, String id, Long offset) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        Long startInstant = Bukkit.getWorld("world").getFullTime() - offset;
        operation.startTime.setValue(tileState, startInstant);
        tileState.update();

        UUID guid = CustomBlock.getGuidFromTileState(tileState);
        OfflineTimedOperation timedOperation = createTimedOperation(tileState, operation);
        operation.handler.restartOperation(guid, timedOperation);
    }

    /**
     * Crea una instancia de TimedOperation.
     */
    private OfflineTimedOperation createTimedOperation(TileState tileState, BlockOperation operation) {
        return new OfflineTimedOperation(
            (c) -> TileStateHelper.updateAndExecute(tileState,
            		(updatedTileState) -> operation.onContinue.run(updatedTileState, c)
            ),
            (c,e) -> TileStateHelper.updateAndExecute(tileState,
            		(updatedTileState) -> this.finishFunction(updatedTileState, operation,c,e)
            ),
            operation.startTime.getValue(tileState),
            operation.ticksDuration, //TODO: Tiempo de tick personalizable
            20L // Intervalo de ejecución, por ejemplo, 20 ticks = 1 segundo
        );
    }
    
    private void finishFunction(TileState tileState, BlockOperation operation, Long fullCycles, Long excess) {
    	operation.startTime.setValue(tileState, 0L);
    	operation.onFinish.finish(tileState, fullCycles, excess);
    }
}

