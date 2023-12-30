package com.lx862.mtrotp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTROTP implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MTROTP");
    private static float nextTickTime = -1;
    @Override
    public void onInitialize() {
        ServerTickEvents.START_SERVER_TICK.register((server) -> {
            nextTickTime = server.getNextTickTime() - Util.getMillis();
        });

        LOGGER.info("[MTR-OTP] MTR-OTP initialized \\(＾▽＾)/");
    }

    public static float getNextTickTime() {
        return nextTickTime == -1 ? 1 : (nextTickTime / 50F);
    }
}
