package com.lx862.mtrotp.mixin;

import mtr.data.RailwayData;
import mtr.data.TrainClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = TrainClient.class, remap = false)
public class TrainClientMixin {
    @ModifyArgs(method = "handlePositions", at = @At(value = "INVOKE", target = "Lmtr/sound/TrainSoundBase;playNearestCar(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;I)V"))
    public void fixReversedSound(Args args) {
        Level lvl = args.get(0);
        BlockPos pos = args.get(1);
        int nearestCar = args.get(2);

        TrainClient thisTrain = (TrainClient)(Object)this;
        if(((TrainAccessorMixin)thisTrain).getReversed()) {
            double nearestDistance = Double.POSITIVE_INFINITY;
            Vec3 fixedPos = null;

            for (int i = -1; i < thisTrain.trainCars; i++) {
                Vec3 newFixedPos = ((TrainAccessorMixin)(thisTrain)).routePosition(i, thisTrain.spacing);
                final double checkDistance = Minecraft.getInstance().getCameraEntity().distanceToSqr(newFixedPos);
                if (checkDistance < nearestDistance) {
                    nearestDistance = checkDistance;
                    fixedPos = newFixedPos;
                }
            }

            if(fixedPos != null) {
                args.set(1, RailwayData.newBlockPos(fixedPos));
            }
        }
    }
}
