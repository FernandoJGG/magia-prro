package org.magiaperro.operations;

public class CastingHandler extends OperationHandler<OfflineTimedOperation> {
	
	private static CastingHandler casters;
	
	public static CastingHandler getInstance() {
		if(casters == null) {
			casters = new CastingHandler();
		}
		return casters;
	}
	
	
}
