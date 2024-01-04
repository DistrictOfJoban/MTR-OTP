package com.lx862.mtrotp;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTROTP implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MTROTP");
    @Override
    public void onInitialize() {
        TickManager.initialize();
        LOGGER.info("[MTR-OTP] MTR-OTP initialized \\(＾▽＾)/");
    }
}
