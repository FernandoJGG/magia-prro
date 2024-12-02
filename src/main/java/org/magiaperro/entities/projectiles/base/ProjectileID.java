package org.magiaperro.entities.projectiles.base;

public enum ProjectileID {
 EXPLOSIVE_ARROW(0);
	
 private final int index;

 private ProjectileID(int index) {
     this.index = index;
 }

 public int getIndex() {
     return index;
 }
 
 public static ProjectileID getByIndex(int index) {
     for (ProjectileID element : ProjectileID.values()) {
         if (element.getIndex() == index) {
             return element;
         }
     }
     return null;
 }
}