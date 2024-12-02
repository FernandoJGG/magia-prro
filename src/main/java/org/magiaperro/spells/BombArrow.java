package org.magiaperro.spells;

import org.bukkit.entity.Player;
import org.magiaperro.entities.projectiles.ProjectileRegistry;
import org.magiaperro.entities.projectiles.ExplosiveArrow;
import org.magiaperro.entities.projectiles.base.ProjectileID;
import org.magiaperro.spells.base.SpellEffect;

public class BombArrow extends SpellEffect {

	public double speed = 1.5;
	
	public BombArrow() {
	}
	
	public BombArrow(double speed) {
		this.speed = speed;
	}
	
	@Override
	public void performSpell(Player caster) {
		ExplosiveArrow explosiveArrow = (ExplosiveArrow) ProjectileRegistry
				.getCustomProjectile(ProjectileID.EXPLOSIVE_ARROW);
		
		explosiveArrow.launch(caster, 3.0);
	}

}
