package com.kitablox.api;

import com.kitablox.api.mixin.ScreenAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KitaBloxApiClient implements ClientModInitializer {
    public static final String MOD_ID = "kitabloxapi";
    public static final String MOD_NAME = "KitaBlox API";
    public static final String MINECRAFT_VERSION = "1.21.11";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static KitaBloxConfig config;
    private static KeyBinding settingsKey;

    @Override
    public void onInitializeClient() {
        config = KitaBloxConfig.load();

        KeyBinding.Category category = KeyBinding.Category.create(
            Identifier.of(MOD_ID, "settings")
        );
        settingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.kitabloxapi.settings",
            InputUtil.Type.KEYSYM,
            -1,
            category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (settingsKey.wasPressed()) {
                if (client.currentScreen == null) {
                    openSettings(client);
                }
            }
        });

        // Use Fabric's screen event instead of shadowing an inherited Screen
        // method on GameMenuScreen (the cause of the reported startup crash).
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof GameMenuScreen)) {
                return;
            }

            ButtonWidget button = ButtonWidget.builder(
                Text.literal("KitaBlox Settings"),
                widget -> openSettings(client)
            ).dimensions(
                scaledWidth / 2 - 100,
                Math.max(20, scaledHeight - 55),
                200,
                20
            ).build();

            ((ScreenAccessor) (Object) screen).kitabloxapi$invokeAddDrawableChild(button);
        });

        ClientLifecycleEvents.CLIENT_STARTED.register(PerformanceProfile::apply);
        LOGGER.info("KitaBlox API loaded for Minecraft {}", MINECRAFT_VERSION);
    }

    public static void openSettings(MinecraftClient client) {
        client.setScreen(new KitaBloxSettingsScreen(client.currentScreen));
    }
}
