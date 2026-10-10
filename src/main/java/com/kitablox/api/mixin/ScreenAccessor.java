package com.kitablox.api.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** Invokes the child-widget helper on Screen itself, where it is declared. */
@Mixin(Screen.class)
public interface ScreenAccessor {
    @Invoker("addDrawableChild")
    <T extends Element & Drawable & Selectable> T kitabloxapi$invokeAddDrawableChild(T drawableElement);
}
