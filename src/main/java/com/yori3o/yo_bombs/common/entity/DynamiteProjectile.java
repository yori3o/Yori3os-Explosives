package com.yori3o.yo_bombs.common.entity;


import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;



public class DynamiteProjectile extends ExplosiveProjectile {


    public DynamiteProjectile(final EntityType<? extends DynamiteProjectile> type, final Level level) {
        super(type, level);
    }

    public DynamiteProjectile(final EntityType<? extends DynamiteProjectile> type, final Level level, double x, double y, double z, final ItemStack itemStack) {
        super(type, level, x, y, z, itemStack);
    }

    public DynamiteProjectile(final EntityType<? extends DynamiteProjectile> type, final Level level, final LivingEntity owner, final ItemStack itemStack) {
        super(type, level, owner, itemStack);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.DYNAMITE.get();
    }

    public boolean isSticky() {
        return false;
    }

    public boolean detonationByEntity() {
        return false;
    }

    public int ticksToDetonation() {
        return ServerConfig.HANDLER.instance().dynamiteDetonationTime * 20;
    }
    
    public float explosionPower() {
        return ServerConfig.HANDLER.instance().dynamiteRadius;
    }

    @Override
    protected double getBounceFactor() {
        return 0.10;
    }

    @Override
    protected double getSurfaceFriction() {
        return 0.82;
    }
    
}
