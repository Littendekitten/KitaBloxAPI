package com.kitablox.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class KitaBloxConfig {
    public static final String VERSION = "0.1.0";
    public boolean nvidiaFpsBoost = false;
    public boolean lowFire = true;
    public boolean smallTotem = false;
    public boolean smallTotemPop = true;
    public boolean clientSideCombo = false;
    public boolean consumableOptimizer = true;
    public boolean crystalVisualOptimization = true;
    public boolean anchorVisualOptimization = true;
    public boolean particlesOptimization = true;
    public boolean entityOptimization = true;
    public boolean animationOptimization = true;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("kitablox-api.json");

    public static KitaBloxConfig load() {
        try {
            if (Files.exists(FILE)) {
                try (Reader reader = Files.newBufferedReader(FILE)) {
                    KitaBloxConfig cfg = GSON.fromJson(reader, KitaBloxConfig.class);
                    if (cfg != null) return cfg;
                }
            }
        } catch (Exception e) {
            KitaBloxApiClient.LOGGER.warn("Could not load config", e);
        }
        return new KitaBloxConfig();
    }

    public void save() {
        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (Exception e) {
            KitaBloxApiClient.LOGGER.warn("Could not save config", e);
        }
    }
}
