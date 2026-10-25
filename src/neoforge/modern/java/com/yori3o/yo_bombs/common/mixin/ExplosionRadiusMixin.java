package com.yori3o.yo_bombs.common.mixin;

import com.yori3o.yo_bombs.common.entity.GrenadeProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerExplosion.class)
public abstract class ExplosionRadiusMixin {
    @Shadow @Final
    private Entity source;

    // NeoForge moves entity damage into this overload to supply affected blocks.
    @ModifyConstant(method = "hurtEntities(Ljava/util/List;)V", constant = @Constant(floatValue = 2.0F))
    private float entityRadiusMultiplier(float multiplier) {
        return source instanceof GrenadeProjectile ? multiplier * 2.0F : multiplier;
    }
}
