package com.yori3o.yo_bombs.neoforge;

import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.init.EntityRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = YoBombs.MOD_ID, dist = Dist.CLIENT)
public final class YoBombsNeoForgeClient {
    public YoBombsNeoForgeClient(IEventBus bus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mod, parent) -> com.yori3o.yo_bombs.common.config.ConfigScreenFactory.create(parent));
        bus.addListener(YoBombsNeoForgeClient::registerRenderers);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.BOMB.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityRegistry.GRENADE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityRegistry.DYNAMITE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityRegistry.STICKY_BOMB.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityRegistry.STICKY_GRENADE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityRegistry.STICKY_DYNAMITE.get(), ThrownItemRenderer::new);
    }
}
