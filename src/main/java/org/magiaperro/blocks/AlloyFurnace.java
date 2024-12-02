package org.magiaperro.blocks;

import java.time.Instant;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.TileState;
import org.bukkit.block.data.type.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.blocks.base.BlockID;
import org.magiaperro.blocks.base.ILoadBlock;
import org.magiaperro.blocks.base.InventoryBlock;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.GuiGraphic;
import org.magiaperro.gui.base.factories.BaseConcurrentGuiFactory;
import org.magiaperro.gui.base.strategies.PDCTileSaveStrategy;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.operations.base.OperationHandler;
import org.magiaperro.operations.base.TimedOperation;
import org.magiaperro.helpers.pdc.TileStateProperty;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

//TODO: Pues casi todo. pero por abreviar:
// El horno se queda encendido al hornear un item con el inv abierto y cerrarlo, pq? el save()? 
// Refinar y usar MachineBlock
// Incluso otra subclase para maquinas con recetas
// Expandir la clase recetas para leer las recetas de forma modular
// Reempezar la task cuando la receta siga siendo valida.
// Las mecanicas de encender/apagar el horno moverlos a su propia funcion, que se ejecuta cuando
// cancelar/iniciar una funcion da true en su handler
public class AlloyFurnace extends InventoryBlock implements ILoadBlock {

	public static final int size = 27;
	public static final int burnTicks = 100*3; // 5 secs
	final OperationHandler<TimedOperation> itemBurnHandler = new OperationHandler<>();
	
	public static TileStateProperty<Long> finishTime = new TileStateProperty<Long>("operation_finish_time", PersistentDataType.LONG);
	
	public AlloyFurnace(BlockID id) {
		super(id, ItemID.AlloyFurnace, size);
	}
	
	@Override
	public void instantiateBlock(TileState tileState) {
		super.instantiateBlock(tileState);   
		finishTime.setValue(tileState, 0L);
	}
	
	@Override
	public void onBlockDisappear(TileState tileState) {
		super.onBlockDisappear(tileState);
		itemBurnHandler.endOperation(getGuidFromTileState(tileState));
	}

	@Override
	public void onRightClick(PlayerInteractEvent event, TileState tileState) {
		Player player = event.getPlayer();
		UUID guid = getGuidFromTileState(tileState);
		
		GuiGraphic[] graphics = new GuiGraphic[] {
          	new GuiGraphic (
              	/*Material*/ 	Material.BLACK_STAINED_GLASS_PANE,
            	/*Slots*/ 		new int[] {0,1,2,3,4,5,6,7,8,9,11,13,15,17,18,19,20,21,22,23,24,25,26},
            	/*Text*/ 		Component.text("")
        	),
          	new GuiGraphic (
                /*Material*/ 	Material.FLINT_AND_STEEL,
                /*Slots*/ 		new int[] {14},
                /*Text*/ 		Component.text("WIP").color(NamedTextColor.RED)
            ),
        };
		
		//TODO: Funcion para pasarle a la gui cuando A. se recoge item B. se mueve item persistible
		//TODO: Añadir slots de recogida
		ConcurrentPersistentGui gui = ConcurrentPersistentGui.getInventoryHolder(
			new BaseConcurrentGuiFactory (
		     	/* Size */		size, 
		      	/* Title */		Component.text("Horno que no hornea"), 
		      	/* Inputs */	new int[] {10,12}, 
		      	/* Outputs */	new int[] {16},
		    	/* Strategy */	new PDCTileSaveStrategy(tileState, guid),
		    	/* Graphics */	graphics
		    ),
			guid
	    );
		gui.setListener(() -> onItemPlaced(tileState));
		
		gui.openInterface(player);
	}

	@Override
	public void onLoad(TileState tileState) {
		//Comprobar si habia una operacion en progreso
		Long endTime = finishTime.getValue(tileState);
		if(endTime != null && endTime > 0)
			itemBurnHandler.startOperation(getGuidFromTileState(tileState), getBurnOperation(tileState, endTime));
	}

	@Override
	public void onUnload(TileState tileState) {
		itemBurnHandler.endOperation(getGuidFromTileState(tileState));
	}
	
	public TimedOperation getBurnOperation(TileState tileState) {
		Long endTime = Instant.now().plusMillis(burnTicks*50).toEpochMilli();
		return getBurnOperation(tileState, endTime);
	}
	
	public TimedOperation getBurnOperation(TileState tileState, Long endTime) {
		return new TimedOperation((c) -> onBurn(tileState, c),(c) -> onFinish(tileState), endTime, 20L);
	}
	
