package org.magiaperro.spells;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Spell {
    private int manaCost;
    private int cooldown;
    private SpellID id;
    private String name;
    private String description;
    private Material material;
    private SpellEffect spellEffect;

    public Spell(int manaCost, int cooldown, SpellID id, String name, String description, Material material, SpellEffect spellEffect) {
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.id = id;
        this.name = name;
        this.description = description;
        this.material = material;
        this.spellEffect = spellEffect;

        // Registrar el hechizo en el registry al construirlo
        SpellRegistry.registerSpell(this);
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getCooldown() {
        return cooldown;
    }

    public SpellID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public void castSpell(Player player) {
    	this.spellEffect.performSpell(player);
    };
    
}
