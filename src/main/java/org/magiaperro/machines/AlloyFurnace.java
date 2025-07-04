package org.magiaperro.machines;

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
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.magiaperro.gui.base.ConcurrentPersistentGui;
import org.magiaperro.gui.base.GuiGraphic;
import org.magiaperro.gui.base.factories.BaseConcurrentGuiFactory;
import org.magiaperro.gui.base.strategies.MachineSaveStrategy;
import org.magiaperro.helpers.TileStateHelper;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.machines.base.MachineID;
import org.magiaperro.machines.base.IMachineLoadable;
import org.magiaperro.machines.base.InventoryMachine;
import org.magiaperro.machines.base.MachineBlock;
import org.magiaperro.machines.base.delegators.TimedOperationDelegate;
import org.magiaperro.machines.base.IMachineData;

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
public class AlloyFurnace extends InventoryMachine implements IMachineLoadable {

	public static final int size = 27;
	public static final Long burnTicks = 5*20L; // 5 secs

    private final TimedOperationDelegate operationDelegate;	
    private static final String SMELT = "smelt";
    
	public AlloyFurnace(MachineID id) {
		super(id, ItemID.AlloyFurnace, size);
        this.operationDelegate = new TimedOperationDelegate();

        operationDelegate.registerOperation(
        	SMELT,
            burnTicks,
            this::onBurn, 
            this::onFinish
        );
	}
	
	@Override
	public void instantiateMachine(IMachineData machineData) {
		super.instantiateMachine(machineData);
		// Iniciar timers a 0 o dejar a null?
	}
	
	@Override
	public void onDisappear(IMachineData machineData) {
		super.onDisappear(machineData);
		this.operationDelegate.onUnload(machineData);
	}

