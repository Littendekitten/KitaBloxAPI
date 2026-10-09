package com.kitablox.api.mixin;

import com.kitablox.api.KitaBloxApiClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin {
    @Shadow public int width;
    @Shadow public int height;
    @Inject(method = "init", at = @At("TAIL"))
    private void kitablox$addSettingsButton(CallbackInfo ci) {
        GameMenuScreen screen = (GameMenuScreen)(Object)this;
        ButtonWidget button = ButtonWidget.builder(Text.literal("KitaBlox Settings"), b -> KitaBloxApiClient.openSettings(MinecraftClient.getInstance()))
                .dimensions(width / 2 - 100, Math.max(20, height - 55), 200, 20).build();
        screen.addDrawableChild(button);
    }
}
