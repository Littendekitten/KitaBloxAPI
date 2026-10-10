package com.kitablox.api.mixin;

import com.kitablox.api.KitaBloxApiClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Suppresses the first-person fire overlay when Low Fire is enabled. */
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void kitabloxapi$reduceFireOverlay(DrawContext context, Identifier texture, float opacity, CallbackInfo ci) {
        if (KitaBloxApiClient.config == null || !KitaBloxApiClient.config.lowFire || texture == null) {
            return;
        }

        String path = texture.getPath();
        if (path.contains("fire_0") || path.contains("fire_1")) {
            ci.cancel();
        }
    }
}
