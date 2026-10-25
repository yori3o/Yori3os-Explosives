package com.yori3o.yo_bombs.compat;

import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;

public final class LootNumbers {
    public static Holder<ContextIntProvider> constant(int value) {
        return Holder.direct(new ConstantValue(value));
    }

    public static Holder<ContextIntProvider> between(int min, int max) {
        return Holder.direct(new UniformGenerator(constant(min), constant(max)));
    }
}
