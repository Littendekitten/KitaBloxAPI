
package com.kitablox.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.particle.ParticleStatus;

public final class PerformanceProfile {
    private PerformanceProfile() {
    }

    public static void apply(MinecraftClient client) {
        KitaBloxConfig cfg = KitaBloxApiClient.config;

        if (cfg == null) {
            return;
        }

        if (cfg.nvidiaFpsBoost) {
            client.options.getCloudRenderMode().setValue(CloudRenderMode.OFF);
            client.options.getEntityShadows().setValue(false);
            client.options.getParticles().setValue(ParticleStatus.MINIMAL);
        }
    }
}
