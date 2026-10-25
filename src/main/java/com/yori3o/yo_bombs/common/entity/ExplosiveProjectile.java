package com.yori3o.yo_bombs.common.entity;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import com.yori3o.yo_bombs.compat.ProjectileBase;
import com.yori3o.yo_bombs.compat.ExplosiveDamageCalculator;
import com.yori3o.yo_bombs.impl.PlatformProjectileImpact;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;



public abstract class ExplosiveProjectile extends ProjectileBase {

    private int ticksToDetonationLeft = -1;

    private BlockPos attachedBlockPos; // only for sticky


    public ExplosiveProjectile(final EntityType<? extends ExplosiveProjectile> type, final Level level) {
        super(type, level);
        ticksToDetonationLeft = ticksToDetonation();
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
            if (ticksToDetonationLeft <= 0 || --ticksToDetonationLeft == 0) {
                detonate();
                return;
            }

        }
    }

    private static final double CONTACT_EPSILON = 1.0E-5;

    /** Called by ExplosiveMovementMixin instead of vanilla's point-projectile movement. */
    public final void tickMovement() {
        if (isRemoved()) return;
        if (attachedBlockPos != null) {
            if (!hasSupportingShape() || getDeltaMovement().lengthSqr() > 0.05) {
                attachedBlockPos = null;
                setNoGravity(false);
            } else {
                setDeltaMovement(Vec3.ZERO);
                updateExplosiveBlockEffects(position());
                return;
            }
        }

        applyGravity();
        Vec3 incoming = getDeltaMovement().scale(isInWater() ? 0.8 : 0.99);
        setDeltaMovement(incoming);
        Vec3 start = position();
        AABB startBox = getBoundingBox();
        // Minecraft's swept AABB/voxel-shape resolver checks the entire path,
        // including multiple faces at a corner and partial blocks such as slabs.
        Vec3 movement = Entity.collideBoundingBox(this, incoming, startBox, level(),
                List.of(level().getWorldBorder().getCollisionShape()));
        Vec3 end = start.add(movement);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(level(), this, start, end,
                startBox.expandTowards(movement).inflate(1.0), this::canHitEntity);
        if (entityHit != null && level().clip(new ClipContext(start, entityHit.getLocation(),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS
                && PlatformProjectileImpact.isAllowed(this, entityHit)) {
            setPos(entityHit.getLocation());
            setDeltaMovement(incoming);
            updateExplosiveBlockEffects(start);
            hitTargetOrDeflectSelf(entityHit);
            return;
        }

        setPos(end);
        boolean hitX = Math.abs(incoming.x - movement.x) > 1.0E-7;
        boolean hitY = Math.abs(incoming.y - movement.y) > 1.0E-7;
        boolean hitZ = Math.abs(incoming.z - movement.z) > 1.0E-7;
        horizontalCollision = hitX || hitZ;
        verticalCollision = hitY;
        verticalCollisionBelow = hitY && incoming.y < 0;
        setOnGround(verticalCollisionBelow);

        if (hitX || hitY || hitZ) {
            Direction face = hitY ? (incoming.y < 0 ? Direction.UP : Direction.DOWN)
                    : hitX ? (incoming.x < 0 ? Direction.EAST : Direction.WEST)
                    : (incoming.z < 0 ? Direction.SOUTH : Direction.NORTH);
            BlockHitResult blockHit = findBlockContact(face);
            if (blockHit != null && !PlatformProjectileImpact.isAllowed(this, blockHit)) {
                // Preserve NeoForge's explicit impact cancellation by other mods.
                setPos(start.add(incoming));
                setDeltaMovement(incoming);
                horizontalCollision = verticalCollision = verticalCollisionBelow = false;
                setOnGround(false);
                updateRotation();
                updateExplosiveBlockEffects(start);
                return;
            }
            if (blockHit != null) hitTargetOrDeflectSelf(blockHit);
            if (isRemoved()) return;
            if (isSticky() && blockHit != null) {
                attachedBlockPos = blockHit.getBlockPos().immutable();
                setNoGravity(true);
                setDeltaMovement(Vec3.ZERO);
            } else {
                double bounce = getBounceFactor();
                boolean restingOnFloor = verticalCollisionBelow && Math.abs(incoming.y) < 0.10;
                double friction = restingOnFloor ? 0.8 : getSurfaceFriction();
                setDeltaMovement(new Vec3(
                        hitX ? -incoming.x * bounce : incoming.x * friction,
                        hitY ? (restingOnFloor ? 0.0 : -incoming.y * bounce) : incoming.y * friction,
                        hitZ ? -incoming.z * bounce : incoming.z * friction));
            }
        } else {
            setDeltaMovement(incoming);
        }
        updateRotation();
        updateExplosiveBlockEffects(start);
    }

    private boolean hasSupportingShape() {
        var shape = level().getBlockState(attachedBlockPos)
                .getCollisionShape(level(), attachedBlockPos, CollisionContext.of(this))
                .move(attachedBlockPos.getX(), attachedBlockPos.getY(), attachedBlockPos.getZ());
        AABB contactBox = getBoundingBox().inflate(CONTACT_EPSILON);
        return shape.toAabbs().stream().anyMatch(contactBox::intersects);
    }

    private BlockHitResult findBlockContact(Direction face) {
        AABB box = getBoundingBox();
        AABB contactBox = box.inflate(CONTACT_EPSILON);
        for (BlockPos pos : BlockPos.betweenClosed(
                BlockPos.containing(contactBox.minX, contactBox.minY, contactBox.minZ),
                BlockPos.containing(contactBox.maxX, contactBox.maxY, contactBox.maxZ))) {
            var shape = level().getBlockState(pos).getCollisionShape(level(), pos, CollisionContext.of(this))
                    .move(pos.getX(), pos.getY(), pos.getZ());
            for (AABB part : shape.toAabbs()) {
                if (!part.intersects(contactBox)) continue;
                double distance = switch (face) {
                    case WEST -> Math.abs(box.maxX - part.minX);
                    case EAST -> Math.abs(box.minX - part.maxX);
                    case DOWN -> Math.abs(box.maxY - part.minY);
                    case UP -> Math.abs(box.minY - part.maxY);
                    case NORTH -> Math.abs(box.maxZ - part.minZ);
                    case SOUTH -> Math.abs(box.minZ - part.maxZ);
                };
                if (distance > CONTACT_EPSILON * 2) continue;
                Vec3 center = box.getCenter();
                Vec3 contact = new Vec3(
                        Math.max(part.minX, Math.min(part.maxX, center.x)),
                        Math.max(part.minY, Math.min(part.maxY, center.y)),
                        Math.max(part.minZ, Math.min(part.maxZ, center.z)));
                return new BlockHitResult(contact, face, pos.immutable(), false);
            }
        }
        return null;
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
        return detonationByEntity() && entity instanceof LivingEntity
                && entity != getOwner() && super.canHitEntity(entity);
    }

    @Override
    protected void onHitBlock(final BlockHitResult hitResult) {
        super.onHitBlock(hitResult);

    }

    protected double getBounceFactor() {
        return 0.20;
    }

    protected double getSurfaceFriction() {
        return 0.90;
    }

    @Override
    protected void onHitEntity(final EntityHitResult hitResult) {
        if (!level().isClientSide()) detonate();
    }

    private void detonate() {
        if (isRemoved() || level().isClientSide()) return;
        level().explode(
            (Entity)this,
            Explosion.getDefaultDamageSource(
                level(), 
                (Entity)this
            ), 
            new ExplosiveDamageCalculator(), 
            getX(), getY() + 0.1, getZ(), 
            explosionPower(), false, Level.ExplosionInteraction.TNT
        );
        discard();
    }

}
