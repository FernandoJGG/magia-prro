package org.magiaperro.main;

import org.magiaperro.items.*;
import org.magiaperro.items.interfaces.IClickable;
import org.magiaperro.listeners.SpellsGuiListener;
import org.magiaperro.spells.SpellRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public static Main instance;

    @Override
    public void onEnable() {
        // Se ejecuta cuando el plugin se habilita (se carga)
        getLogger().info("El plugin MyPlugin se ha habilitado.");

        instance = this;
        ItemRegistry.register();
        SpellRegistry.register();
        
        getServer().getPluginManager().registerEvents(this, this);    
        getServer().getPluginManager().registerEvents(new SpellsGuiListener(), this);

        //getCommand("givecustomitem").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Se ejecuta cuando el plugin se deshabilita (se descarga)
        getLogger().info("El plugin MyPlugin se ha deshabilitado.");
    }
    
    
    //ToDo: Aislar en su propia clase (commands)
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givecustomitem")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                
                // Crear una instancia de CustomItem
                ItemStack varita = ItemRegistry.getCustomItem("varita_nieve").buildItemStack();
                
                // Entregar el item personalizado al jugador
                player.getInventory().addItem(varita);
               
                sender.sendMessage("Has recibido un item personalizado.");
                return true;
            }
        }
        return false;
    }
    
    // Escucha el evento de interacción del jugador
    // ToDo: Aislar en su propia clase (listeners)
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	ItemStack itemOnHand = player.getInventory().getItemInMainHand();
        	String metadata = itemOnHand.getPersistentDataContainer().get(Keys.CUSTOM_ITEM_ID, PersistentDataType.STRING);
        	if (metadata != null) {
	        	CustomItem itemType = ItemRegistry.getCustomItem(metadata);
	        	if(itemType instanceof IClickable) {
		        	((IClickable) itemType).onRightClick(event);
	        	}
        	}
        }
    }
}