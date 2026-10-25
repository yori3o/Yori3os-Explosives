package com.yori3o.yo_bombs.compat;

import com.yori3o.yo_bombs.common.YoBombs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;

public final class MinecraftCompat {
    public static Item.Properties itemProperties(String path) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Ids.create(YoBombs.MOD_ID, path)));
    }
    public static <T extends Entity> EntityType<T> entityType(String path, EntityType.EntityFactory<T> factory, float size) {
        return EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(size, size).clientTrackingRange(4).updateInterval(4)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, Ids.create(YoBombs.MOD_ID, path)));
    }
}
