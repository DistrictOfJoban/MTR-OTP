package com.lx862.mtrotp.mixin;

import com.lx862.mtrotp.config.ClientConfig;
import com.mojang.blaze3d.vertex.*;
import mtr.screen.WidgetMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mtr.data.IGui.ARGB_BLACK;

/**
 * This mixin draws a plain black background in the map, and blocks that are black (including those out of chunk bound) are completely skipped.
 * The effectivity depends on how much black area is rendered. If the map is completely covered with other blocks then there would be no speed up.
 */
@Mixin(value = WidgetMap.class)
public abstract class WidgetMapMixin {
    @Shadow protected abstract void drawRectangle(BufferBuilder buffer, double xA, double yA, double xB, double yB, int color);

    @Shadow(remap = false) private int width;

    @Shadow(remap = false) private int height;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(ClientConfig.dashboardLazyRender) {
            drawBlackBackground();
        }
    }

    @Inject(method = "Lmtr/screen/WidgetMap;drawRectangleFromWorldCoords(Lcom/mojang/blaze3d/vertex/BufferBuilder;DDDDI)V", at = @At("HEAD"), cancellable = true)
    public void drawRectFromWorldCoords(BufferBuilder buffer, double posX1, double posZ1, double posX2, double posZ2, int color, CallbackInfo ci) {
        if(ClientConfig.dashboardLazyRender && color == ARGB_BLACK) {
            ci.cancel();
        }
    }

    private void drawBlackBackground() {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        drawRectangle(bufferBuilder, 0, 0, width, height, ARGB_BLACK);
        tessellator.end();
    }
}
