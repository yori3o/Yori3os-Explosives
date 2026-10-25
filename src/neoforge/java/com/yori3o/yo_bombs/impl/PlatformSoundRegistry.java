package com.yori3o.yo_bombs.impl;

import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class PlatformSoundRegistry {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, YoBombs.MOD_ID);
    public static void registerSound(String path, SoundEvent event) {
        REGISTRY.register(path, () -> event);
    }
}
