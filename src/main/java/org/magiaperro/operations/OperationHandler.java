package org.magiaperro.operations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.UUID;

/**
 * Se encarga de la gestión de una operación, y asegura que una misma entidad
 * no pueda ejecutar más de una a la vez.
 */
public class OperationHandler<T extends BaseOperation> {

	protected final Map<UUID, T> operations = new ConcurrentHashMap<>();
    
    public OperationHandler() {
	}
    
    public boolean startOperation(UUID guid, T operation) {
    	operation.setCaller(guid, this);
        boolean put = operations.putIfAbsent(guid, operation) == null;
        if(put) {
        	operation.startOperation();
        }
        return put;
    }
    
    public T getOperation(UUID guid) {
        return operations.get(guid);
    }
    
    public boolean endOperation(UUID guid) {
        T operation = operations.remove(guid);

        if (operation != null && !operation.isCancelled()) {
            operation.cancel();

            return true;
        } else {
            return false;
        }
    }
    
    public boolean restartOperation(UUID guid, T operation) {
    	operation.setCaller(guid, this);
    	
        this.endOperation(guid);
        this.startOperation(guid, operation);
        
        return true;
    }
    
    protected boolean removeOperation(UUID guid) {
        T operation = operations.remove(guid);
        
        return operation != null;
    }
}
