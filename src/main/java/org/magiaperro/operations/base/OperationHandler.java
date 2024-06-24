package org.magiaperro.operations.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.UUID;

public class OperationHandler<T extends BaseOperation> {

	protected final Map<UUID, T> operations = new ConcurrentHashMap<>();
    
    public OperationHandler() {
	}
    
    public boolean startOperation(UUID guid, T operation) {
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
    
    public boolean removeOperation(UUID guid) {
        T operation = operations.remove(guid);
        
        return operation != null;
    }
    
    
//    public @Nullable ItemStack getProgressBar() {
//        return progressBar;
//    }
}
