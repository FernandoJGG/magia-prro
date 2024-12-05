package org.magiaperro.listeners;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.magiaperro.entities.projectiles.base.CustomProjectile;
import org.magiaperro.machines.base.Machine;
import org.magiaperro.machines.base.MachineEntity;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntitiesListener implements Listener {

	@EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        
		CustomProjectile customProjectile = CustomProjectile.fromProjectile(projectile);
        
        if (customProjectile != null) {
        	customProjectile.onHit(projectile, event);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Obtener la entidad que murió
        Entity entity = event.getEntity();
        
        Machine machine = Machine.fromPDC(entity.getPersistentDataContainer());
        
        if(machine != null) {
        	machine.onDisappear(new MachineEntity(entity));
        	event.getDrops().clear();
        	event.setDroppedExp(0);
        }
    }

}
