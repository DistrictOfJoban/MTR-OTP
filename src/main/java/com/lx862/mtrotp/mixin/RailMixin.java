package com.lx862.mtrotp.mixin;

import mtr.data.Rail;
import mtr.data.RailwayData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.function.Consumer;

@Mixin(Rail.RailActions.class)
public abstract class RailMixin {
    @Shadow protected abstract boolean create(boolean includeMiddle, Consumer<Vec3> consumer);

    @Shadow @Final private Set<BlockPos> blacklistedPos;

    @Shadow
    private static BlockPos getHalfPos(BlockPos pos, boolean isTopHalf) {
        return null;
    }

    @Shadow @Final private boolean isSlab;

    @Shadow @Final private BlockState state;

    @Shadow
    private static boolean canPlace(Level world, BlockPos pos) {
        return false;
    }

    @Shadow @Final private Level world;

    @Inject(method = "createBridge", at = @At("HEAD"), remap = false, cancellable = true)
    public void createBridge(CallbackInfoReturnable<Boolean> cir) {
        // 17
        // 12
        cir.setReturnValue(create(false, editPos -> {
            final BlockPos pos = RailwayData.newBlockPos(editPos);
            final boolean isTopHalf = editPos.y - Math.floor(editPos.y) >= 0.5;
            blacklistedPos.add(getHalfPos(pos, isTopHalf));

            final BlockPos placePos;
            final BlockState placeState;
            final boolean placeHalf;

            if (isSlab && isTopHalf) {
                placePos = pos;
                placeState = state.setValue(SlabBlock.TYPE, SlabType.BOTTOM);
                placeHalf = false;
            } else {
                placePos = pos.below();
                placeState = isSlab ? state.setValue(SlabBlock.TYPE, SlabType.TOP) : state;
                placeHalf = true;
            }

            BlockPos pph = getHalfPos(placePos, placeHalf);
            if(blacklistedPos.contains(pph)) return;

            if (placePos != pos && canPlace(world, pos)) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }

            if (canPlace(world, placePos)) {
                world.setBlockAndUpdate(placePos, placeState);
            }
            blacklistedPos.add(pph);
        }));
    }
}
