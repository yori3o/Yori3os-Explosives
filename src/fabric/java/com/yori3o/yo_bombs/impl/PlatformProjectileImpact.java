package com.yori3o.yo_bombs.impl;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public final class PlatformProjectileImpact {
    public static boolean isAllowed(Projectile projectile, HitResult hit) {
        return true;
    }
}
