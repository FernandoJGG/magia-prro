package org.magiaperro.spells;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;

import org.bukkit.entity.Fireball;

public class SpellRegistry {
	public static final Map<SpellID, Spell> spells = new HashMap<>();

	public static void registerSpell(Spell spell) {
		spells.put(spell.getId(), spell);
	}

	public static Spell getSpell(SpellID id) {
		return spells.get(id);
	}

	public static Spell getSpell(int id) {
		return spells.get(SpellID.getByIndex(id));
	}

	@SuppressWarnings("unused")
	public static void register() {
		// Registra los hechizos aquí
		Spell snowBall = new Spell(0, 0, SpellID.Snowball, "Bola de nieve", "Le da su nieve al prro", Material.SNOWBALL,
				new BaseProjectileSpell(Snowball.class));

		Spell fireBall = new Spell(0, 0, SpellID.Fireball, "Bola de fuego", "Le da su fuego al prro",
				Material.FIRE_CHARGE, new BaseProjectileSpell(Fireball.class));

		Spell goldenApple = new Spell(0, 0, SpellID.GoldenApple, "Manzana dorada", "aa", Material.GOLDEN_APPLE,
				new BasePotionSpell(
						new PotionEffect[] { new PotionEffect(PotionEffectType.ABSORPTION, 400, 2, false, false),
								new PotionEffect(PotionEffectType.REGENERATION, 400, 2, false, false) }));
	}
}