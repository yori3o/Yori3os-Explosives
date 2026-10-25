package com.yori3o.yo_bombs.impl;

import java.util.function.Supplier;
import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class PlatformEntityRegistry {

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String path, Supplier<EntityType<T>> factory) {
        EntityType<T> value = Registry.register(BuiltInRegistries.ENTITY_TYPE, Ids.create(YoBombs.MOD_ID, path), factory.get());
        return () -> value;
    }
}
