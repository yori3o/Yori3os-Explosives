package com.yori3o.yo_bombs.impl;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;

public final class PlatformProjectileImpact {
    public static boolean isAllowed(Projectile projectile, HitResult hit) {
        return !EventHooks.onProjectileImpact(projectile, hit);
    }
}
