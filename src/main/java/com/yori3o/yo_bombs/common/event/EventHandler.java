package com.yori3o.yo_bombs.common.event;


import net.minecraft.server.MinecraftServer;



public class EventHandler {


    public static void serverStarted(MinecraftServer minecraftServer) {
        ServerEvents.loadServerConfig();
	}

}