package com.yori3o.yo_bombs.common.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class CreativeTabContents {
    public static CreativeModeTab.Builder configure(CreativeModeTab.Builder builder) {
        return builder.title(Component.translatable("itemGroup.yo_bombs"))
                .icon(() -> new ItemStack(ItemRegistry.BOMB.get()))
                .displayItems((parameters, output) -> {
                    output.accept(ItemRegistry.BOMB.get());
                    output.accept(ItemRegistry.GRENADE.get());
                    output.accept(ItemRegistry.DYNAMITE.get());
                    output.accept(ItemRegistry.STICKY_BOMB.get());
                    output.accept(ItemRegistry.STICKY_GRENADE.get());
                    output.accept(ItemRegistry.STICKY_DYNAMITE.get());
                });
    }
}
