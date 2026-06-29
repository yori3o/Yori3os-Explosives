package com.yori3o.yo_bombs.impl;


import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;



public class CreativeTabRegistry {

    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(YoBombs.MOD_ID, "bombs"));
    public static final CreativeModeTab CUSTOM_ITEM_GROUP = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ItemRegistry.BOMB.get()))
            .title(Component.translatable("itemGroup.yo_bombs"))
            .build();

            
    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);
        
        CreativeModeTabEvents.modifyOutputEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {

            itemGroup.accept(ItemRegistry.BOMB.get());
            itemGroup.accept(ItemRegistry.GRENADE.get());
            itemGroup.accept(ItemRegistry.DYNAMITE.get());
            itemGroup.accept(ItemRegistry.STICKY_BOMB.get());
            itemGroup.accept(ItemRegistry.STICKY_GRENADE.get());
            itemGroup.accept(ItemRegistry.STICKY_DYNAMITE.get());
            
        });
    }
}
