package com.yori3o.yo_bombs.common.event;


import com.yori3o.yo_bombs.common.config.ServerConfig;



public class ServerEvents {


    public static void loadServerConfig() {
        ServerConfig.HANDLER.load();
	}

}