	public void onFinish(TileState tileState) {
		ItemStack[] inventory = getInventoryFromTileState(tileState);
		if((inventory[2] == null || CustomItem.isCustomItem(inventory[2], ItemID.BronzeIngot)) && 
				isValidRecipe(inventory)) {
	        World world = tileState.getWorld();
			Location location = tileState.getLocation();

	    	Furnace horno = (Furnace) tileState.getBlockData();
			BlockFace facing = horno.getFacing();
			
	        // Define las posiciones relativas para las partículas
	        double[][] positions = {
	        	{0.5*facing.getModX()+0.5, 0.2, 0.5*facing.getModZ()+0.5}, // En la cara del bloque
	        };
	
	        for (double[] pos : positions) {
	            Location particleLocation = location.clone().add(pos[0], pos[1], pos[2]);
	            world.spawnParticle(Particle.CLOUD, particleLocation, 0, 0, 0, 0, 0);
	            world.playSound(location, Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
	            Bukkit.getWorlds().get(0).getFullTime();
	        }

			setItem(tileState, inventory[0].subtract(), 0);
			setItem(tileState, inventory[1].subtract(), 1);
			if(inventory[2] != null)
				setItem(tileState, inventory[2].add(), 2);
			else
				setItem(tileState, CustomItem.fromId(ItemID.BronzeIngot).buildItemStack(), 2);
	        
		}
		stopBurn(tileState);
	}
	
	public void onBurn(TileState tileState, int cycle) {
		ItemStack[] inventory = getInventoryFromTileState(tileState);
		if((inventory[2] == null || CustomItem.isCustomItem(inventory[2], ItemID.BronzeIngot)) && 
				isValidRecipe(inventory)) {
	        World world = tileState.getWorld();
			Location location = tileState.getLocation();
			
	        // Define las posiciones relativas para las partículas
	    	Furnace horno = (Furnace) tileState.getBlockData();
			BlockFace facing = horno.getFacing();
			
			//Enciende el horno
			horno.setLit(true);
	        tileState.setBlockData(horno);
	        tileState.update();
	        
	        double[][] positions = {
	        	//{0.5, 1.0, 0.5}, // En la parte superior del bloque
	            {0.5*facing.getModX()+0.5, 0.2, 0.5*facing.getModZ()+0.5}, // En la cara del bloque
	            //{0.2, 0.5, 0.2}, // En una esquina del bloque
	            //{0.8, 0.5, 0.8}  // En la otra esquina del bloque
	        };

	        for (double[] pos : positions) {
	            Location particleLocation = location.clone().add(pos[0], pos[1], pos[2]);
	            world.spawnParticle(Particle.FLAME, particleLocation, 0, 0, 0, 0, 0);
	            // Reproduce el sonido de quemado
	            if(cycle%2 == 0)
	            	world.playSound(location, Sound.BLOCK_FIRE_AMBIENT, 1.5f, 1.0f);
	        }
		}
		else {
			stopBurn(tileState);
		}
	}
	
	public void stopBurn(TileState tileState) {
    	Furnace horno = (Furnace) tileState.getBlockData();
		horno.setLit(false);
        tileState.setBlockData(horno);
        tileState.update();
        
		itemBurnHandler.endOperation(getGuidFromTileState(tileState));
		finishTime.setValue(tileState, 0L);		
	}
	
	//TODO: Tambien comprobar si la receta cambia para cancelar y reiniciar
	//(Al haber solo una receta ahora no importa mucho)
	public void onItemPlaced(TileState tileState) {
		UUID guid = getGuidFromTileState(tileState);
		if(isValidRecipe(tileState)) {
			itemBurnHandler.startOperation(guid, getBurnOperation(tileState));
		}
		else {
			stopBurn(tileState);
		}
	}
	
	public boolean isValidRecipe(TileState tileState) {
		ItemStack[] inventory = getInventoryFromTileState(tileState);

		return isValidRecipe(inventory);
	}
	public boolean isValidRecipe(ItemStack[] inventory) {
//		for(ItemStack is : inventory) {
//			Bukkit.getLogger().info((is == null ? "null" : is.getDisplayName())
//					+ " " + CustomItem.isVanillaItem(is, Material.RAW_COPPER)
//					+ " " + CustomItem.isCustomItem(is, ItemID.LeadOre));
//		}
		return CustomItem.isVanillaItem(inventory[0], Material.RAW_COPPER) &&
			   CustomItem.isCustomItem(inventory[1], ItemID.LeadOre);
	}

}
