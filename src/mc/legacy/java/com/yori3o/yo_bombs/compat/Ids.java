package com.yori3o.yo_bombs.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;

public final class Ids {
    public static ResourceLocation create(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
    public static String key(ResourceKey<?> key) {
        return key.location().toString();
    }
}
