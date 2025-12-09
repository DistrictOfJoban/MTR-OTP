package com.lx862.mtrotp.mixin;

import mtr.data.Lift;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = Lift.class, remap = false)
public class LiftMixin {
    @ModifyArg(method = "lambda$tick$4", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), index = 4)
    public float fixLiftVolume(float f) {
        return 1.0F;
    }
}