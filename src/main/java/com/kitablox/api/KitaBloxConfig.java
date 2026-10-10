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
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("kitablox-api.json");

    // Implemented settings.
    public boolean nvidiaFpsBoost = false;
    public boolean entityOptimization = false;
    public boolean animationOptimization = false;

    // Reserved for future verified hooks. The UI reports these as unavailable
    // rather than presenting non-functional toggles as if they worked.
    public boolean lowFire = false;
    public boolean smallTotem = false;
    public boolean smallTotemPop = false;
    public boolean clientSideCombo = false;
    public boolean consumableOptimizer = false;
    public boolean crystalVisualOptimization = false;
    public boolean anchorVisualOptimization = false;
    public boolean particlesOptimization = false;

    // Saved original Minecraft options, so the profile can restore them.
    public boolean hasCapturedVideoSettings = false;
    public String originalCloudRenderMode = "";
    public boolean originalEntityShadows = true;
    public boolean originalBobView = true;

    public static KitaBloxConfig load() {
        try {
            if (Files.exists(FILE)) {
                try (Reader reader = Files.newBufferedReader(FILE)) {
                    KitaBloxConfig config = GSON.fromJson(reader, KitaBloxConfig.class);
                    if (config != null) {
                        return config;
                    }
                }
            }
        } catch (Exception exception) {
            KitaBloxApiClient.LOGGER.warn("Could not load KitaBlox config; defaults will be used.", exception);
        }

        KitaBloxConfig config = new KitaBloxConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(FILE.getParent());
            Path temporaryFile = FILE.resolveSibling(FILE.getFileName() + ".tmp");
            try (Writer writer = Files.newBufferedWriter(temporaryFile)) {
                GSON.toJson(this, writer);
            }
            try {
                Files.move(temporaryFile, FILE,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                    java.nio.file.StandardCopyOption.ATOMIC_MOVE);
            } catch (java.nio.file.AtomicMoveNotSupportedException ignored) {
                Files.move(temporaryFile, FILE,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception exception) {
            KitaBloxApiClient.LOGGER.warn("Could not save KitaBlox config.", exception);
        }
    }

    public void resetFeatureDefaults() {
        nvidiaFpsBoost = false;
        entityOptimization = false;
        animationOptimization = false;
        lowFire = false;
        smallTotem = false;
        smallTotemPop = false;
        clientSideCombo = false;
        consumableOptimizer = false;
        crystalVisualOptimization = false;
        anchorVisualOptimization = false;
        particlesOptimization = false;
        // Intentionally preserve captured video options until PerformanceProfile
        // restores them, then it clears the capture record.
    }
}
