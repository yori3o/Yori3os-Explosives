package com.yori3o.yo_bombs.impl;

import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.init.CreativeTabContents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YoBombs.MOD_ID);
    static {
        REGISTRY.register("yo_bombs", () -> CreativeTabContents.configure(CreativeModeTab.builder()).build());
    }
}
