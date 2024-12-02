package org.magiaperro.entities.projectiles.base;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.magiaperro.entities.projectiles.ProjectileRegistry;
import org.magiaperro.main.Keys;

@SuppressWarnings("rawtypes")
public abstract class CustomProjectile<T extends Projectile> {
    private final ProjectileID id;
    private final Class<T> projectileClass;

    public CustomProjectile(Class<T> projectileClass, ProjectileID id) {
        this.id = id;
        this.projectileClass = projectileClass;
        
        ProjectileRegistry.registerCustomProjectile(this);
        // Registrar el listener
//        Bukkit.getPluginManager().registerEvents(new ProjectileListener(), plugin);
    }

    // Método para lanzar el proyectil personalizado
 	public T launch(LivingEntity launcher, double speed) {
        Location eyeLocation = launcher.getEyeLocation();
        Vector direction = eyeLocation.getDirection();
        
        return this.launch(launcher, eyeLocation, direction, speed);
     }

	public T launch(LivingEntity launcher, Location location, Vector direction, double speed) {
		T projectile = launcher.launchProjectile(projectileClass, direction.normalize().multiply(speed));

        // Agregar el ID al PDC del proyectil
        PersistentDataContainer pdc = projectile.getPersistentDataContainer();
        pdc.set(Keys.CUSTOM_PROJECTILE_ID, PersistentDataType.INTEGER, id.getIndex());

        return projectile;
    }
	
	public ProjectileID getId() {
		return this.id;
	}

    // Método abstracto que define la lógica de impacto
    public abstract void onHit(T projectile, ProjectileHitEvent event);

	public static CustomProjectile fromProjectile(Projectile projectile) {
        PersistentDataContainer pdc = projectile.getPersistentDataContainer();

        // Verificar si el proyectil tiene la clave en el PDC
        if (pdc.has(Keys.CUSTOM_PROJECTILE_ID, PersistentDataType.INTEGER)) {
            int id = pdc.get(Keys.CUSTOM_PROJECTILE_ID, PersistentDataType.INTEGER);
            return fromId(id);
        }
        return null;
	}
	
	public static CustomProjectile fromId(int id) {
		return ProjectileRegistry.getCustomProjectile(id);
	}
}
