package com.yori3o.yo_bombs.common.entity;


import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;



public class GrenadeProjectile extends ExplosiveProjectile {


    public GrenadeProjectile(final EntityType<? extends GrenadeProjectile> type, final Level level) {
        super(type, level);
    }

    public GrenadeProjectile(final EntityType<? extends GrenadeProjectile> type, final Level level, double x, double y, double z, final ItemStack itemStack) {
        super(type, level, x, y, z, itemStack);
    }

    public GrenadeProjectile(final EntityType<? extends GrenadeProjectile> type, final Level level, final LivingEntity owner, final ItemStack itemStack) {
        super(type, level, owner, itemStack);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.GRENADE.get();
    }

    public boolean isSticky() {
        return false;
    }

    public boolean detonationByEntity() {
        return true;
    }

    public int ticksToDetonation() {
        return ServerConfig.HANDLER.instance().grenadeDetonationTime * 20;
    }
    
    public float explosionPower() {
        return ServerConfig.HANDLER.instance().grenadeRadius;
    }

    @Override
    protected double getBounceFactor() {
        return 0.3;
    }

    @Override
    protected double getSurfaceFriction() {
        return 0.93;
    }
    
}
