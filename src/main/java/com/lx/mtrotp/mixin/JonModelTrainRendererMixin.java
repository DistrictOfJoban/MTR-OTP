package com.lx.mtrotp.mixin;

import com.lx.mtrotp.Util;
import com.lx.mtrotp.config.Config;
import mtr.data.TrainClient;
import mtr.render.JonModelTrainRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = JonModelTrainRenderer.class, remap = false)
/**
 * Skip rendering train cars outside the camera's bound
 * The bounding box for determining whether to cull is calculated by the train's defined width and height rather than the model.
 * Expect stuff to be culled incorrectly on some custom RP or the A320 plane.
 */
public class JonModelTrainRendererMixin {
    @Shadow @Final private TrainClient train;

    @Inject(method = "renderCar", at = @At("HEAD"), cancellable = true)
    public void renderCar(int carIndex, double x, double y, double z, float yaw, float pitch, boolean doorLeftOpen, boolean doorRightOpen, CallbackInfo ci) {
        if(canBeCulled(carIndex)) {
            ci.cancel();
        }
    }

    private boolean canBeCulled(int carIndex) {
        if(!Config.cullTrain) return false;
        AABB boundingBox = Util.getTrainBoundingBox(train, carIndex, train.spacing);
        Frustum frustum = ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).getCullingFrustum();

        return !frustum.isVisible(boundingBox);
    }
}
