package com.yori3o.yo_bombs.impl;

import java.util.function.Supplier;
import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class PlatformItemRegistry {

    public static <T extends Item> Supplier<T> registerItem(String path, Supplier<T> factory) {
        T value = Registry.register(BuiltInRegistries.ITEM, Ids.create(YoBombs.MOD_ID, path), factory.get());
        return () -> value;
    }
}
