package org.magiaperro.operations;

/**
 * Singleton para casteos de entidades, como magias o items.
 */
public class CastingHandler extends OperationHandler<OnlineTimedOperation> {
	
	private static CastingHandler casters;
	
	public static CastingHandler getInstance() {
		if(casters == null) {
			casters = new CastingHandler();
		}
		return casters;
	}
	
	
}
