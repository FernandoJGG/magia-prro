package org.magiaperro.items.base;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class BasePotion extends CustomItem implements IConsumable {

	public final List<PotionEffect> effects;
	public final Color color;
	
	public BasePotion(ItemID id, String itemName, Color color, List<String> lore, PotionEffect[] effects) {
		super(id, itemName, Material.POTION, lore);
		
		this.color = color;
		this.effects = Arrays.asList(effects);
	}

	@Override
	public ItemStack buildItemStack(int amount) {
		// TODO Auto-generated method stub
		ItemStack potion = super.buildItemStack(amount);
		PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
		
        for(PotionEffect effect: effects) {
            potionMeta.addCustomEffect(effect, true);
        }
        potionMeta.setColor(color);
        potionMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        
        potion.setItemMeta(potionMeta);
        
		return potion;
	}
	

	@Override
	public void onConsume(PlayerItemConsumeEvent event) {
//		event.getPlayer().sendTitle(
//	            "Borrachera",          // Título principal
//	            "solo queria probar el evento",       // Subtítulo
//	            10,             // Tiempo de fade-in (ticks)
//	            70,             // Duración del título (ticks)
//	            20              // Tiempo de fade-out (ticks)
//	        );

		Player player = event.getPlayer();
		int foodLevel = 5;
		int saturation = 6;
		
		//Esto pegaria mejor en un ICustomFood pero whatever
        int newFoodLevel = Math.min(20, player.getFoodLevel() + foodLevel);
        player.setFoodLevel(newFoodLevel);

        // Añadir saturación (nivel máximo es igual al nivel actual de comida)
        float newSaturation = Math.min(newFoodLevel, player.getSaturation() + saturation);
        player.setSaturation(newSaturation);
	}

}
