package com.yori3o.yo_bombs.common.item;


import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.common.entity.BombProjectile;
import com.yori3o.yo_bombs.common.entity.ExplosiveProjectile;
import com.yori3o.yo_bombs.common.init.EntityRegistry;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;



public class BombItem extends ExplosiveItem {
    public BombItem(Item.Properties properties) {
        super(properties);
    }

    public int getCooldown() {
        return 40;
    }

    public float getThrowingVelocity() {
        return 0.8f;
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return (Projectile)new BombProjectile(EntityRegistry.BOMB.get(), level, pos.x(), pos.y(), pos.z(), stack);
    }

    public ExplosiveProjectile getEntity(Level level, Player owner, ItemStack itemStack) {
        return new BombProjectile(EntityRegistry.BOMB.get(), level, owner, itemStack);
    }

    public int getSoundTimeSeconds() {
        return ServerConfig.HANDLER.instance().bombDetonationTime;
    }

}