package org.magiaperro.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.magiaperro.items.interfaces.IClickable;

import java.util.List;

public class Varita2 extends CustomItem implements IClickable {
	
	
    public Varita2(ItemID id, String itemName, Material material, List<String> lore) {
		super(id, itemName, material, lore);
	}


    // Manejar la acción al darle click derecho al item
    public void onRightClick(PlayerInteractEvent event, EquipmentSlot hand) {
        Player player = event.getPlayer();

	    // Crear una instancia de la bola de fuego
	    Snowball snowball = player.launchProjectile(Snowball.class);
	            
	    // Configurar las propiedades de la bola de fuego
	    snowball.setVelocity(player.getLocation().getDirection().multiply(1.5)); // Velocidad de la bola de nieve
	            
	    // Agrega aquí la lógica de la acción especial al darle click derecho al item
	    // Por ejemplo, puedes enviar un mensaje al jugador o ejecutar alguna funcionalidad personalizada
	    player.sendMessage("Has usado el item personalizado de nieve.");
    }
}
