package com.yori3o.yo_bombs.common.entity;


import com.yori3o.yo_bombs.common.config.ServerConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;



public abstract class ExplosiveProjectile extends ThrowableItemProjectile {

    private int ticksToDetonationLeft = -1;

    private BlockPos attachedBlockPos; // only for sticky


    public ExplosiveProjectile(final EntityType<? extends ExplosiveProjectile> type, final Level level) {
        super(type, level);
    }

    public ExplosiveProjectile(final EntityType<? extends ExplosiveProjectile> type, final Level level, double x, double y, double z, final ItemStack itemStack) {
        this(type, level);
        this.setPos(x, y, z);
        this.setItem(itemStack);
    }

    public ExplosiveProjectile(final EntityType<? extends ExplosiveProjectile> type, final Level level, final LivingEntity owner, final ItemStack itemStack) {
        this(type, level);
        this.setPos(owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ());
        this.setItem(itemStack);
        this.setOwner(owner);
        ticksToDetonationLeft = ticksToDetonation();
    }

    protected abstract Item getDefaultItem();

    public abstract boolean isSticky();

    public abstract boolean detonationByEntity();
    
    public abstract int ticksToDetonation();
    
    public abstract float explosionPower();

    
    @Override
    public void tick() {
        super.tick();
        if (this.isRemoved()) return;

        if (!level().isClientSide()) {
            if (ticksToDetonationLeft == 0) {
                detonate();
                ticksToDetonationLeft = -1;
            } else if (ticksToDetonationLeft > 0) {
                ticksToDetonationLeft--;
            }

            if (attachedBlockPos != null) {
                if (this.isNoGravity()) {
                    if (this.level().getBlockState(attachedBlockPos).isAir()) {
                        setNoGravity(false);
                    } else if (getDeltaMovement().lengthSqr() > 0.05d) {
                        setNoGravity(false);
                    }
                }
            }
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
    
    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return !isNoGravity();
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        if (detonationByEntity()) {
            if (entity instanceof LivingEntity && entity != getOwner()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onHitBlock(final BlockHitResult hitResult) {
        super.onHitBlock(hitResult);

        if (!isSticky()) {
            Vec3 velocity = getDeltaMovement();

            double bounce = getBounceFactor();      // 0.15..0.4
            double friction = getSurfaceFriction(); // 0.8..0.98

            if (Math.abs(velocity.y) < 0.10 && hitResult.getDirection() == Direction.UP) {
                velocity = new Vec3(velocity.x * 0.8, 0.0, velocity.z * 0.8);
            } else {
                switch (hitResult.getDirection().getAxis()) {
                    case X -> velocity = new Vec3(
                            -velocity.x * bounce,
                            velocity.y * friction,
                            velocity.z * friction
                    );
                    case Y -> velocity = new Vec3(
                            velocity.x * friction,
                            -velocity.y * bounce,
                            velocity.z * friction
                    );
                    case Z -> velocity = new Vec3(
                            velocity.x * friction,
                            velocity.y * friction,
                            -velocity.z * bounce
                    );
                }
            }
            setDeltaMovement(velocity);
        } else {
            setDeltaMovement(new Vec3(0, 0, 0));
            setNoGravity(true);
            attachedBlockPos = hitResult.getBlockPos();
        }   
    }

    protected double getBounceFactor() {
        return 0.20;
    }

    protected double getSurfaceFriction() {
        return 0.90;
    }

    @Override
    protected void onHitEntity(final EntityHitResult hitResult) {
        detonate();
    }

    private void detonate() {
        level().explode(
            (Entity)this,
            Explosion.getDefaultDamageSource(
                level(), 
                (Entity)this
            ), 
            new CustomExplosionDamageCalculator(), 
            getX(), getY() + 0.1, getZ(), 
            explosionPower(), false, Level.ExplosionInteraction.TNT
        );
        discard();
    }

    public class CustomExplosionDamageCalculator extends ExplosionDamageCalculator {

        @Override
        public boolean shouldBlockExplode(final Explosion explosion, final BlockGetter level, final BlockPos pos, final BlockState state, final float power) {
            if (explosion.getDirectSourceEntity() instanceof GrenadeProjectile && !ServerConfig.HANDLER.instance().grenadeBreakBlocks) return false;
            if (explosion.getDirectSourceEntity() instanceof BombProjectile && !ServerConfig.HANDLER.instance().bombBreakBlocks) return false;
            if (explosion.getDirectSourceEntity() instanceof DynamiteProjectile && !ServerConfig.HANDLER.instance().dynamiteBreakBlocks) return false;
            return true;
        }

        @Override
        public float getKnockbackMultiplier(final Entity entity) {
            return ServerConfig.HANDLER.instance().defaultKnockbackMultiplier;
        }
        
        @Override
        public float getEntityDamageAmount(final Explosion explosion, final Entity entity, float exposure) {
            float doubleRadius = explosion.radius() * 2.0F;
            if (explosion.getDirectSourceEntity() instanceof GrenadeProjectile) {
                doubleRadius *= 2f;
            }
            Vec3 center = explosion.center();
            double dist = Math.sqrt(entity.distanceToSqr(center)) / (double)doubleRadius;
            double pow = (1.0D - dist) * (double)exposure;
            return (float)(((pow * pow + pow) / 2.0D * 7.0D * (double)doubleRadius + 1.0D) * getDamageMultiplier(explosion.getDirectSourceEntity()));
        }

        public float getDamageMultiplier(Entity source) {
            if (source instanceof GrenadeProjectile) return ServerConfig.HANDLER.instance().grenadeDamageMultiplier;
            if (source instanceof BombProjectile) return ServerConfig.HANDLER.instance().bombDamageMultiplier;
            if (source instanceof DynamiteProjectile) return ServerConfig.HANDLER.instance().dynamiteDamageMultiplier;
            return 0.6f;
        }
    }
    
}