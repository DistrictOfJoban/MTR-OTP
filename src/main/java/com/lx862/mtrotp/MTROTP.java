package com.lx862.mtrotp;

import com.lx862.mtrotp.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTROTP implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MTROTP");
    private static float serverTickTime = 1;
    @Override
    public void onInitializeClient() {
        Config.load();

        ServerTickEvents.START_SERVER_TICK.register((server) -> {
            serverTickTime = ((server.getNextTickTime() - Util.getMillis()) / 50F);
        });

        LOGGER.info("[MTR-OTP] MTR-OTP initialized \\(＾▽＾)/");
    }

    public static float getServerTickTime() {
        return serverTickTime;
    }
}
