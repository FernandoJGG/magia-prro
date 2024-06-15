package org.magiaperro.spells.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;


public class BasePotionSpell extends SpellEffect {

	public List<PotionEffect> effects;
	
	/* Constructores: Permite lista o efecto unico */
	
	public BasePotionSpell(PotionEffect effect) {
		this.effects = new ArrayList<>();
		this.effects.add(effect);
	}
	
	public BasePotionSpell(PotionEffect[] effects) {
		this.effects = new ArrayList<>(Arrays.asList(effects));
	}
	
	/* Aplica efectos de pocción. El tiempo y el nivel marcado todo en el propio efecto. */
	
	@Override
	public void performSpell(Player caster) {

		Sound sound = Sound.BLOCK_LAVA_EXTINGUISH;
		float volume = 1.0f; // Volumen del sonido (0.0f a 1.0f)
		float pitch = 1.0f; // Tono del sonido (0.5f a 2.0f)
		
		caster.playSound(caster.getLocation(), sound, volume, pitch);
		
		for (PotionEffect effect:effects) {
			caster.addPotionEffect(effect);
		}
	}


}
