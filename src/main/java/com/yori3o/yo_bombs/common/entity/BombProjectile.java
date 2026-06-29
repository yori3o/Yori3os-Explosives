package com.yori3o.yo_bombs.common.entity;


import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;



public class BombProjectile extends ExplosiveProjectile {


    public BombProjectile(final EntityType<? extends BombProjectile> type, final Level level) {
        super(type, level);
    }

    public BombProjectile(final EntityType<? extends BombProjectile> type, final Level level, double x, double y, double z, final ItemStack itemStack) {
        super(type, level, x, y, z, itemStack);
    }

    public BombProjectile(final EntityType<? extends BombProjectile> type, final Level level, final LivingEntity owner, final ItemStack itemStack) {
        super(type, level, owner, itemStack);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.STICKY_BOMB.get();
    }

    public boolean isSticky() {
        return false;
    }

    public boolean detonationByEntity() {
        return false;
    }

    public int ticksToDetonation() {
        return ServerConfig.HANDLER.instance().bombDetonationTime * 20;
    }
    
    public float explosionPower() {
        return ServerConfig.HANDLER.instance().bombRadius;
    }
    
}