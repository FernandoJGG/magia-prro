package org.magiaperro.blocks.base;

import java.time.Instant;
import java.util.UUID;

import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.factories.ConcurrentGuiFactory;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.main.Keys;
import org.magiaperro.operations.base.OperationHandler;
import org.magiaperro.operations.base.TimedOperation;

public abstract class MachineBlock extends InventoryBlock implements ILoadBlock {

	public final int operationTicks;
	final OperationHandler<TimedOperation> operationHandler = new OperationHandler<>();
	private final ConcurrentGuiFactory guiFactory;
	
	public MachineBlock(BlockID id, ItemID itemBlockId, int inventorySize, int operationTicks,
			ConcurrentGuiFactory guiFactory) {
		super(id, itemBlockId, inventorySize);
		this.operationTicks = operationTicks;
		this.guiFactory = guiFactory;
	}
	
	@Override
	public void instantiateBlock(TileState tileState) {
		super.instantiateBlock(tileState);    	
		tileState.getPersistentDataContainer().set(Keys.BLOCK_OPERATION_FINISH_TIME, PersistentDataType.LONG, 0L);
	}
	
	@Override
	public void onBlockDisappear(TileState tileState) {
		super.onBlockDisappear(tileState);
		operationHandler.endOperation(getGuidFromTileState(tileState));
	}

	@Override
	public void onLoad(TileState tileState) {
		//Comprobar si habia una operacion en progreso
		Long endTime = tileState.getPersistentDataContainer().get(Keys.BLOCK_OPERATION_FINISH_TIME, PersistentDataType.LONG);
		if(endTime != null && endTime > 0)
			operationHandler.startOperation(getGuidFromTileState(tileState), getOperation(tileState, endTime));
	}

	@Override
	public void onUnload(TileState tileState) {
		operationHandler.endOperation(getGuidFromTileState(tileState));
	}
	
	public TimedOperation getNewOperation(TileState tileState) {
		Long endTime = Instant.now().plusMillis(operationTicks*50).toEpochMilli();
		return getOperation(tileState, endTime);
	}
	
	public TimedOperation getOperation(TileState tileState, Long endTime) {
		return new TimedOperation((c) -> onOperationCycle(tileState, c),(c) -> onFinish(tileState), endTime, 20L);
	}
	
	public void onFinish(TileState tileState) {
		tileState.getPersistentDataContainer().set(Keys.BLOCK_OPERATION_FINISH_TIME, PersistentDataType.LONG, 0L);
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent event, TileState tileState) {
		Player player = event.getPlayer();
		UUID guid = getGuidFromTileState(tileState);
		
		//TODO: Funcion para pasarle a la gui cuando A. se recoge item B. se mueve item persistible
		//TODO: Añadir slots de recogida
		ConcurrentPersistentGui gui = ConcurrentPersistentGui.getInventoryHolder(
			this.guiFactory,
			guid
	    );
		gui.setListener(() -> onItemPlaced(tileState));
		
		gui.openInterface(player);
	}
	
	public abstract void onOperationCycle(TileState tileState, int cycle);
	
	public abstract void onItemPlaced(TileState tileState);



}
