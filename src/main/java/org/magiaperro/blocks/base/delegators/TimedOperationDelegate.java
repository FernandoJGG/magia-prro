package org.magiaperro.blocks.base.delegators;

import java.util.Map;
import java.time.Instant;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.UUID;

import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.base.CustomBlock;
import org.magiaperro.helpers.TileStateHelper;
import org.magiaperro.helpers.pdc.TileStateProperty;
import org.magiaperro.operations.base.OperationHandler;
import org.magiaperro.operations.base.TimedOperation;


public class TimedOperationDelegate {

    // Mapa de operaciones registradas, cada una identificada por su ID
    private final Map<String, BlockOperation> operations = new HashMap<>();

    // Clase interna para gestionar cada operación
    private class BlockOperation {
        public String id;
        public OperationHandler<TimedOperation> handler;
        public TileStateProperty<Long> finishTimeProperty;
        public BiConsumer<TileState, Integer> onBurn;
        public Consumer<TileState> onFinish;
        public long burnTicks;

        public BlockOperation(String id, long burnTicks,
        		BiConsumer<TileState, Integer> onBurn, 
        		Consumer<TileState> onFinish) {
            this.id = id;
            this.handler = new OperationHandler<>();
            this.finishTimeProperty = new TileStateProperty<>("finish_time_"+id, PersistentDataType.LONG);
            this.onBurn = onBurn;
            this.onFinish = onFinish;
            this.burnTicks = burnTicks;
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
    public void registerOperation(String id, long burnTicks, 
    							BiConsumer<TileState, Integer> onBurn,
    							Consumer<TileState> onFinish) {
        operations.put(id, new BlockOperation(id, burnTicks, onBurn, onFinish));
    }

    /**
     * Carga todas las operaciones registradas desde el estado del bloque.
     */
    public void onLoad(TileState tileState) {
        for (BlockOperation operation : operations.values()) {
            Long endTime = operation.finishTimeProperty.getValue(tileState);
            if (endTime != null && endTime > 0) {
                TimedOperation timedOperation = createTimedOperation(tileState, operation);
                UUID guid = CustomBlock.getGuidFromTileState(tileState);
                operation.handler.startOperation(guid, timedOperation);
            }
        }
    }
    
    public void onUnload(TileState tileState) {
        for (BlockOperation operation : operations.values()) {
            stopOperation(tileState, operation.id);
        }
    }

    /**
     * Inicia una operación específica.
     */
    public void startOperation(TileState tileState, String id) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;
        
        Long endInstant = Instant.now().plusMillis(operation.burnTicks * 50).toEpochMilli();
        this.startOperation(tileState, id, endInstant);
    }
    

    /**
     * Inicia una operación específica programada para acabar en un instante determinado.
     */
    public void startOperation(TileState tileState, String id, Long endInstant) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        UUID guid = CustomBlock.getGuidFromTileState(tileState);
        operation.finishTimeProperty.setValue(tileState, endInstant);
        tileState.update();

        TimedOperation timedOperation = createTimedOperation(tileState, operation);
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
        operation.finishTimeProperty.setValue(tileState, 0L);
    }

    /**
     * Crea una instancia de TimedOperation basada en la lógica proporcionada.
     */
    private TimedOperation createTimedOperation(TileState tileState, BlockOperation operation) {
        return new TimedOperation(
            (c) -> TileStateHelper.updateAndExecute(tileState,
            		(updatedTileState) -> operation.onBurn.accept(updatedTileState, c)
            ),
            (c) -> TileStateHelper.updateAndExecute(tileState,
            		(updatedTileState) -> this.finishFunction(updatedTileState, operation)
            ),
            operation.finishTimeProperty.getValue(tileState),
            //TODO: Tiempo de tick personalizable
            20L // Intervalo de ejecución, por ejemplo, 20 ticks = 1 segundo
        );
    }
    
    private void finishFunction(TileState tileState, BlockOperation operation) {
    	operation.finishTimeProperty.setValue(tileState, 0L);
    	operation.onFinish.accept(tileState);
    }
}

