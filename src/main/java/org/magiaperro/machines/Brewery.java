package org.magiaperro.machines;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.magiaperro.helpers.InventoryHelper;
import org.magiaperro.helpers.pdc.PDCProperty;
import org.magiaperro.items.base.CustomItem;
import org.magiaperro.items.base.ItemID;
import org.magiaperro.machines.base.IMachineClickable;
import org.magiaperro.machines.base.IMachineData;
import org.magiaperro.machines.base.IMachineLoadable;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineID;
import org.magiaperro.machines.base.delegators.TimedOperationDelegate;


public class Brewery extends Machine implements IMachineLoadable, IMachineClickable {
	
	private final TimedOperationDelegate operationDelegate;	
    private static final String BREW = "brew";
    
	public static PDCProperty<Integer> servings = new PDCProperty<Integer>("servings", PersistentDataType.INTEGER);
	public static PDCProperty<Integer> ingredient = new PDCProperty<Integer>("ingredient", PersistentDataType.INTEGER);
	public static PDCProperty<Boolean> isBrewed = new PDCProperty<Boolean>("brewed", PersistentDataType.BOOLEAN);

    
	public Brewery(MachineID id) {
		super(id, ItemID.Brewery);
		
		this.operationDelegate = new TimedOperationDelegate();
		

        operationDelegate.registerOperation(
        	BREW,
            20L * 10, // 10 secs
            this::onBrew, 
            this::onFinish
        );
	}
	
	public void onBrew(IMachineData machineData, int cycle) {
		//soniditos y particulas
		
		Location location = machineData.getLocation();
		World world = machineData.getWorld();
//		double[][] positions = new double[][] {
//			{0.5, 1.2, 0.5}
//		};
//     
//
//	    for (double[] pos : positions) {
//	        Location particleLocation = location.clone().add(pos[0], pos[1], pos[2]);
//	        world.spawnParticle(Particle.BUBBLE, particleLocation, 0, 0, 0, 0, 0);
//	    }
        if(cycle%2 == 1)
        	world.playSound(location, Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, 1.4f, 1.0f);
	}
	
	public void onFinish(IMachineData machineData, Long a, Long b) {
		Location location = machineData.getLocation();
		World world = machineData.getWorld();
		
		isBrewed.setValue(machineData.getPDC(), true);
		machineData.update();
		
    	world.playSound(location, Sound.BLOCK_BREWING_STAND_BREW, 1.5f, 1.0f);
	}

	@Override
	public void onDisappear(IMachineData machineData) {
		super.onDisappear(machineData);
		this.operationDelegate.onUnload(machineData);
	}
	
	@Override
	public void onLoad(IMachineData machineData) {
		this.operationDelegate.onLoad(machineData);
		
	}
	
	@Override
	public void onUnload(IMachineData machineData) {
		this.operationDelegate.onUnload(machineData);
		
	}

	@Override
	public void onRightClick(PlayerEvent event, IMachineData machineData) {
		Player player = event.getPlayer();
		Integer servingsLeft = servings.getValue(machineData.getPDC());
		Integer ingredientId = ingredient.getValue(machineData.getPDC());
		Boolean brewed = isBrewed.getValue(machineData.getPDC());
		
		//TODO: REfactorizar este espagueti
        for(EquipmentSlot hand : InventoryHelper.hands()) {
    		ItemStack itemStack = player.getInventory().getItem(hand);
			
    		if(brewed==null || !brewed) {
    			if(CustomItem.isVanillaItem(itemStack, Material.WATER_BUCKET)) {
        			if(servingsLeft== null || servingsLeft == 0) {
        				servings.setValue(machineData.getPDC(), 6);
        				if(ingredientId != null && ingredientId > 0) {
        					this.operationDelegate.startOperation(machineData, BREW);
        				}
        				ItemStack emptyBucket = new ItemStack(Material.BUCKET);
        				player.getInventory().setItem(hand, emptyBucket);
        				break;
        			}
        		}
        		else if (CustomItem.isVanillaItem(itemStack, Material.WHEAT)) {
        			if(ingredientId == null || ingredientId == 0) {
        				ingredient.setValue(machineData.getPDC(), 1);
        				if(servingsLeft!= null && servingsLeft > 0) {
        					this.operationDelegate.startOperation(machineData, BREW);
        				}
        				itemStack.subtract();
        				break;
        			}
        		}
    			
    		}
			else {
				if(CustomItem.isVanillaItem(itemStack, Material.GLASS_BOTTLE)) {
	    			servings.setValue(machineData.getPDC(), servingsLeft-1);
		    		if(servingsLeft == 1) {
		    			isBrewed.setValue(machineData.getPDC(), false);
		    			ingredient.setValue(machineData.getPDC(), 0);
		    		}
	
					itemStack.subtract();
					ItemStack result = CustomItem.fromId(ItemID.Beer).buildItemStack();

					HashMap<Integer, ItemStack> notAdded = player.getInventory().addItem(result);
	
					// Si el inventario está lleno, los elementos que no pudieron ser añadidos estarán en el Map
					if (!notAdded.isEmpty()) {
					    // El inventario está lleno, dropeamos los ítems que no se pudieron añadir
					    for (ItemStack item : notAdded.values()) {
					        player.getWorld().dropItemNaturally(player.getLocation(), item);
					    }
					}
			    	player.playSound(machineData.getLocation(), Sound.ITEM_BUCKET_FILL, 1.5f, 1.0f);
					
		    		break;
				}
	    	}
    	}
		machineData.update();
		
	}
}
