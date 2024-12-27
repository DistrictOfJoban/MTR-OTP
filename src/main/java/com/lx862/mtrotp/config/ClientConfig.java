package com.lx862.mtrotp.config;

import com.google.gson.*;
import com.lx862.mtrotp.MTROTPClient;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class ClientConfig {
    private static final Path CONFIG_PATH = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "mtrotp.json");
    public static boolean cullTrain = true;
    public static boolean dashboardLazyRender = true;

    public static void load() {
        if(!Files.exists(CONFIG_PATH)) {
            MTROTPClient.LOGGER.info("[MTR-OTP] Client config not found, generating one...");
            writeConfig();
            return;
        }

        MTROTPClient.LOGGER.info("[MTR-OTP] Reading client config...");
        try {
            final JsonObject jsonConfig = new JsonParser().parse(String.join("", Files.readAllLines(CONFIG_PATH))).getAsJsonObject();
            try {
                cullTrain = jsonConfig.get("cullTrain").getAsBoolean();
            } catch (Exception ignored) {}

            try {
                dashboardLazyRender = jsonConfig.get("dashboardLazyRender").getAsBoolean();
            } catch (Exception ignored) {}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeConfig() {
        MTROTPClient.LOGGER.info("[MTR-OTP] Writing client config...");
        final JsonObject jsonConfig = new JsonObject();
        jsonConfig.addProperty("cullTrain", cullTrain);
        jsonConfig.addProperty("dashboardLazyRender", dashboardLazyRender);

        try {
            Files.write(CONFIG_PATH, Collections.singleton(new GsonBuilder().setPrettyPrinting().create().toJson(jsonConfig)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
