package org.magiaperro.spells;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class BaseProjectileSpell extends SpellEffect {

	public Class<? extends Projectile> projectileType;
	
	public BaseProjectileSpell(Class<? extends Projectile> projectileType) {
		this.projectileType = projectileType;
	}
	
	@Override
	public void performSpell(Player caster) {
		Projectile proyectile = caster.launchProjectile(projectileType);
	    
		proyectile.setVelocity(caster.getLocation().getDirection().multiply(1.5));
		caster.sendMessage("Has usado la magia lanzable");
	}

}
