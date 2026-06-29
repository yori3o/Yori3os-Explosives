package com.yori3o.yo_bombs.fabric;


import com.yori3o.yo_bombs.common.YoBombs;
import com.yori3o.yo_bombs.common.event.EventHandler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;



public class YoBombsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        (new YoBombs()).init();

        ServerLifecycleEvents.SERVER_STARTED.register((minecraftServer) -> {
            EventHandler.serverStarted(minecraftServer);
        });

    }

        /*ServerPlayConnectionEvents.JOIN.register((serverGamePacketListenerImpl, packetSender, minecraftServer) -> {
            EventHandler.whenPlayerJoinToServer(serverGamePacketListenerImpl.player);
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if (livingEntity instanceof Player player) {
                EventHandler.whenPlayerDie(player, damageSource);
            }
        });*/

        

}
