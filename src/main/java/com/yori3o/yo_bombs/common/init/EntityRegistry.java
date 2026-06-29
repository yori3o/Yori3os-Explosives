package com.yori3o.yo_bombs.common.init;


import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.entity.*;
import com.yori3o.yo_bombs.impl.PlatformEntityRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;



public class EntityRegistry {


    public static final Supplier<EntityType<BombProjectile>> BOMB =
        PlatformEntityRegistry.registerEntity(
            Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "bomb"),
            () -> EntityType.Builder.<BombProjectile>of( 
                (type, level) -> new BombProjectile(type, level),
                MobCategory.MISC
            )
            .sized(0.35f, 0.35f)
            .clientTrackingRange(4)
            .updateInterval(4)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "bomb")))
                    
        );

    public static final Supplier<EntityType<GrenadeProjectile>> GRENADE =
        PlatformEntityRegistry.registerEntity(
            Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "grenade"),
            () -> EntityType.Builder.<GrenadeProjectile>of( 
                (type, level) -> new GrenadeProjectile(type, level),
                MobCategory.MISC
            )
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(4)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "grenade")))
                    
        );

    public static final Supplier<EntityType<DynamiteProjectile>> DYNAMITE =
        PlatformEntityRegistry.registerEntity(
            Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "dynamite"),
            () -> EntityType.Builder.<DynamiteProjectile>of( 
                (type, level) -> new DynamiteProjectile(type, level),
                MobCategory.MISC
            )
            .sized(0.4f, 0.4f)
            .clientTrackingRange(4)
            .updateInterval(4)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "dynamite")))
                    
        );

    public static final Supplier<EntityType<StickyBombProjectile>> STICKY_BOMB =
        PlatformEntityRegistry.registerEntity(
            Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "sticky_bomb"),
            () -> EntityType.Builder.<StickyBombProjectile>of( 
                (type, level) -> new StickyBombProjectile(type, level),
                MobCategory.MISC
            )
            .sized(0.35f, 0.35f)
            .clientTrackingRange(4)
            .updateInterval(4)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "sticky_bomb")))
                    
        );

    public static final Supplier<EntityType<StickyGrenadeProjectile>> STICKY_GRENADE =
        PlatformEntityRegistry.registerEntity(
            Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "sticky_grenade"),
            () -> EntityType.Builder.<StickyGrenadeProjectile>of( 
                (type, level) -> new StickyGrenadeProjectile(type, level),
                MobCategory.MISC
            )
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(4)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "sticky_grenade")))
                    
        );

    public static final Supplier<EntityType<StickyDynamiteProjectile>> STICKY_DYNAMITE =
        PlatformEntityRegistry.registerEntity(
            Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "sticky_dynamite"),
            () -> EntityType.Builder.<StickyDynamiteProjectile>of( 
                (type, level) -> new StickyDynamiteProjectile(type, level),
                MobCategory.MISC
            )
            .sized(0.4f, 0.4f)
            .clientTrackingRange(4)
            .updateInterval(4)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "sticky_dynamite")))
                    
        );

    public static void register() {}

}
