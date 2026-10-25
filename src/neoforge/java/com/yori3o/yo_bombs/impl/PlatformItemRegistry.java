package com.yori3o.yo_bombs.impl;

import java.util.function.Supplier;
import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PlatformItemRegistry {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, YoBombs.MOD_ID);

    public static <T extends Item> Supplier<T> registerItem(String path, Supplier<T> factory) {
        return REGISTRY.register(path, factory);
    }
}
