package com.yori3o.yo_bombs.common.mixin;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.yori3o.yo_bombs.common.entity.GrenadeProjectile;




@Mixin(Explosion.class)
public abstract class ExplosionRadiusMixin {


    @Shadow @Final
    private Entity source;

    // The first three 2.0F constants normalize block rays; the fourth is the entity radius.
    @ModifyConstant(method = "explode", constant = @Constant(floatValue = 2.0F, ordinal = 3))
    private float entityRadiusMultiplier(float q) {
        if (source instanceof GrenadeProjectile) {
            return q * 2.0F;
        }

        return q;
    }
}
