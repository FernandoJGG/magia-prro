package org.magiaperro.spells;

import java.util.Random;

import org.bukkit.entity.Player;

public abstract class SpellEffect {
	protected static Random spellRand = new Random();
	
	public abstract void performSpell(Player caster);
}