	@Override
	public void onRightClick(PlayerEvent event, IMachineData machineData) {
		Player player = event.getPlayer();
		UUID guid = getGuidFromMachine(machineData);
		
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
		      	/* Title */		Component.text("Horno de aleación"), 
		      	/* Inputs */	new int[] {10,12}, 
		      	/* Outputs */	new int[] {16},
		    	/* Strategy */	new MachineSaveStrategy(machineData, guid),
		    	/* Graphics */	graphics
		    ),
			guid
	    );
		gui.setListener(() -> TileStateHelper.updateAndExecute(machineData,
        		(updatedMachineData) -> onItemPlaced(updatedMachineData)
        ));
		
		gui.openInterface(player);
	}

	@Override
	public void onLoad(IMachineData machineData) {
		this.operationDelegate.onLoad(machineData);
	}

	@Override
	public void onUnload(IMachineData machineData) {
		this.operationDelegate.onUnload(machineData);
	}
	
	private void onFinish(IMachineData machineData, Long fullCycles, Long excess) {
		ItemStack[] inventory = getInventoryFromMachine(machineData);
		if((inventory[2] == null || CustomItem.isCustomItem(inventory[2], ItemID.BronzeIngot)) && 
				isValidRecipe(inventory)) {
	        World world = machineData.getWorld();
			Location location = machineData.getLocation();

			double[][] positions;
			if(machineData instanceof MachineBlock) {
				TileState tileState = ((MachineBlock)machineData).getTileState();
				
		    	Furnace horno = (Furnace) tileState.getBlockData();
				BlockFace facing = horno.getFacing();
				
		        // Define las posiciones relativas para las partículas
		        positions = new double[][]{
		        	{0.5*facing.getModX()+0.5, 0.2, 0.5*facing.getModZ()+0.5}, // En la cara del bloque
		        };
			}
			else {
				positions = new double[][] {
					{0, 1, 0}
				};
			}
	
	        for (double[] pos : positions) {
	            Location particleLocation = location.clone().add(pos[0], pos[1], pos[2]);
	            world.spawnParticle(Particle.CLOUD, particleLocation, 0, 0, 0, 0, 0);
	            world.playSound(location, Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
	            Bukkit.getWorlds().get(0).getFullTime();
	        }
	        
	        int amountIngots = inventory[2] == null ? 0 : inventory[2].getAmount();
	        int itemsSmelt = (int) Math.min(Math.min(64-amountIngots, fullCycles),
	        				Math.min(inventory[0].getAmount(), inventory[1].getAmount()));
	        
			setItem(machineData, inventory[0].subtract(itemsSmelt), 0);
			setItem(machineData, inventory[1].subtract(itemsSmelt), 1);
			if(inventory[2] != null)
				setItem(machineData, inventory[2].add(itemsSmelt), 2);
			else
				setItem(machineData, CustomItem.fromId(ItemID.BronzeIngot).buildItemStack(itemsSmelt), 2);
	        
		}
		this.operationDelegate.restartOperation(machineData, SMELT, excess);
	}
	
	private void onBurn(IMachineData machineData, int cycle) {
		ItemStack[] inventory = getInventoryFromMachine(machineData);
        int amountIngots = inventory[2] == null ? 0 : inventory[2].getAmount();
		if((inventory[2] == null || CustomItem.isCustomItem(inventory[2], ItemID.BronzeIngot)) && 
				isValidRecipe(inventory) && amountIngots < 64) {
	        World world = machineData.getWorld();
			Location location = machineData.getLocation();

	        double[][] positions;
			if(machineData instanceof MachineBlock) {
				TileState tileState = ((MachineBlock)machineData).getTileState();
		        // Define las posiciones relativas para las partículas
		    	Furnace horno = (Furnace) tileState.getBlockData();
				BlockFace facing = horno.getFacing();
				
				//Enciende el horno
				horno.setLit(true);
		        tileState.setBlockData(horno);
		        tileState.update();
		        positions = new double[][]{
			        	//{0.5, 1.0, 0.5}, // En la parte superior del bloque
			            {0.5*facing.getModX()+0.5, 0.2, 0.5*facing.getModZ()+0.5}, // En la cara del bloque
			            //{0.2, 0.5, 0.2}, // En una esquina del bloque
			            //{0.8, 0.5, 0.8}  // En la otra esquina del bloque
			        };
			}
			else {
				positions = new double[][] {
					{0, 1, 0}
				};
			}
	         

	        for (double[] pos : positions) {
	            Location particleLocation = location.clone().add(pos[0], pos[1], pos[2]);
	            world.spawnParticle(Particle.FLAME, particleLocation, 0, 0, 0, 0, 0);
	            // Reproduce el sonido de quemado
	            if(cycle%2 == 1)
	            	world.playSound(location, Sound.BLOCK_FIRE_AMBIENT, 1.4f, 1.0f);
	        }
		}
		else {
			stopBurn(machineData);
		}
	}
	
	private void stopBurn(IMachineData machineData) {
		if(machineData instanceof MachineBlock) {
			TileState tileState = ((MachineBlock)machineData).getTileState();
	    	Furnace horno = (Furnace) tileState.getBlockData();
			horno.setLit(false);
	        tileState.setBlockData(horno);
		}
        machineData.update();
        
		this.operationDelegate.stopOperation(machineData, SMELT);
	}
	
	//TODO: Tambien comprobar si la receta cambia para cancelar y reiniciar
	//(Al haber solo una receta ahora no importa mucho)
	private void onItemPlaced(IMachineData machineData) {
		if(isValidRecipe(machineData)) {
			this.operationDelegate.startOperation(machineData, SMELT);
		}
		else {
			stopBurn(machineData);
		}
	}
	
	private boolean isValidRecipe(IMachineData machineData) {
		ItemStack[] inventory = getInventoryFromMachine(machineData);

		return isValidRecipe(inventory);
	}
	private boolean isValidRecipe(ItemStack[] inventory) {
		return CustomItem.isVanillaItem(inventory[0], Material.RAW_COPPER) &&
			   CustomItem.isCustomItem(inventory[1], ItemID.LeadOre);
	}

}
