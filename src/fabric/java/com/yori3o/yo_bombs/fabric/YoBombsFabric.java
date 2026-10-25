package com.yori3o.yo_bombs.fabric;

import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.init.DispenserBehaviors;
import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.impl.CreativeTabRegistry;
import com.yori3o.yo_bombs.impl.LootInjector;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

public final class YoBombsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        YoBombs.initialize(FabricLoader.getInstance().getConfigDir());
        DispenserBehaviors.register();
        CreativeTabRegistry.register();
        LootInjector.register();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> ServerConfig.HANDLER.load());
    }
}
