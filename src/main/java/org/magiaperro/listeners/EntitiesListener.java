package org.magiaperro.listeners;

import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.magiaperro.entities.projectiles.base.CustomProjectile;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntitiesListener implements Listener {

	@EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        
		CustomProjectile customProjectile = CustomProjectile.fromProjectile(projectile);
        
        if (customProjectile != null) {
        	customProjectile.onHit(projectile, event);
        }
    }

}
