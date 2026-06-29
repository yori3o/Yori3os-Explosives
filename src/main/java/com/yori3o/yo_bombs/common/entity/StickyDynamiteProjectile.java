package com.yori3o.yo_bombs.common.entity;


import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;



public class StickyDynamiteProjectile extends DynamiteProjectile {


    public StickyDynamiteProjectile(final EntityType<? extends StickyDynamiteProjectile> type, final Level level) {
        super(type, level);
    }

    public StickyDynamiteProjectile(final EntityType<? extends StickyDynamiteProjectile> type, final Level level, double x, double y, double z, final ItemStack itemStack) {
        super(type, level, x, y, z, itemStack);
    }

    public StickyDynamiteProjectile(final EntityType<? extends StickyDynamiteProjectile> type, final Level level, final LivingEntity owner, final ItemStack itemStack) {
        super(type, level, owner, itemStack);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.STICKY_DYNAMITE.get();
    }

    public boolean isSticky() {
        return true;
    }

    public boolean detonationByEntity() {
        return false;
    }
    
}