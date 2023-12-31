package com.lx862.mtrotp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTROTP implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MTROTP");
    private static long last2TickTime = -1;
    private static long lastTickTime = -1;
    private static boolean serverStarted = false;
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            serverStarted = true;
            last2TickTime = -1;
            lastTickTime = -1;
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            serverStarted = false;
            last2TickTime = -1;
            lastTickTime = -1;
        });

        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            if(!serverStarted) return;

            if(lastTickTime == -1) lastTickTime = System.currentTimeMillis();

            last2TickTime = lastTickTime;
            lastTickTime = System.currentTimeMillis();
        });

        LOGGER.info("[MTR-OTP] MTR-OTP initialized \\(＾▽＾)/");
    }

    public static float getNextTickTime() {
        if(lastTickTime == -1 || last2TickTime == -1) return 1;

        float result = (lastTickTime - last2TickTime) / 50F;
        // 30 second has elapsed, seriously?
        if(result >= 20 * 30) {
            LOGGER.warn("[MTR-OTP] More than 30s has elapsed since last tick, too much to skip!");
            return 1;
        }

        return Math.max(1F, result);
    }
}
