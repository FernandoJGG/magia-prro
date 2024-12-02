package org.magiaperro.entities.projectiles;

import java.util.HashMap;
import java.util.Map;

import org.magiaperro.entities.projectiles.base.CustomProjectile;
import org.magiaperro.entities.projectiles.base.ProjectileID;

@SuppressWarnings("rawtypes")
public class ProjectileRegistry {
	public static final Map<ProjectileID, CustomProjectile> customProjectiles = new HashMap<>();

    @SuppressWarnings("unused")
	public static void register() {
    	ExplosiveArrow explosiveArrow = new ExplosiveArrow(ProjectileID.EXPLOSIVE_ARROW);
    }
    
    public static void registerCustomProjectile(CustomProjectile customProjectile) {
        customProjectiles.put(customProjectile.getId(), customProjectile);
    }
    
	public static CustomProjectile getCustomProjectile(ProjectileID projectileId) {
    	if (customProjectiles.containsKey(projectileId))
            return customProjectiles.get(projectileId);
    	else 
    		return null;
    }
    
    public static CustomProjectile getCustomProjectile(int id) {
    	ProjectileID projectileId = ProjectileID.getByIndex(id);
        return getCustomProjectile(projectileId);
    }

}
