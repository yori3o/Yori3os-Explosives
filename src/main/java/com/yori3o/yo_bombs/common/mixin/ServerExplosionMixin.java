package com.yori3o.yo_bombs.common.mixin;


import com.yori3o.yo_bombs.common.entity.GrenadeProjectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerExplosion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;



@Mixin(ServerExplosion.class)
public abstract class ServerExplosionMixin {

    //@Shadow
    //private float radius;

    @Shadow
    private Entity source;

    @ModifyVariable(
        method = "hurtEntities",
        at = @At("STORE"),
        ordinal = 0
    )
    private float modifyDoubleRadius(float doubleRadius) {
        if (source instanceof GrenadeProjectile) {
            return doubleRadius * 2.0F;
        }

        return doubleRadius;
    }
}