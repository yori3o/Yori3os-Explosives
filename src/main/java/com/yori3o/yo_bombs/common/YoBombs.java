package com.yori3o.yo_bombs.common;


import com.yori3o.yo_bombs.common.config.ServerConfig;
import com.yori3o.yo_bombs.common.init.*;
import com.yori3o.yo_bombs.common.sound.SoundRegistry;
import com.yori3o.yo_bombs.impl.LootInjector;
import com.yori3o.yo_bombs.impl.PlatformUtil;



public class YoBombs {


	public static final String MOD_ID = "yo_bombs";

    
	public void init() {

        ServerConfig.HANDLER.load();

		EntityRegistry.register();
		ItemRegistry.register();
        SoundRegistry.register();
        EntityRegistry.register();
        LootInjector.register();

		
		if (PlatformUtil.isClient()) {
            YoBombsClient.initClient();
        }

	}

}