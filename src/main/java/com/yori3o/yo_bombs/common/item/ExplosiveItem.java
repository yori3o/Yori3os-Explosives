package com.yori3o.yo_bombs.common.item;


import com.yori3o.yo_bombs.common.entity.ExplosiveProjectile;
import com.yori3o.yo_bombs.common.sound.SoundRegistry;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;



public abstract class ExplosiveItem extends Item implements ProjectileItem {
    public ExplosiveItem(Item.Properties properties) {
        super(properties);
    }

    public abstract int getCooldown();
    
    public abstract float getThrowingVelocity();

    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            ExplosiveProjectile entity = Projectile.spawnProjectileFromRotation((world, owner, itemStack) -> getEntity(level, player, itemStack), serverLevel, stack, player, 0.0F, getThrowingVelocity(), 1.0F);
            level.playSound(null, entity, getSound(), SoundSource.PLAYERS, getSoundVolume(), 1.0f);
            level.playSound(null, player, SoundRegistry.THROW, SoundSource.PLAYERS, 0.2f, 0.5f + (level.getRandom().nextFloat() * 0.2f));
        } 
        
        stack.consume(1, player);
        player.getCooldowns().addCooldown(stack, getCooldown());
        player.awardStat(Stats.ITEM_USED.get(this));

        return (InteractionResult)InteractionResult.SUCCESS;
    }

    public abstract Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction);
    
    public abstract ExplosiveProjectile getEntity(Level level, Player owner, ItemStack itemStack);
    
    public SoundEvent getSound() {
        int sec = getSoundTimeSeconds();
        if (sec == 3) {
            return SoundRegistry.FUSE_3_SEC;
        } else if (sec == 5) {
            return SoundRegistry.FUSE_5_SEC;
        } else if (sec == 7) {
            return SoundRegistry.FUSE_7_SEC;
        }
        return SoundRegistry.FUSE_3_SEC;
    }

    public float getSoundVolume() {
        return 0.55f;
    }

    public abstract int getSoundTimeSeconds();

}