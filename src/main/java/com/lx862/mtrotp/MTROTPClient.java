package com.lx862.mtrotp;

import com.lx862.mtrotp.config.ClientConfig;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTROTPClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("MTROTPClient");
    @Override
    public void onInitializeClient() {
        ClientConfig.load();
    }
}
