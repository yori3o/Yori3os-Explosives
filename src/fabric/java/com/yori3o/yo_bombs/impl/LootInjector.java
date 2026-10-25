package com.yori3o.yo_bombs.impl;

import com.yori3o.yo_bombs.common.loot.ExplosiveLoot;
import com.yori3o.yo_bombs.compat.Ids;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

public final class LootInjector {
    public static void register() {
        LootTableEvents.MODIFY.register((key, table, source, registries) -> {
            var pool = ExplosiveLoot.pool(Ids.key(key));
            if (pool != null) table.withPool(pool);
        });
    }
}
