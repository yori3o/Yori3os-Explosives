package com.yori3o.yo_bombs.common.init;

import net.minecraft.world.level.block.DispenserBlock;

public final class DispenserBehaviors {
    private DispenserBehaviors() { }

    public static void register() {
        DispenserBlock.registerProjectileBehavior(ItemRegistry.BOMB.get());
        DispenserBlock.registerProjectileBehavior(ItemRegistry.GRENADE.get());
        DispenserBlock.registerProjectileBehavior(ItemRegistry.DYNAMITE.get());
        DispenserBlock.registerProjectileBehavior(ItemRegistry.STICKY_BOMB.get());
        DispenserBlock.registerProjectileBehavior(ItemRegistry.STICKY_GRENADE.get());
        DispenserBlock.registerProjectileBehavior(ItemRegistry.STICKY_DYNAMITE.get());
    }
}
