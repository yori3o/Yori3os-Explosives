package com.yori3o.yo_bombs.common.sound;


import com.yori3o.yo_bombs.impl.PlatformSoundRegistry;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;



public class SoundRegistry {


    public static final SoundEvent FUSE_3_SEC = SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("yo_bombs", "fuse_3_sec"));
    public static final SoundEvent FUSE_5_SEC = SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("yo_bombs", "fuse_5_sec"));
    public static final SoundEvent FUSE_7_SEC = SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("yo_bombs", "fuse_7_sec"));
    public static final SoundEvent THROW = SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("yo_bombs", "throw"));


    public static void register() {
        PlatformSoundRegistry.registerSound(FUSE_3_SEC.location(), FUSE_3_SEC);
        PlatformSoundRegistry.registerSound(FUSE_5_SEC.location(), FUSE_5_SEC);
        PlatformSoundRegistry.registerSound(FUSE_7_SEC.location(), FUSE_7_SEC);
        PlatformSoundRegistry.registerSound(THROW.location(), THROW);
    }

}