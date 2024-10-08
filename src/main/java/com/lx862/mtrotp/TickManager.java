package com.lx862.mtrotp;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class TickManager {
    private static long last2TickTime = -1;
    private static long lastTickTime = -1;
    private static float tickTime = -1;
    private static boolean serverStarted = false;
    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            serverStarted = true;
            tickTime = -1;
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            serverStarted = false;
            tickTime = -1;
        });

        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            if(!serverStarted) {
                tickTime = -1;
                return;
            }

            if(tickTime == -1) {
                lastTickTime = System.currentTimeMillis();
                tickTime = 1;
            }

            last2TickTime = lastTickTime;
            lastTickTime = System.currentTimeMillis();
            tickTime = (lastTickTime - last2TickTime) / 50F;
        });
    }

    public static float getNextTickTime() {
        return tickTime;
    }
}
