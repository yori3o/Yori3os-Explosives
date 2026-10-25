package com.yori3o.yo_bombs.impl;

import java.util.function.Supplier;
import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PlatformEntityRegistry {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, YoBombs.MOD_ID);

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String path, Supplier<EntityType<T>> factory) {
        return REGISTRY.register(path, factory);
    }
}
