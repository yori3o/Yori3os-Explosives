package com.yori3o.yo_bombs.common.mixin;

import com.yori3o.yo_bombs.common.entity.ExplosiveProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrowableProjectile.class)
public abstract class ExplosiveMovementMixin extends Projectile {
    protected ExplosiveMovementMixin(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void yoBombs$tickMovement(CallbackInfo ci) {
        if ((Object) this instanceof ExplosiveProjectile explosive) {
            // Retain Projectile/Entity ticking, but replace ThrowableProjectile's
            // point raycast and unchecked setPos with swept volume collisions.
            super.tick();
            explosive.tickMovement();
            ci.cancel();
        }
    }
}
