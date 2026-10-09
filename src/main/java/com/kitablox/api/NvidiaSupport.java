package com.kitablox.api;

import org.lwjgl.opengl.GL11;

public final class NvidiaSupport {
    private NvidiaSupport() {}
    public static String vendor() {
        String v = GL11.glGetString(GL11.GL_VENDOR);
        return v == null ? "Unknown" : v;
    }
    public static boolean looksLikeNvidia() {
        return vendor().toLowerCase().contains("nvidia");
    }
}
