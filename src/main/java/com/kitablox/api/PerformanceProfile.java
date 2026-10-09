
package com.kitablox.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;

public final class PerformanceProfile {

    private PerformanceProfile() {
    }

    public static void apply(MinecraftClient client) {
        KitaBloxConfig cfg = KitaBloxApiClient.config;

        if (cfg == null) {
            return;
        }

        if (cfg.nvidiaFpsBoost) {
            // Reduce cloud rendering overhead.
            client.options.getCloudRenderMode().setValue(CloudRenderMode.OFF);

            // Disable entity shadows to reduce rendering work.
            client.options.getEntityShadows().setValue(false);
        }
    }
}
