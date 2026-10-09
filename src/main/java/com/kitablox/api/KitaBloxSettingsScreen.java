package com.kitablox.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class KitaBloxSettingsScreen extends Screen {
    private final Screen parent;
    private int y;
    public KitaBloxSettingsScreen(Screen parent) {
        super(Text.literal("KitaBlox Settings"));
        this.parent = parent;
    }
    @Override protected void init() {
        y = 65;
        addToggle("NVIDIA FPS Boost", () -> KitaBloxApiClient.config.nvidiaFpsBoost, v -> {
            if (v) MinecraftClient.getInstance().setScreen(new NvidiaWarningScreen(this));
            else { KitaBloxApiClient.config.nvidiaFpsBoost = false; KitaBloxApiClient.config.save(); }
        });
        addToggle("Low Fire", () -> KitaBloxApiClient.config.lowFire, v -> set(() -> KitaBloxApiClient.config.lowFire = v));
        addToggle("Small Totem", () -> KitaBloxApiClient.config.smallTotem, v -> set(() -> KitaBloxApiClient.config.smallTotem = v));
        addToggle("Small Totem Pop", () -> KitaBloxApiClient.config.smallTotemPop, v -> set(() -> KitaBloxApiClient.config.smallTotemPop = v));
        addToggle("Consumable Optimizer", () -> KitaBloxApiClient.config.consumableOptimizer, v -> set(() -> KitaBloxApiClient.config.consumableOptimizer = v));
        addToggle("Client Side Combo", () -> KitaBloxApiClient.config.clientSideCombo, v -> set(() -> KitaBloxApiClient.config.clientSideCombo = v));
        addToggle("Crystal Visual Optimization", () -> KitaBloxApiClient.config.crystalVisualOptimization, v -> set(() -> KitaBloxApiClient.config.crystalVisualOptimization = v));
        addToggle("Anchor Visual Optimization", () -> KitaBloxApiClient.config.anchorVisualOptimization, v -> set(() -> KitaBloxApiClient.config.anchorVisualOptimization = v));
        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), b -> client.setScreen(parent)).dimensions(width / 2 - 100, Math.min(y + 10, height - 35), 200, 20).build());
    }
    private void set(Runnable r) { r.run(); KitaBloxApiClient.config.save(); PerformanceProfile.apply(client); clearAndInit(); }
    private void addToggle(String name, java.util.function.BooleanSupplier get, java.util.function.Consumer<Boolean> set) {
        String label = name + ": " + (get.getAsBoolean() ? "ON" : "OFF");
        addDrawableChild(ButtonWidget.builder(Text.literal(label), b -> set.accept(!get.getAsBoolean())).dimensions(width / 2 - 150, y, 300, 20).build());
        y += 24;
    }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("K I T A B L O X"), width / 2, 18, 0xFF66CCFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("KitaBlox Settings • Minecraft 1.21.11"), width / 2, 38, 0xFFAAAAAA);
        super.render(ctx, mouseX, mouseY, delta);
    }
}
