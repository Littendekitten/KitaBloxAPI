package com.kitablox.api;

import org.lwjgl.opengl.GL11;

public final class NvidiaSupport {
    private NvidiaSupport() {}

    /** Returns the OpenGL renderer vendor string; this is informational only. */
    public static String vendor() {
        try {
            String value = GL11.glGetString(GL11.GL_VENDOR);
            return value == null || value.isBlank() ? "Unknown" : value;
        } catch (RuntimeException exception) {
            return "Not available";
        }
    }

    public static boolean looksLikeNvidia() {
        return vendor().toLowerCase(java.util.Locale.ROOT).contains("nvidia");
    }
}
