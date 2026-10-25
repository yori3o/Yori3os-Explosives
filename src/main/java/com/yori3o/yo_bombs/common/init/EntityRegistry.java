package com.yori3o.yo_bombs.common.init;

import java.util.function.Supplier;
import com.yori3o.yo_bombs.common.entity.*;
import com.yori3o.yo_bombs.compat.MinecraftCompat;
import com.yori3o.yo_bombs.impl.PlatformEntityRegistry;
import net.minecraft.world.entity.EntityType;

public final class EntityRegistry {
    public static final Supplier<EntityType<BombProjectile>> BOMB = PlatformEntityRegistry.registerEntity("bomb", () -> MinecraftCompat.entityType("bomb", BombProjectile::new, 0.35f));
    public static final Supplier<EntityType<GrenadeProjectile>> GRENADE = PlatformEntityRegistry.registerEntity("grenade", () -> MinecraftCompat.entityType("grenade", GrenadeProjectile::new, 0.25f));
    public static final Supplier<EntityType<DynamiteProjectile>> DYNAMITE = PlatformEntityRegistry.registerEntity("dynamite", () -> MinecraftCompat.entityType("dynamite", DynamiteProjectile::new, 0.4f));
    public static final Supplier<EntityType<StickyBombProjectile>> STICKY_BOMB = PlatformEntityRegistry.registerEntity("sticky_bomb", () -> MinecraftCompat.entityType("sticky_bomb", StickyBombProjectile::new, 0.35f));
    public static final Supplier<EntityType<StickyGrenadeProjectile>> STICKY_GRENADE = PlatformEntityRegistry.registerEntity("sticky_grenade", () -> MinecraftCompat.entityType("sticky_grenade", StickyGrenadeProjectile::new, 0.25f));
    public static final Supplier<EntityType<StickyDynamiteProjectile>> STICKY_DYNAMITE = PlatformEntityRegistry.registerEntity("sticky_dynamite", () -> MinecraftCompat.entityType("sticky_dynamite", StickyDynamiteProjectile::new, 0.4f));
    public static void register() { }
}
