package org.magiaperro.machines.base.delegators;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.helpers.TileStateHelper;
import org.magiaperro.helpers.pdc.PDCProperty;
import org.magiaperro.machines.base.IMachineData;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.main.Main;
import org.magiaperro.operations.OperationHandler;
import org.magiaperro.operations.OfflineTimedOperation;

// TODO Añadir nuevos delegates para online/auras y no solo timed
// Quizas renombrar la semantica para que estos handlers sirvan para mobs custom
// IMachineData -> ICustomEntityData o IMedi(terranea)EntityData
public class TimedOperationDelegate {

    // Mapa de operaciones registradas, cada una identificada por su ID
	private final Map<String, BlockOperation> operations = new HashMap<>();

    // Clase interna para gestionar cada operación
    private class BlockOperation {
        public String id;
        public OperationHandler<OfflineTimedOperation> handler;
        public PDCProperty<Long> startTime;
        public MachineContinueConsumer onContinue;
        public MachineFinishConsumer onFinish;
        public Long ticksDuration;

        public BlockOperation(String id, Long duration,
        		MachineContinueConsumer onContinue, 
        		MachineFinishConsumer onFinish) {
            this.id = id;
            this.handler = new OperationHandler<>();
            this.startTime = new PDCProperty<>("start_time_"+id, PersistentDataType.LONG);
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
    							MachineContinueConsumer onContinue,
    							MachineFinishConsumer onFinish) {
        operations.put(id, new BlockOperation(id, duration, onContinue, onFinish));
    }

    /**
     * Carga todas las operaciones registradas desde el estado del bloque.
     */
    public void onLoad(IMachineData machineData) {
        for (BlockOperation operation : operations.values()) {
        	resumeOperation(machineData, operation);
        }
    }
    
    public void onUnload(IMachineData machineData) {
        for (BlockOperation operation : operations.values()) {
        	pauseOperation(machineData, operation.id);
        }
    }
    
    /**
     * Reanuda una operacion que ya estaba iniciada, si la hubiese
     */
    private void resumeOperation(IMachineData machineData, BlockOperation operation) {
        Long startTime = operation.startTime.getValue(machineData.getPDC());

        if (startTime != null && startTime > 0) {
            OfflineTimedOperation timedOperation = createTimedOperation(machineData, operation);
            UUID guid = Machine.getGuidFromMachine(machineData);
            operation.handler.startOperation(guid, timedOperation);
        }
    }

    /**
     * Inicia una nueva operacion que inicia en el instante actual
     */
    public void startOperation(IMachineData machineData, String id) {
        this.startOperation(machineData, id, Main.getWorldFullTime());
    }
    

    /**
     *  Inicia una operación con un tiempo de inicio determinado
     */
    public void startOperation(IMachineData machineData, String id, Long startInstant) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        operation.startTime.setValue(machineData.getPDC(), startInstant);
        machineData.update();

        UUID guid = Machine.getGuidFromMachine(machineData);
        OfflineTimedOperation timedOperation = createTimedOperation(machineData, operation);
        operation.handler.startOperation(guid, timedOperation);
    }
    /**
     * Pausa una operación específica, pero no reinicia el tiempo de inicio
     * Usada cuando una operacion se detiene por descarga del chunk
     */
    private void pauseOperation(IMachineData machineData, String id) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        UUID guid = Machine.getGuidFromMachine(machineData);
        operation.handler.endOperation(guid);
    }
    
    /**
     * Detiene una operación específica.
     */
    public void stopOperation(IMachineData machineData, String id) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        UUID guid = Machine.getGuidFromMachine(machineData);
        operation.handler.endOperation(guid);
        operation.startTime.setValue(machineData.getPDC(), 0L);
        machineData.update();
    }
    
    /**
     * Detiene una operacion previa si existiese e inicia una nueva
     */
    public void restartOperation(IMachineData machineData, String id, Long offset) {
        BlockOperation operation = operations.get(id);
        if (operation == null) return;

        Long startInstant = Main.getWorldFullTime() - offset;
        operation.startTime.setValue(machineData.getPDC(), startInstant);
        machineData.update();

        UUID guid = Machine.getGuidFromMachine(machineData);
        OfflineTimedOperation timedOperation = createTimedOperation(machineData, operation);
        operation.handler.restartOperation(guid, timedOperation);
    }

    /**
     * Crea una instancia de TimedOperation.
     */
    private OfflineTimedOperation createTimedOperation(IMachineData machineData, BlockOperation operation) {
        return new OfflineTimedOperation(
            (c) -> TileStateHelper.updateAndExecute(machineData,
            		(updatedTileState) -> operation.onContinue.run(updatedTileState, c)
            ),
            (c,e) -> TileStateHelper.updateAndExecute(machineData,
            		(updatedTileState) -> this.finishFunction(updatedTileState, operation,c,e)
            ),
            operation.startTime.getValue(machineData.getPDC()),
            operation.ticksDuration, //TODO: Tiempo de tick personalizable
            20L // Intervalo de ejecución, por ejemplo, 20 ticks = 1 segundo
        );
    }
    
    private void finishFunction(IMachineData machineData, BlockOperation operation, Long fullCycles, Long excess) {
    	operation.startTime.setValue(machineData.getPDC(), 0L);
    	machineData.update();
    	operation.onFinish.finish(machineData, fullCycles, excess);
    }
}

