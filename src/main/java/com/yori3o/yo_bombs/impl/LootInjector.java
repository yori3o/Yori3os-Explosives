package com.yori3o.yo_bombs.impl;


import com.yori3o.yo_bombs.common.init.ItemRegistry;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;



public class LootInjector {

    private static final Identifier MINESHAFT =
            Identifier.fromNamespaceAndPath("minecraft", "chests/abandoned_mineshaft");
    private static final Identifier SIMPLE_DUNGEON =
            Identifier.fromNamespaceAndPath("minecraft", "chests/simple_dungeon");
    private static final Identifier STRONGHOLD_CORRIDOR =
            Identifier.fromNamespaceAndPath("minecraft", "chests/stronghold_corridor");

    // mod support
    private static final Identifier UNDERGROUND_TERRALITH =
            Identifier.fromNamespaceAndPath("terralith", "underground/chest");

    //private static final Identifier STRONGHOLD_ARMORY_ENDERSCAPE =
    //        Identifier.fromNamespaceAndPath("enderscape", "stronghold/chest/armory");


    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.identifier().equals(MINESHAFT) || key.identifier().equals(UNDERGROUND_TERRALITH)) {

                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ItemRegistry.BOMB.get())
                                .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 6)))
                        )
                        .add(LootItem.lootTableItem(ItemRegistry.DYNAMITE.get())
                                .setWeight(1)
                        )
                        .add(EmptyLootItem.emptyItem().setWeight(6))
                        .build();

                tableBuilder.pool(pool);
            } else if (key.identifier().equals(SIMPLE_DUNGEON) || key.identifier().equals(STRONGHOLD_CORRIDOR)) {

                LootPool pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ItemRegistry.BOMB.get())
                                .setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                        )
                        .add(LootItem.lootTableItem(ItemRegistry.GRENADE.get())
                                .setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))
                        )
                        .add(EmptyLootItem.emptyItem().setWeight(6))
                        .build();

                tableBuilder.pool(pool);
            }
        });
    }
    
}
