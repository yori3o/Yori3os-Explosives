package com.yori3o.yo_bombs.common.init;


import com.yori3o.yo_bombs.common.item.*;
import com.yori3o.yo_bombs.impl.PlatformItemRegistry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;



public class ItemRegistry {

    

    public static void register() {}

    public static Supplier<Item> register(String id, Item item) {
        return PlatformItemRegistry.registerItem(Identifier.fromNamespaceAndPath("yo_bombs", id), () -> item);
    }


    public static final Supplier<Item> BOMB = register(
        "bomb",
        new BombItem(new Item.Properties()
            .setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("yo_bombs", "bomb")))
        )
    );

    public static final Supplier<Item> GRENADE = register(
        "grenade",
        new GrenadeItem(new Item.Properties()
            .setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("yo_bombs", "grenade")))
        )
    );

    public static final Supplier<Item> DYNAMITE = register(
        "dynamite",
        new DynamiteItem(new Item.Properties()
            .setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("yo_bombs", "dynamite")))
        )
    );

    public static final Supplier<Item> STICKY_BOMB = register(
        "sticky_bomb",
        new StickyBombItem(new Item.Properties()
            .setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("yo_bombs", "sticky_bomb")))
        )
    );

    public static final Supplier<Item> STICKY_GRENADE = register(
        "sticky_grenade",
        new StickyGrenadeItem(new Item.Properties()
            .setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("yo_bombs", "sticky_grenade")))
        )
    );

    public static final Supplier<Item> STICKY_DYNAMITE = register(
        "sticky_dynamite",
        new StickyDynamiteItem(new Item.Properties()
            .setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), Identifier.fromNamespaceAndPath("yo_bombs", "sticky_dynamite")))
        )
    );
    
}
