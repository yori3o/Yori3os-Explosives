package com.yori3o.yo_bombs.impl;

import com.yori3o.yo_bombs.common.loot.ExplosiveLoot;
import net.neoforged.neoforge.event.LootTableLoadEvent;

public final class LootInjector {
    public static void onLootLoad(LootTableLoadEvent event) {
        var pool = ExplosiveLoot.pool(event.getName().toString());
        if (pool != null) event.getTable().addPool(pool.build());
    }
}
