package com.lx.mtrotp.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface WorldRendererAccessor {
    @Accessor
    Frustum getCullingFrustum();
}
