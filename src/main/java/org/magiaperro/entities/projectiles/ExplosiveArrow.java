package org.magiaperro.entities.projectiles;

import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;
import org.magiaperro.entities.projectiles.base.CustomProjectile;
import org.magiaperro.entities.projectiles.base.ProjectileID;

public class ExplosiveArrow extends CustomProjectile<Arrow>{

	public ExplosiveArrow(ProjectileID id) {
		super(Arrow.class, id);
		// TODO Auto-generated constructor stub
	}
	
	public Arrow launch(LivingEntity launcher, Location location, Vector direction, double speed) {
		Arrow arrow = super.launch(launcher, location, direction, speed);
		
		arrow.setPickupStatus(PickupStatus.DISALLOWED);
		
		return arrow;
    }

	@Override
	public void onHit(Arrow projectile, ProjectileHitEvent event) {
		// TODO Auto-generated method stub
        Entity hitEntity = event.getHitEntity();
        
        // Verificar si la entidad es un jugador o un mob
        if (hitEntity != null && hitEntity instanceof LivingEntity) {
    		projectile.getWorld().createExplosion(projectile.getLocation(), 4.0f, true, true);
            
            // Eliminar la flecha después de la explosión
    		projectile.remove();
        }
		
	}

}
