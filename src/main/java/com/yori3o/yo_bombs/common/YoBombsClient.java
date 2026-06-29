package com.yori3o.yo_bombs.common;


import com.yori3o.yo_bombs.common.init.EntityRegistry;
import com.yori3o.yo_bombs.impl.PlatformEntityRendererRegistry;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;



public class YoBombsClient {
    

    /*public static final Category YO_bombs_CATEGORY = new Category(Identifier.fromNamespaceAndPath("yo_bombs", "yo_bo        "));

    public static final KeyMapping JUMP = new KeyMapping(
        "key.yo_bombs.jump",
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_SPACE,
        YO_bombs_CATEGORY
    );
    public static final KeyMapping CLIMB = new KeyMapping(
        "key.yo_bombs.climb",
        InputConstants.Type.KEYSYM,
        InputConstants.UNKNOWN.getValue(),
        YO_bombs_CATEGORY
    );
    public static final KeyMapping CLIMB_DOWN = new KeyMapping(
        "key.yo_bombs.climb_down",
        InputConstants.Type.KEYSYM,
        InputConstants.UNKNOWN.getValue(),
        YO_bombs_CATEGORY
    );

    public static final KeyMapping PREVENT_USE = new KeyMapping(
        "key.yo_bombs.prevent_use",
        InputConstants.Type.KEYSYM,
        InputConstants.UNKNOWN.getValue(),
        YO_bombs_CATEGORY
    );*/


    public static void initClient() {
        /*PlatformKeyMappingRegistry.registerKeyMapping(JUMP);
        PlatformKeyMappingRegistry.registerKeyMapping(CLIMB);
        PlatformKeyMappingRegistry.registerKeyMapping(CLIMB_DOWN);
        PlatformKeyMappingRegistry.registerKeyMapping(PREVENT_USE);
        
        ConfigManager.loadClient();*/

        PlatformEntityRendererRegistry.registerEntityRenderer(EntityRegistry.BOMB.get(), ThrownItemRenderer::new);
        PlatformEntityRendererRegistry.registerEntityRenderer(EntityRegistry.GRENADE.get(), ThrownItemRenderer::new);
        PlatformEntityRendererRegistry.registerEntityRenderer(EntityRegistry.DYNAMITE.get(), ThrownItemRenderer::new);
        PlatformEntityRendererRegistry.registerEntityRenderer(EntityRegistry.STICKY_BOMB.get(), ThrownItemRenderer::new);
        PlatformEntityRendererRegistry.registerEntityRenderer(EntityRegistry.STICKY_GRENADE.get(), ThrownItemRenderer::new);
        PlatformEntityRendererRegistry.registerEntityRenderer(EntityRegistry.STICKY_DYNAMITE.get(), ThrownItemRenderer::new);
    }
    
}
