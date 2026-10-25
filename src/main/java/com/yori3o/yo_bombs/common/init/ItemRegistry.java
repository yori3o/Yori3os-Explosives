package com.yori3o.yo_bombs.common.init;

import java.util.function.Supplier;
import com.yori3o.yo_bombs.common.item.*;
import com.yori3o.yo_bombs.compat.MinecraftCompat;
import com.yori3o.yo_bombs.impl.PlatformItemRegistry;
import net.minecraft.world.item.Item;

public final class ItemRegistry {
    public static final Supplier<Item> BOMB = PlatformItemRegistry.registerItem("bomb", () -> new BombItem(MinecraftCompat.itemProperties("bomb")));
    public static final Supplier<Item> GRENADE = PlatformItemRegistry.registerItem("grenade", () -> new GrenadeItem(MinecraftCompat.itemProperties("grenade")));
    public static final Supplier<Item> DYNAMITE = PlatformItemRegistry.registerItem("dynamite", () -> new DynamiteItem(MinecraftCompat.itemProperties("dynamite")));
    public static final Supplier<Item> STICKY_BOMB = PlatformItemRegistry.registerItem("sticky_bomb", () -> new StickyBombItem(MinecraftCompat.itemProperties("sticky_bomb")));
    public static final Supplier<Item> STICKY_GRENADE = PlatformItemRegistry.registerItem("sticky_grenade", () -> new StickyGrenadeItem(MinecraftCompat.itemProperties("sticky_grenade")));
    public static final Supplier<Item> STICKY_DYNAMITE = PlatformItemRegistry.registerItem("sticky_dynamite", () -> new StickyDynamiteItem(MinecraftCompat.itemProperties("sticky_dynamite")));
    public static void register() { }
}
