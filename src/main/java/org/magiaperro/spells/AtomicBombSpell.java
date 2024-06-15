package org.magiaperro.spells;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.magiaperro.spells.base.SpellEffect;

public class AtomicBombSpell extends SpellEffect {

	public double speed = 1.5;
	
	public AtomicBombSpell() {
	}
	
	public AtomicBombSpell(double speed) {
		this.speed = speed;
	}
	
	@Override
	public void performSpell(Player caster) {
		Fireball fireball = caster.launchProjectile(Fireball.class);
		fireball.setYield(60);

        Vector direction = caster.getLocation().getDirection().multiply(this.speed);
        direction.setY(direction.getY() + 0.5);
        fireball.setVelocity(direction);
	}

}
