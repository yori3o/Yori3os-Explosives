package com.yori3o.yo_bombs.common.entity;


import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;



public class StickyGrenadeProjectile extends GrenadeProjectile {


    public StickyGrenadeProjectile(final EntityType<? extends StickyGrenadeProjectile> type, final Level level) {
        super(type, level);
    }

    public StickyGrenadeProjectile(final EntityType<? extends StickyGrenadeProjectile> type, final Level level, double x, double y, double z, final ItemStack itemStack) {
        super(type, level, x, y, z, itemStack);
    }

    public StickyGrenadeProjectile(final EntityType<? extends StickyGrenadeProjectile> type, final Level level, final LivingEntity owner, final ItemStack itemStack) {
        super(type, level, owner, itemStack);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.STICKY_GRENADE.get();
    }

    public boolean isSticky() {
        return true;
    }

    public boolean detonationByEntity() {
        return true;
    }
    
}