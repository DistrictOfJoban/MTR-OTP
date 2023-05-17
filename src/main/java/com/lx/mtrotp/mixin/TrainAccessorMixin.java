package com.lx.mtrotp.mixin;

import mtr.data.Train;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@Mixin(value = Train.class, remap = false)
public interface TrainAccessorMixin {

    @Accessor
    List<Double> getDistances();

    @Invoker("getModelZOffset")
    float modelZOffset();
}
