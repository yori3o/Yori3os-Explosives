package com.yori3o.yo_bombs.fabric;

import com.yori3o.yo_bombs.common.init.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class YoBombsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.BOMB.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.GRENADE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.DYNAMITE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.STICKY_BOMB.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.STICKY_GRENADE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.STICKY_DYNAMITE.get(), ThrownItemRenderer::new);
    }
}
