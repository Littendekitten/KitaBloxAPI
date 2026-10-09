
package com.kitablox.api.mixin;

import com.kitablox.api.KitaBloxApiClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Selectable;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin {

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Shadow
    protected abstract <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement);

    @Inject(method = "init", at = @At("TAIL"))
    private void kitablox$addSettingsButton(CallbackInfo ci) {
        ButtonWidget button = ButtonWidget.builder(
            Text.literal("KitaBlox Settings"),
            b -> KitaBloxApiClient.openSettings(
                MinecraftClient.getInstance()
            )
        )
        .dimensions(
            width / 2 - 100,
            Math.max(20, height - 55),
            200,
            20
        )
        .build();

        this.addDrawableChild(button);
    }
}
