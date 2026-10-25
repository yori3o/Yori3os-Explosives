package com.yori3o.yo_bombs.neoforge;

import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.init.DispenserBehaviors;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.impl.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@Mod(YoBombs.MOD_ID)
public final class YoBombsNeoForge {
    public YoBombsNeoForge(IEventBus bus) {
        YoBombs.initialize(FMLPaths.CONFIGDIR.get());
        PlatformEntityRegistry.REGISTRY.register(bus);
        PlatformItemRegistry.REGISTRY.register(bus);
        PlatformSoundRegistry.REGISTRY.register(bus);
        CreativeTabRegistry.REGISTRY.register(bus);
        bus.addListener((FMLCommonSetupEvent event) -> event.enqueueWork(DispenserBehaviors::register));
        NeoForge.EVENT_BUS.addListener(LootInjector::onLootLoad);
        NeoForge.EVENT_BUS.addListener((ServerStartedEvent event) -> ServerConfig.HANDLER.load());
    }
}
