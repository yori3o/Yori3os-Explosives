package com.yori3o.yo_bombs.impl;


import net.minecraft.client.KeyMapping;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;



public class PlatformKeyMappingRegistry {

    
    public static void registerKeyMapping(KeyMapping keyMapping) {
        KeyMappingHelper.registerKeyMapping(keyMapping);
    }
 
}

