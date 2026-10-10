package com.kitablox.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class NvidiaWarningScreen extends Screen {
    private final Screen parent;

    public NvidiaWarningScreen(Screen parent) {
        super(Text.literal("NVIDIA FPS Boost Warning"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int buttonY = Math.min(height - 38, height / 2 + 54);
        addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), button -> client.setScreen(parent))
            .dimensions(width / 2 - 108, buttonY, 100, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Enable profile"), button -> {
            KitaBloxApiClient.config.nvidiaFpsBoost = true;
            KitaBloxApiClient.config.save();
            PerformanceProfile.apply(MinecraftClient.getInstance());
            client.setScreen(parent);
        }).dimensions(width / 2 + 8, buttonY, 130, 22).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        drawSpaceBackground(context);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("NVIDIA FPS BOOST"), width / 2, height / 2 - 70, 0xFFFF7300);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Renderer vendor: " + NvidiaSupport.vendor()), width / 2, height / 2 - 43, 0xFF7BE7FF);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("This profile is not a proprietary NVIDIA feature."), width / 2, height / 2 - 18, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("It disables clouds and entity shadows while enabled."), width / 2, height / 2 + 2, 0xFFBFCBE0);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Your previous settings are saved and restored when disabled."), width / 2, height / 2 + 22, 0xFFBFCBE0);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawSpaceBackground(DrawContext context) {
        context.fill(0, 0, width, height, 0xFF050A18);
        for (int i = 0; i < 70; i++) {
            int x = Math.floorMod(i * 73 + 19, Math.max(1, width));
            int y = Math.floorMod(i * 131 + 29, Math.max(1, height));
            int color = i % 8 == 0 ? 0xFF63DFFF : 0xFF495A82;
            context.fill(x, y, x + 1, y + 1, color);
        }
        context.fill(width / 2 - 250, height / 2 - 92, width / 2 + 250, height / 2 + 84, 0xDD101A32);
        context.fill(width / 2 - 250, height / 2 - 92, width / 2 + 250, height / 2 - 89, 0xFFFF7300);
    }
}
