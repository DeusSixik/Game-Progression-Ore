package dev.sixik.gameprogressionore.impl.mixin.packets;

import dev.sixik.gameprogressionore.api.BlockRestrictionHelper;
import dev.sixik.gameprogressionore.api.block.BlockIdGetter;
import dev.sixik.gameprogressionore.impl.utils.FastBlockStateCache;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientboundBlockUpdatePacket.class)
public class MixinClientboundBlockUpdatePacket {

    @Shadow
    @Final
    private BlockState blockState;

    @Inject(method = "getBlockState", at = @At("HEAD"), cancellable = true)
    private void gpo$getBlockState$replaceHidden(CallbackInfoReturnable<BlockState> cir) {
        final int blockId = BlockIdGetter.get(this.blockState).gpo$getFastId();
        final int replacementId = BlockRestrictionHelper.getReplacementBlock(blockId);
        cir.setReturnValue(replacementId == blockId ? this.blockState : FastBlockStateCache.STATES[replacementId]);
    }
}
