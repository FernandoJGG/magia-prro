package org.magiaperro.mobs;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.bukkit.EntityEffect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntitySnapshot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.block.TargetBlockInfo.FluidMode;
import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.TargetEntityInfo;

import io.papermc.paper.entity.TeleportFlag;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.TriState;

//import net.minecraft.world.entity.monster.EntitySkeleton;

public class TestMob implements Skeleton {

	@Override
	public @NotNull SkeletonType getSkeletonType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSkeletonType(SkeletonType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldBurnInDay() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setShouldBurnInDay(boolean shouldBurnInDay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull EntityEquipment getEquipment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Pathfinder getPathfinder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInDaylight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void lookAt(@NotNull Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookAt(@NotNull Location location, float headRotationSpeed, float maxHeadPitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookAt(@NotNull Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookAt(@NotNull Entity entity, float headRotationSpeed, float maxHeadPitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookAt(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookAt(double x, double y, double z, float headRotationSpeed, float maxHeadPitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHeadRotationSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxHeadPitch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTarget(@Nullable LivingEntity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable LivingEntity getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAware(boolean aware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAware() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @Nullable Sound getAmbientSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAggressive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAggressive(boolean aggressive) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLeftHanded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLeftHanded(boolean leftHanded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPossibleExperienceReward() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight(boolean ignorePose) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public @NotNull Location getEyeLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<Block> getLineOfSight(@Nullable Set<Material> transparent, int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Block getTargetBlock(@Nullable Set<Material> transparent, int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Block getTargetBlock(int maxDistance, @NotNull FluidMode fluidMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable BlockFace getTargetBlockFace(int maxDistance, @NotNull FluidMode fluidMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable BlockFace getTargetBlockFace(int maxDistance, @NotNull FluidCollisionMode fluidMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable TargetBlockInfo getTargetBlockInfo(int maxDistance, @NotNull FluidMode fluidMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Entity getTargetEntity(int maxDistance, boolean ignoreBlocks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable TargetEntityInfo getTargetEntityInfo(int maxDistance, boolean ignoreBlocks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable RayTraceResult rayTraceEntities(int maxDistance, boolean ignoreBlocks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<Block> getLastTwoTargetBlocks(@Nullable Set<Material> transparent, int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Block getTargetBlockExact(int maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Block getTargetBlockExact(int maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable RayTraceResult rayTraceBlocks(double maxDistance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable RayTraceResult rayTraceBlocks(double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemainingAir() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRemainingAir(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaximumAir() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaximumAir(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable ItemStack getItemInUse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemInUseTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setItemInUseTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getArrowCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setArrowCooldown(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getArrowsInBody() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setArrowsInBody(int count, boolean fireEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextArrowRemoval(@Range(from = 0, to = 2147483647) int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNextArrowRemoval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBeeStingerCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBeeStingerCooldown(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBeeStingersInBody() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBeeStingersInBody(int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextBeeStingerRemoval(@Range(from = 0, to = 2147483647) int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNextBeeStingerRemoval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaximumNoDamageTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getLastDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastDamage(double damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNoDamageTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNoActionTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNoActionTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable Player getKiller() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKiller(@Nullable Player killer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addPotionEffect(@NotNull PotionEffect effect) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffect(@NotNull PotionEffect effect, boolean force) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffects(@NotNull Collection<PotionEffect> effects) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPotionEffect(@NotNull PotionEffectType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @Nullable PotionEffect getPotionEffect(@NotNull PotionEffectType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePotionEffect(@NotNull PotionEffectType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull Collection<PotionEffect> getActivePotionEffects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean clearActivePotionEffects() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLineOfSight(@NotNull Entity other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLineOfSight(@NotNull Location location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getRemoveWhenFarAway() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRemoveWhenFarAway(boolean remove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCanPickupItems(boolean pickup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getCanPickupItems() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeashed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull Entity getLeashHolder() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setLeashHolder(@Nullable Entity holder) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGliding() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGliding(boolean gliding) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSwimming() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSwimming(boolean swimming) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRiptiding() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSleeping() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClimbing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAI(boolean ai) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasAI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void attack(@NotNull Entity target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swingMainHand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swingOffHand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playHurtAnimation(float yaw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCollidable(boolean collidable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCollidable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull Set<UUID> getCollidableExemptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> @Nullable T getMemory(@NotNull MemoryKey<T> memoryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable Sound getHurtSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Sound getDeathSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getFallDamageSound(int fallHeight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getFallDamageSoundSmall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getFallDamageSoundBig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getDrinkingSound(@NotNull ItemStack itemStack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getEatingSound(@NotNull ItemStack itemStack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canBreatheUnderwater() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull EntityCategory getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInvisible(boolean invisible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInvisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getArrowsStuck() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setArrowsStuck(int arrows) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getShieldBlockingDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setShieldBlockingDelay(int delay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getSidewaysMovement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getUpwardsMovement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getForwardsMovement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startUsingItem(@NotNull EquipmentSlot hand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void completeUsingActiveItem() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull ItemStack getActiveItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearActiveItem() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getActiveItemRemainingTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setActiveItemRemainingTime(@Range(from = 0, to = 2147483647) int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasActiveItem() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getActiveItemUsedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public @NotNull EquipmentSlot getActiveItemHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isJumping() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setJumping(boolean jumping) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playPickupItemAnimation(@NotNull Item item, int quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getHurtDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHurtDirection(float hurtDirection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void knockback(double strength, double directionX, double directionZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcastSlotBreak(@NotNull EquipmentSlot slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcastSlotBreak(@NotNull EquipmentSlot slot, @NotNull Collection<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull ItemStack damageItemStack(@NotNull ItemStack stack, int amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void damageItemStack(@NotNull EquipmentSlot slot, int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getBodyYaw() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBodyYaw(float bodyYaw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcastItemBreak(@NotNull EquipmentSlot slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable AttributeInstance getAttribute(@NotNull Attribute attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerAttribute(@NotNull Attribute attribute) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(double amount, @Nullable Entity source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(double amount, @NotNull DamageSource damageSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHealth(double health) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void heal(double amount, @NotNull RegainReason reason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getAbsorptionAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAbsorptionAmount(double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxHealth(double health) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetMaxHealth() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Location getLocation(@Nullable Location loc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVelocity(@NotNull Vector velocity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public @NotNull BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnGround() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInWater() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRotation(float yaw, float pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean teleport(@NotNull Location location, @NotNull TeleportCause cause,
			@NotNull TeleportFlag @NotNull... teleportFlags) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(@NotNull Location location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(@NotNull Location location, @NotNull TeleportCause cause) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(@NotNull Entity destination) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean teleport(@NotNull Entity destination, @NotNull TeleportCause cause) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull CompletableFuture<Boolean> teleportAsync(@NotNull Location loc, @NotNull TeleportCause cause,
			@NotNull TeleportFlag @NotNull... teleportFlags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull List<Entity> getNearbyEntities(double x, double y, double z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEntityId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFireTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisualFire(boolean fire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisualFire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getFreezeTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxFreezeTicks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFreezeTicks(int ticks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFrozen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNoPhysics(boolean noPhysics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasNoPhysics() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFreezeTickingLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void lockFreezeTicks(boolean locked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPersistent(boolean persistent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable Entity getPassenger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setPassenger(@NotNull Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull List<Entity> getPassengers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPassenger(@NotNull Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removePassenger(@NotNull Entity passenger) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eject() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getFallDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFallDistance(float distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastDamageCause(@Nullable EntityDamageEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable EntityDamageEvent getLastDamageCause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull UUID getUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTicksLived() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTicksLived(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playEffect(@NotNull EntityEffect type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull EntityType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getSwimSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getSwimSplashSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Sound getSwimHighSpeedSplashSound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInsideVehicle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveVehicle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @Nullable Entity getVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCustomNameVisible(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCustomNameVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setVisibleByDefault(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isVisibleByDefault() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull Set<Player> getTrackedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGlowing(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isGlowing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInvulnerable(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInvulnerable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSilent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSilent(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasGravity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGravity(boolean gravity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPortalCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPortalCooldown(int cooldown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull Set<String> getScoreboardTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addScoreboardTag(@NotNull String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeScoreboardTag(@NotNull String tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull PistonMoveReaction getPistonMoveReaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull BlockFace getFacing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Pose getPose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSneaking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSneaking(boolean sneak) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPose(@NotNull Pose pose, boolean fixed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasFixedPose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull SpawnCategory getSpawnCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInWorld() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @Nullable String getAsString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable EntitySnapshot createSnapshot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Entity copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Entity copy(@NotNull Location to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Spigot spigot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Component teamDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Location getOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean fromMobSpawner() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull SpawnReason getEntitySpawnReason() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUnderWater() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInRain() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInBubbleColumn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInWaterOrRain() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInWaterOrBubbleColumn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInWaterOrRainOrBubbleColumn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInLava() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTicking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull Set<Player> getTrackedPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean spawnAt(@NotNull Location location, @NotNull SpawnReason reason) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInPowderedSnow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPitch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getYaw() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean collidesAt(@NotNull Location location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wouldCollideUsing(@NotNull BoundingBox boundingBox) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull EntityScheduler getScheduler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull String getScoreboardEntryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Player getRider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRider() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRidable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRidableInWater() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isImmuneToFire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setImmuneToFire(@Nullable Boolean fireImmune) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasMetadata(@NotNull String metadataKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(@NotNull String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(@NotNull String... messages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(@Nullable UUID sender, @NotNull String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(@Nullable UUID sender, @NotNull String... messages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull Component name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPermissionSet(@NotNull String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(@NotNull Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(@NotNull String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(@NotNull Permission perm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value,
			int ticks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAttachment(@NotNull PermissionAttachment attachment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recalculatePermissions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOp(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable Component customName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void customName(@Nullable Component customName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable String getCustomName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCustomName(@Nullable String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile,
			@Nullable Vector velocity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile,
			@Nullable Vector velocity, @Nullable Consumer<? super T> function) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @NotNull TriState getFrictionState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFrictionState(@NotNull TriState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLootTable(@Nullable LootTable table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public @Nullable LootTable getLootTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSeed(long seed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getSeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void rangedAttack(@NotNull LivingEntity target, float charge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setChargingAttack(boolean raiseHands) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConverting() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getConversionTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setConversionTime(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int inPowderedSnowTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
