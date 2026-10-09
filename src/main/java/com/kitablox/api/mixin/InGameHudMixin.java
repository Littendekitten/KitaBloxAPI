package com.kitablox.api.mixin;

import com.kitablox.api.KitaBloxApiClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "renderOverlay", at = @At("HEAD"), cancellable = true)
    private void kitablox$lowFire(DrawContext context, Identifier texture, float opacity, CallbackInfo ci) {
        if (!KitaBloxApiClient.config.lowFire) return;
        String path = texture.getPath();
        if (path.contains("fire_0") || path.contains("fire_1")) {
            ci.cancel();
            int h = Math.max(1, context.getScaledWindowHeight() / 7);
            context.fill(0, 0, context.getScaledWindowWidth(), h, 0x22000000);
        }
    }
}
