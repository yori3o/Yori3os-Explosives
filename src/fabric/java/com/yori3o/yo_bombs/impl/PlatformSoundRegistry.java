package com.yori3o.yo_bombs.impl;

import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class PlatformSoundRegistry {
    public static void registerSound(String path, SoundEvent event) {
        Registry.register(BuiltInRegistries.SOUND_EVENT, Ids.create(YoBombs.MOD_ID, path), event);
    }
}
