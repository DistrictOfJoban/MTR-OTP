package com.lx862.mtrotp;

import com.lx862.mtrotp.config.Config;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTROTP implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MTROTP");

    @Override
    public void onInitializeClient() {
        Config.load();
        LOGGER.info("[MTR-OTP] MTR-OTP initialized \\(＾▽＾)/");
    }
}
