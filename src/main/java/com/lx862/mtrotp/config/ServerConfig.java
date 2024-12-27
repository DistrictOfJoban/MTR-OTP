package com.lx862.mtrotp.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lx862.mtrotp.MTROTPClient;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class ServerConfig {
    private static final Path CONFIG_PATH = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), "mtrotp_server.json");
    public static int trainUpdateDistance = 128;

    public static void load() {
        if(!Files.exists(CONFIG_PATH)) {
            MTROTPClient.LOGGER.info("[MTR-OTP] Server config not found, generating one...");
            writeConfig();
            return;
        }

        MTROTPClient.LOGGER.info("[MTR-OTP] Reading server config...");
        try {
            final JsonObject jsonConfig = JsonParser.parseString(String.join("", Files.readAllLines(CONFIG_PATH))).getAsJsonObject();
            try {
                trainUpdateDistance = jsonConfig.get("trainUpdateDistance").getAsInt();
            } catch (Exception ignored) {}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeConfig() {
        MTROTPClient.LOGGER.info("[MTR-OTP] Writing server config...");
        final JsonObject jsonConfig = new JsonObject();
        jsonConfig.addProperty("trainUpdateDistance", trainUpdateDistance);

        try {
            Files.write(CONFIG_PATH, Collections.singleton(new GsonBuilder().setPrettyPrinting().create().toJson(jsonConfig)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
