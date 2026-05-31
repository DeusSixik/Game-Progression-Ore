package dev.sixik.gameprogressionore.impl.mixin;

import dev.sixik.gameprogressionore.api.BlockRestrictionHelper;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import dev.sixik.gameprogressionore.impl.utils.FastBlockStateCache;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerGameMode.class)
public class MixinServerPlayerGameMode {

    @Shadow
    @Final
    protected ServerPlayer player;

    @Unique
    private BlockState gpo$replaceBlock(BlockState original) {
        final int blockId = BlockIdGetter.get(original).gpo$getFastId();
        int replace = BlockRestrictionHelper.getReplacementBlock(blockId, player);
        if(replace == blockId) return original;
        return FastBlockStateCache.STATES[replace];
    }

    @Redirect(method = "handleBlockBreakAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState gpo$onBlockBreak(ServerLevel instance, BlockPos blockPos) {
        return gpo$replaceBlock(instance.getBlockState(blockPos));
    }

    @Redirect(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState gpo$onDestroyBlock(ServerLevel instance, BlockPos blockPos) {
        return gpo$replaceBlock(instance.getBlockState(blockPos));
    }

    @Redirect(method = "useItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState gpo$onUseItemOn(Level instance, BlockPos blockPos) {
        return gpo$replaceBlock(instance.getBlockState(blockPos));
    }
}
