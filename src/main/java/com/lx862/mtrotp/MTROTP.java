package com.lx862.mtrotp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTROTP implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MTROTP");
    private static long last2TickTime = -1;
    private static long lastTickTime = -1;
    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            if(lastTickTime == -1) lastTickTime = System.currentTimeMillis();

            last2TickTime = lastTickTime;
            lastTickTime = System.currentTimeMillis();
        });

        LOGGER.info("[MTR-OTP] MTR-OTP initialized \\(＾▽＾)/");
    }

    public static float getNextTickTime() {
        return Math.max(1F, ((lastTickTime - last2TickTime) / 50F));
    }
}
