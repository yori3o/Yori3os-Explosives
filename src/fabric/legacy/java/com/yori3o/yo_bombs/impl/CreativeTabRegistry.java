package com.yori3o.yo_bombs.impl;

import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.init.CreativeTabContents;
import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

public final class CreativeTabRegistry {
    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Ids.create(YoBombs.MOD_ID, "bombs"),
                CreativeTabContents.configure(FabricItemGroup.builder()).build());
    }
}
