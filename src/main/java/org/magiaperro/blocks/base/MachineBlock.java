package org.magiaperro.blocks.base;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.NamespacedKey;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.main.Main;
import org.magiaperro.operations.base.BaseOperation;
import org.magiaperro.operations.base.OperationHandler;
import org.magiaperro.operations.base.TimedOperation;

//TODO: Una funcion startOperation(TileState, index);
public abstract class MachineBlock extends CustomBlock implements ILoadBlock {

	private List<BlockOperation> timedOps = Collections.emptyList();
	private List<OperationHandler<TimedOperation>> timedHandlers = Collections.emptyList();
	private List<BlockOperation> auraOps = Collections.emptyList();
	private List<OperationHandler<BaseOperation>> auraHandlers = Collections.emptyList();
	
	//TODO Mejor implementar como una lista de int 
	private static String operationKeyName = "operation_time_";
	
	public MachineBlock(BlockID id, ItemID itemBlockId, int operationTicks) {
		super(id, itemBlockId);
	}
	
	public void setAuras(BlockOperation... auras) {
		this.auraHandlers = IntStream.range(0, auras.length)
						.mapToObj(i->new OperationHandler<>())
						.collect(Collectors.toList());
		this.auraOps = List.of(auras);
	}
	
	public void setOperations(BlockOperation... operations) {
		this.timedHandlers = IntStream.range(0, operations.length)
				.mapToObj(i->new OperationHandler<TimedOperation>())
				.collect(Collectors.toList());
		this.timedOps = List.of(operations);
	}
	
	@Override
	public void instantiateBlock(TileState tileState) {
		super.instantiateBlock(tileState);    	
		for (int i = 0; i < this.timedHandlers.size(); i++) {
		    NamespacedKey key = new NamespacedKey(Main.instance, operationKeyName + i);
		    tileState.getPersistentDataContainer().set(key, PersistentDataType.LONG, 0L);
		}
		
	}

	@Override
	public void onLoad(TileState tileState) {
		UUID guid = getGuidFromTileState(tileState);

		for (int i = 0; i < this.timedHandlers.size(); i++) {
		    OperationHandler<TimedOperation> handler = this.timedHandlers.get(i);
		    NamespacedKey key = new NamespacedKey(Main.instance, operationKeyName + i);
			Long endTime = tileState.getPersistentDataContainer().get(key, PersistentDataType.LONG);
			if(endTime != null && endTime > 0) {
				handler.startOperation(guid, getTimedOperation(tileState, i));
			}
		}
		for (int i = 0; i < this.auraHandlers.size(); i++) {
		    OperationHandler<BaseOperation> handler = this.auraHandlers.get(i);
			handler.startOperation(guid, getAuraOperation(tileState, i));
		}
	}

	@Override
	public void onUnload(TileState tileState) {
		this.cancellAllOperations(tileState);
	}
	
	@Override
	public void onBlockDisappear(TileState tileState) {
		super.onBlockDisappear(tileState);
		this.cancellAllOperations(tileState);
	}
	
	public void cancellAllOperations(TileState tileState) {
		UUID guid = getGuidFromTileState(tileState);
		
		for (OperationHandler<TimedOperation> handler: this.timedHandlers) {
			handler.endOperation(guid);
		}
		for (OperationHandler<BaseOperation> handler: this.auraHandlers) {
			handler.endOperation(guid);
		}
	}
	
	public TimedOperation getTimedOperation(TileState tileState, int opIndex) {
		BlockOperation timedOp = timedOps.get(opIndex);
		return getTimedOperation(tileState, opIndex, timedOp.ticksPerCycle);
	}
	
	public TimedOperation getTimedOperation(TileState tileState, int opIndex, Long totalTicks) {
		BlockOperation timedOp = timedOps.get(opIndex);
		return new TimedOperation((c) -> onOperationCycle(tileState, c, opIndex),
				(c) -> onOperationFinish(tileState, opIndex),
				totalTicks, timedOp.ticksPerCycle);
	}
	
	public BaseOperation getAuraOperation(TileState tileState, int opIndex) {
		BlockOperation aura = auraOps.get(opIndex);
		return new BaseOperation((c) -> onAuraCycle(tileState, c, opIndex), aura.ticksPerCycle);
	}
	
	public void onOperationFinish(TileState tileState, int opIndex) {
	    NamespacedKey key = new NamespacedKey(Main.instance, operationKeyName + opIndex);
		tileState.getPersistentDataContainer().set(key, PersistentDataType.LONG, 0L);
	}
	
	public abstract void onAuraCycle(TileState tileState, int cycle, int opIndex);
	
	public abstract void onOperationCycle(TileState tileState, int cycle, int opIndex);
	



}
