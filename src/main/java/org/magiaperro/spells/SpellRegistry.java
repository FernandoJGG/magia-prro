package org.magiaperro.spells;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Fireball;

public class SpellRegistry {
    public static final Map<String, Spell> spells = new HashMap<>();

    public static void registerSpell(Spell spell) {
    	spells.put(spell.getId(), spell);
    }

    public static Spell getSpell(String id) {
        return spells.get(id);
    }

    @SuppressWarnings("unused")
	public static void register() {
        // Registra los hechizos aquí
    	Spell snowBall = new Spell(0, 0, "snowball", "Bola de nieve", "Le da su nieve al prro",
    								Material.SNOWBALL, new BaseProjectileSpell(Snowball.class));

    	Spell fireBall = new Spell(0, 0, "fireball", "Bola de fuego", "Le da su fuego al prro",
    								Material.FIRE_CHARGE, new BaseProjectileSpell(Fireball.class));
    }
}