package com.yori3o.yo_bombs.common;

import java.nio.file.Path;
import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.common.init.EntityRegistry;
import com.yori3o.yo_bombs.common.init.ItemRegistry;
import com.yori3o.yo_bombs.common.sound.SoundRegistry;

public final class YoBombs {
    public static final String MOD_ID = "yo_bombs";

    public static void initialize(Path configDirectory) {
        ServerConfig.initialize(configDirectory);
        EntityRegistry.register();
        ItemRegistry.register();
        SoundRegistry.register();
    }
}
