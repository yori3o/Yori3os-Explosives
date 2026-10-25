package com.yori3o.yo_bombs.common.loot;

import com.yori3o.yo_bombs.common.init.ItemRegistry;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import com.yori3o.yo_bombs.compat.LootNumbers;

public final class ExplosiveLoot {
    public static LootPool.Builder pool(String table) {
        boolean mine = table.equals("minecraft:chests/abandoned_mineshaft") || table.equals("terralith:underground/chest");
        boolean dungeon = table.equals("minecraft:chests/simple_dungeon") || table.equals("minecraft:chests/stronghold_corridor");
        if (!mine && !dungeon) return null;
        LootPool.Builder pool = LootPool.lootPool().setRolls(LootNumbers.constant(1))
                .add(LootItem.lootTableItem(ItemRegistry.BOMB.get()).setWeight(2)
                        .apply(SetItemCountFunction.setCount(LootNumbers.between(1, mine ? 6 : 3))))
                .add(EmptyLootItem.emptyItem().setWeight(6));
        if (mine) pool.add(LootItem.lootTableItem(ItemRegistry.DYNAMITE.get()).setWeight(1));
        else pool.add(LootItem.lootTableItem(ItemRegistry.GRENADE.get()).setWeight(1)
                .apply(SetItemCountFunction.setCount(LootNumbers.between(2, 5))));
        return pool;
    }
}
