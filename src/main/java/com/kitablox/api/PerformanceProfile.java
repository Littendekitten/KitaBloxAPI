package com.kitablox.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;

/** Applies only reversible, real GameOptions changes supported by 1.21.11. */
public final class PerformanceProfile {
    private PerformanceProfile() {}

    public static void apply(MinecraftClient client) {
        if (client == null || client.options == null || KitaBloxApiClient.config == null) {
            return;
        }

        KitaBloxConfig config = KitaBloxApiClient.config;
        boolean cloudsProfile = config.nvidiaFpsBoost;
        boolean shadowsProfile = config.nvidiaFpsBoost || config.entityOptimization;
        boolean bobbingProfile = config.animationOptimization;
        boolean anyProfile = cloudsProfile || shadowsProfile || bobbingProfile;

        if (!anyProfile) {
            if (config.hasCapturedVideoSettings) {
                restore(client, config);
                config.hasCapturedVideoSettings = false;
                config.originalCloudRenderMode = "";
                config.save();
            }
            return;
        }

        if (!config.hasCapturedVideoSettings) {
            config.originalCloudRenderMode = client.options.getCloudRenderMode().getValue().name();
            config.originalEntityShadows = client.options.getEntityShadows().getValue();
            config.originalBobView = client.options.getBobView().getValue();
            config.hasCapturedVideoSettings = true;
            config.save();
        }

        CloudRenderMode originalCloudMode = readCloudMode(
            config.originalCloudRenderMode,
            client.options.getCloudRenderMode().getValue()
        );
        client.options.getCloudRenderMode().setValue(
            cloudsProfile ? CloudRenderMode.OFF : originalCloudMode
        );
        client.options.getEntityShadows().setValue(
            shadowsProfile ? false : config.originalEntityShadows
        );
        client.options.getBobView().setValue(
            bobbingProfile ? false : config.originalBobView
        );
    }

    private static void restore(MinecraftClient client, KitaBloxConfig config) {
        client.options.getCloudRenderMode().setValue(readCloudMode(
            config.originalCloudRenderMode,
            client.options.getCloudRenderMode().getValue()
        ));
        client.options.getEntityShadows().setValue(config.originalEntityShadows);
        client.options.getBobView().setValue(config.originalBobView);
    }

    private static CloudRenderMode readCloudMode(String name, CloudRenderMode fallback) {
        if (name == null || name.isBlank()) {
            return fallback;
        }
        try {
            return CloudRenderMode.valueOf(name);
        } catch (IllegalArgumentException exception) {
            return fallback;
        }
    }
}
