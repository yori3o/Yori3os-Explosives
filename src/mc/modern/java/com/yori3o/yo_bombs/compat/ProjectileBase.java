package com.yori3o.yo_bombs.compat;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class ProjectileBase extends ThrowableItemProjectile {
    protected ProjectileBase(EntityType<? extends ProjectileBase> type, Level level) {
        super(type, level);
    }

    protected void updateExplosiveBlockEffects(Vec3 from) {
        applyEffectsFromBlocks(from, position());
    }
}
