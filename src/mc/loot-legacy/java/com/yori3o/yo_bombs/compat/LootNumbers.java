package com.yori3o.yo_bombs.compat;

import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class LootNumbers {
    public static NumberProvider constant(int value) {
        return ConstantValue.exactly(value);
    }

    public static NumberProvider between(int min, int max) {
        return UniformGenerator.between(min, max);
    }
}
