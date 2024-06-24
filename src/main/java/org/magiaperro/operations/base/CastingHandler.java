package org.magiaperro.operations.base;

public class CastingHandler extends OperationHandler<TimedOperation> {
	
	private static CastingHandler casters;
	
	public static CastingHandler getInstance() {
		if(casters == null) {
			casters = new CastingHandler();
		}
		return casters;
	}
	
	
}
