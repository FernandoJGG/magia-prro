package org.magiaperro.spells.base;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class BaseProjectileSpell extends SpellEffect {

	public Class<? extends Projectile> projectileType;
	public double speed = 1.5;
	
	public BaseProjectileSpell(Class<? extends Projectile> projectileType) {
		this.projectileType = projectileType;
	}
	
	public BaseProjectileSpell(Class<? extends Projectile> projectileType, double speed) {
		this.projectileType = projectileType;
		this.speed = speed;
	}
	
	@Override
	public void performSpell(Player caster) {
		Projectile proyectile = caster.launchProjectile(projectileType);
	    
		proyectile.setVelocity(caster.getLocation().getDirection().multiply(this.speed));
	}

}
