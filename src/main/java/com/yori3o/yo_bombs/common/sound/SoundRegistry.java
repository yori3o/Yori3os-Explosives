package com.yori3o.yo_bombs.common.sound;


import com.yori3o.yo_bombs.impl.PlatformSoundRegistry;

import com.yori3o.yo_bombs.compat.Ids;
import net.minecraft.sounds.SoundEvent;



public class SoundRegistry {


    public static final SoundEvent FUSE_3_SEC = SoundEvent.createVariableRangeEvent(Ids.create("yo_bombs", "fuse_3_sec"));
    public static final SoundEvent FUSE_5_SEC = SoundEvent.createVariableRangeEvent(Ids.create("yo_bombs", "fuse_5_sec"));
    public static final SoundEvent FUSE_7_SEC = SoundEvent.createVariableRangeEvent(Ids.create("yo_bombs", "fuse_7_sec"));
    public static final SoundEvent THROW = SoundEvent.createVariableRangeEvent(Ids.create("yo_bombs", "throw"));


    public static void register() {
        PlatformSoundRegistry.registerSound("fuse_3_sec", FUSE_3_SEC);
        PlatformSoundRegistry.registerSound("fuse_5_sec", FUSE_5_SEC);
        PlatformSoundRegistry.registerSound("fuse_7_sec", FUSE_7_SEC);
        PlatformSoundRegistry.registerSound("throw", THROW);
    }

}
