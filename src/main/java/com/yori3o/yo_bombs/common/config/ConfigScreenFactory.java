package com.yori3o.yo_bombs.common.config;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import dev.isxander.yacl3.api.YetAnotherConfigLib;



public final class ConfigScreenFactory {

    public static Screen create(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Yori3o's Explosives"))
                .categories(ServerConfig.HANDLER.generateGui().categories())
                .save(() -> ServerConfig.HANDLER.save())
                .build()
                .generateScreen(parent);
    }

}
