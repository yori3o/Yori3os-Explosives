package com.yori3o.yo_bombs.common.entity;


import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;



public class StickyBombProjectile extends BombProjectile {


    public StickyBombProjectile(final EntityType<? extends StickyBombProjectile> type, final Level level) {
        super(type, level);
    }

    public StickyBombProjectile(final EntityType<? extends StickyBombProjectile> type, final Level level, double x, double y, double z, final ItemStack itemStack) {
        super(type, level, x, y, z, itemStack);
    }

    public StickyBombProjectile(final EntityType<? extends StickyBombProjectile> type, final Level level, final LivingEntity owner, final ItemStack itemStack) {
        super(type, level, owner, itemStack);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.STICKY_BOMB.get();
    }

    public boolean isSticky() {
        return true;
    }

    public boolean detonationByEntity() {
        return false;
    }
    
}