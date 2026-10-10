package com.kitablox.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public final class KitaBloxSettingsScreen extends Screen {
    private enum Category { PERFORMANCE, VISUALS, TOTEM_FIRE, NVIDIA, GENERAL }

    private final Screen parent;
    private Category category = Category.PERFORMANCE;
    private boolean confirmReset = false;

    public KitaBloxSettingsScreen(Screen parent) {
        super(Text.literal("KitaBlox Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        addCategoryButton("PERF", Category.PERFORMANCE, 0);
        addCategoryButton("VISUALS", Category.VISUALS, 1);
        addCategoryButton("TOTEM/FIRE", Category.TOTEM_FIRE, 2);
        addCategoryButton("NVIDIA", Category.NVIDIA, 3);
        addCategoryButton("GENERAL", Category.GENERAL, 4);

        int center = width / 2;
        int controlWidth = Math.min(390, Math.max(220, width - 36));
        int left = center - controlWidth / 2;
        int y = 126;

        switch (category) {
            case PERFORMANCE -> {
                addToggle("Entity Optimization", () -> KitaBloxApiClient.config.entityOptimization,
                    value -> setFeature(() -> KitaBloxApiClient.config.entityOptimization = value), left, y, controlWidth);
                y += 29;
                addToggle("Animation Optimization", () -> KitaBloxApiClient.config.animationOptimization,
                    value -> setFeature(() -> KitaBloxApiClient.config.animationOptimization = value), left, y, controlWidth);
            }
            case VISUALS -> { /* status rows are drawn in render() */ }
            case TOTEM_FIRE -> {
                addToggle("Low Fire (hide first-person fire overlay)", () -> KitaBloxApiClient.config.lowFire,
                    value -> setFeature(() -> KitaBloxApiClient.config.lowFire = value), left, y, controlWidth);
            }
            case NVIDIA -> {
                boolean enabled = KitaBloxApiClient.config.nvidiaFpsBoost;
                String label = enabled ? "NVIDIA FPS Boost: ON  •  Disable" : "NVIDIA FPS Boost: OFF  •  Configure";
                addDrawableChild(ButtonWidget.builder(Text.literal(label), button -> {
                    if (KitaBloxApiClient.config.nvidiaFpsBoost) {
                        KitaBloxApiClient.config.nvidiaFpsBoost = false;
                        KitaBloxConfig config = KitaBloxApiClient.config;
                        config.save();
                        PerformanceProfile.apply(MinecraftClient.getInstance());
                        clearAndInit();
                    } else {
                        client.setScreen(new NvidiaWarningScreen(this));
                    }
                }).dimensions(left, y, controlWidth, 22).build());
            }
            case GENERAL -> {
                if (confirmReset) {
                    addDrawableChild(ButtonWidget.builder(Text.literal("YES, RESET SETTINGS"), button -> {
                        KitaBloxApiClient.config.resetFeatureDefaults();
                        KitaBloxApiClient.config.save();
                        PerformanceProfile.apply(MinecraftClient.getInstance());
                        confirmReset = false;
                        KitaBloxApiClient.config.save();
                        clearAndInit();
                    }).dimensions(left, y, controlWidth, 22).build());
                    addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), button -> {
                        confirmReset = false;
                        clearAndInit();
                    }).dimensions(left, y + 29, controlWidth, 22).build());
                } else {
                    addDrawableChild(ButtonWidget.builder(Text.literal("Reset KitaBlox Settings to Defaults"), button -> {
                        confirmReset = true;
                        clearAndInit();
                    }).dimensions(left, y, controlWidth, 22).build());
                }
            }
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> client.setScreen(parent))
            .dimensions(center - 75, height - 34, 150, 22).build());
    }

    private void addCategoryButton(String text, Category target, int index) {
        int gap = 4;
        int maxTabWidth = 138;
        int tabWidth = Math.min(maxTabWidth, Math.max(48, (width - 32 - gap * 4) / 5));
        int totalWidth = tabWidth * 5 + gap * 4;
        int x = (width - totalWidth) / 2 + index * (tabWidth + gap);
        String label = category == target ? "• " + text : text;
        addDrawableChild(ButtonWidget.builder(Text.literal(label), button -> {
            category = target;
            confirmReset = false;
            clearAndInit();
        }).dimensions(x, 78, tabWidth, 22).build());
    }

    private void addToggle(String label, BooleanSupplier current, Consumer<Boolean> setter,
                           int x, int y, int controlWidth) {
        String state = current.getAsBoolean() ? "ON" : "OFF";
        addDrawableChild(ButtonWidget.builder(Text.literal(label + ": " + state), button -> {
            setter.accept(!current.getAsBoolean());
        }).dimensions(x, y, controlWidth, 22).build());
    }

    private void setFeature(Runnable change) {
        change.run();
        KitaBloxApiClient.config.save();
        PerformanceProfile.apply(MinecraftClient.getInstance());
        clearAndInit();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        drawSpaceBackground(context);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("K I T A B L O X"), width / 2, 22, 0xFFFF8B32);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("SPACE PERFORMANCE CLIENT  •  MINECRAFT 1.21.11"), width / 2, 45, 0xFF7BE7FF);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal(categoryTitle()), width / 2, 109, 0xFFFFFFFF);

        int x = Math.max(16, width / 2 - Math.min(390, width - 36) / 2);
        int y = 190;
        switch (category) {
            case PERFORMANCE -> {
                drawStatus(context, x, y, "Particle Optimization — unavailable in this build");
                drawStatus(context, x, y + 22, "Consumable Optimizer — unavailable in this build");
                drawStatus(context, x, y + 53, "Entity Optimization disables entity shadows.");
                drawStatus(context, x, y + 72, "Animation Optimization disables view bobbing.");
            }
            case VISUALS -> {
                drawStatus(context, x, y - 50, "Crystal Visual Optimization — unavailable in this build");
                drawStatus(context, x, y - 24, "Respawn Anchor Visual Optimization — unavailable in this build");
                drawStatus(context, x, y + 2, "Client-side Combo HUD — unavailable in this build");
            }
            case TOTEM_FIRE -> {
                drawStatus(context, x, y - 14, "Low Fire suppresses the first-person fire overlay.");
                drawStatus(context, x, y + 16, "Small Totem — unavailable in this build");
                drawStatus(context, x, y + 42, "Small Totem Pop — unavailable in this build");
            }
            case NVIDIA -> {
                drawStatus(context, x, y + 2, "Renderer vendor: " + NvidiaSupport.vendor());
                drawStatus(context, x, y + 26, "Profile disables clouds and entity shadows.");
                drawStatus(context, x, y + 50, "Original values are restored when all profiles are off.");
                drawStatus(context, x, y + 74, "This is a reversible settings profile, not a vendor-specific driver feature.");
            }
            case GENERAL -> {
                drawStatus(context, x, y + 14, confirmReset
                    ? "Reset every KitaBlox feature setting and restore saved video options?"
                    : "KitaBlox API " + KitaBloxConfig.VERSION);
                drawStatus(context, x, y + 38, "Config file: .minecraft/config/kitablox-api.json");
                drawStatus(context, x, y + 62, "Unavailable options are intentionally not clickable.");
            }
        }

        context.drawCenteredTextWithShadow(textRenderer,
            Text.literal("Verified settings only • No packets or gameplay automation"),
            width / 2, height - 54, 0xFF788BAF);
        super.render(context, mouseX, mouseY, delta);
    }

    private String categoryTitle() {
        return switch (category) {
            case PERFORMANCE -> "PERFORMANCE";
            case VISUALS -> "VISUALS";
            case TOTEM_FIRE -> "TOTEM & FIRE";
            case NVIDIA -> "NVIDIA FPS PROFILE";
            case GENERAL -> "GENERAL";
        };
    }

    private void drawStatus(DrawContext context, int x, int y, String text) {
        context.drawTextWithShadow(textRenderer, Text.literal(text), x, y, 0xFFB6C6E2);
    }

    private void drawSpaceBackground(DrawContext context) {
        context.fill(0, 0, width, height, 0xFF050A18);
        for (int i = 0; i < 90; i++) {
            int x = Math.floorMod(i * 73 + 19, Math.max(1, width));
            int y = Math.floorMod(i * 131 + 29, Math.max(1, height));
            int color = i % 9 == 0 ? 0xFF63DFFF : 0xFF46567D;
            context.fill(x, y, x + 1, y + 1, color);
        }
        int panelWidth = Math.min(560, Math.max(260, width - 24));
        int left = (width - panelWidth) / 2;
        context.fill(left, 68, left + panelWidth, height - 44, 0xD90D172D);
        context.fill(left, 68, left + panelWidth, 71, 0xFFFF7300);
        context.fill(left, 71, left + 2, height - 44, 0xFF1B9EC7);
        context.fill(left + panelWidth - 2, 71, left + panelWidth, height - 44, 0xFF1B9EC7);
    }
}
