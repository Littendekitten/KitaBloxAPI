package com.kitablox.api;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class NvidiaWarningScreen extends Screen {
    private final Screen parent;
    public NvidiaWarningScreen(Screen parent) { super(Text.literal("NVIDIA FPS Boost Warning")); this.parent = parent; }
    @Override protected void init() {
        addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), b -> client.setScreen(parent)).dimensions(width/2-105, height/2+35, 100, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Enable NVIDIA FPS Boost"), b -> {
            KitaBloxApiClient.config.nvidiaFpsBoost = true;
            KitaBloxApiClient.config.save();
            PerformanceProfile.apply(client);
            client.setScreen(parent);
        }).dimensions(width/2+5, height/2+35, 150, 20).build());
    }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("NVIDIA GPU REQUIRED"), width/2, height/2-45, 0xFFFF5555);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("Detected GPU vendor: " + NvidiaSupport.vendor()), width/2, height/2-20, 0xFFFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("Only enable this setting if you use an NVIDIA GPU."), width/2, height/2, 0xFFCCCCCC);
        super.render(ctx, mouseX, mouseY, delta);
    }
}
