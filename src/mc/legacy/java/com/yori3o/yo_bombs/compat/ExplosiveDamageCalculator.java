package com.yori3o.yo_bombs.compat;

import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.common.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public final class ExplosiveDamageCalculator extends ExplosionDamageCalculator {

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
        public float getEntityDamageAmount(final Explosion explosion, final Entity entity) {
            float doubleRadius = explosion.radius() * 2.0F;
            if (explosion.getDirectSourceEntity() instanceof GrenadeProjectile) doubleRadius *= 2f;
            Vec3 center = explosion.center();
            if (doubleRadius <= 0) return 0;
            double dist = Math.sqrt(entity.distanceToSqr(center)) / (double)doubleRadius;
            double pow = Math.max(0.0D, 1.0D - dist) * (double)Explosion.getSeenPercent(center, entity);
            return (float)(((pow * pow + pow) / 2.0D * 7.0D * (double)doubleRadius + 1.0D) * getDamageMultiplier(explosion.getDirectSourceEntity()));

        }

        public float getDamageMultiplier(Entity source) {
            if (source instanceof GrenadeProjectile) return ServerConfig.HANDLER.instance().grenadeDamageMultiplier;
            if (source instanceof BombProjectile) return ServerConfig.HANDLER.instance().bombDamageMultiplier;
            if (source instanceof DynamiteProjectile) return ServerConfig.HANDLER.instance().dynamiteDamageMultiplier;
            return 0.6f;
        }
    }
