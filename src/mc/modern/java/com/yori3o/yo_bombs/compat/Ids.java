package com.yori3o.yo_bombs.compat;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public final class Ids {
    public static Identifier create(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
    public static String key(ResourceKey<?> key) {
        return key.identifier().toString();
    }
}
