package com.yori3o.yo_bombs.fabric;


import com.yori3o.yo_bombs.impl.CreativeTabRegistry;

import net.fabricmc.api.ClientModInitializer;



public final class YoBombsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        CreativeTabRegistry.register();
        
        /*ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            EventHandler.whenClientTickStart();;
        });*/
    }

}
